package com.fiberhome.nmpDataBusPlugin;

import com.fiberhome.nmpDataBusPlugin.HardwareHelper.HardwareInfo;
import com.fiberhome.nmpDataBusPlugin.HardwareHelper.SystemInfo;
import com.fiberhome.nmpDataBusPlugin.thrift.*;
import com.fiberhome.nmpDataBusPlugin.util.ConfigUtil;
import com.fiberhome.nmpDataBusPlugin.util.FileUtil;
import com.fiberhome.nmpDataBusPlugin.util.UUIDUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.hyperic.sigar.SigarException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author  黄帆
 */
public class DataServiceImp implements NMPDataService.Iface {


    private Logger logger=LogManager.getLogger(DataServiceImp.class);

    public static  final  Map<String,FileWatcherEventArgs> lazyFileEventsMap=new HashMap<>();

    private Map<String,FileAlterationMonitor> registedWathersMap=new HashMap<>();

    private String notifyIP;

    private int notifyPort;

    private static Object o = new Object();

    private  String javaFileServiceName;

    private int maxDownloadFileLimit=16000000;

    public String tempFolder;

    public String getJavaFileServiceName() {
        return javaFileServiceName;
    }

    public void setJavaFileServiceName(String javaFileServiceName) {
        this.javaFileServiceName = javaFileServiceName;
    }

    public String getTempFolder() {
        return tempFolder;
    }

    public void setTempFolder(String tempFolder) {
        this.tempFolder = tempFolder;
    }

    public int getMaxDownloadFileLimit() {
        return maxDownloadFileLimit;
    }

    public static Map<String, FileWatcherEventArgs> getLazyFileEventsMap() {
        return lazyFileEventsMap;
    }

    public String getNotifyIP() {
        return notifyIP;
    }

    public void setNotifyIP(String notifyIP) {
        this.notifyIP = notifyIP;
    }

    public DataServiceImp(){
        FolderWatcherInfo watcherInfo= null;
        try {
            watcherInfo = loadFromFIle();
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
        restoreWatchers(watcherInfo);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        synchronized (o){
                            if (lazyFileEventsMap !=null &&!lazyFileEventsMap.isEmpty()){
                                Iterator<Map.Entry<String,FileWatcherEventArgs>> it=lazyFileEventsMap.entrySet().iterator();
                                while (it.hasNext()){
                                    Map.Entry<String,FileWatcherEventArgs> entry=it.next();
                                    fileWatcherCallback(entry.getValue().getPath(),entry.getValue().getEventType());
                                    it.remove();
                                }

                            }
                        }
                    }catch (Exception e){
                        logger.error(e);
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                }
            }
        });
        t.start();
        logger.info(">>>>>begin file system watcher");
        Thread serviceDaemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {

                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error(e);
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        serviceDaemonThread.start();
        logger.info(">>>>>>begin daemon service watcher");
    }


    @Override
    public ByteBuffer DownloadFile(String path) throws RPCApplicationException, TException {
        logger.info(">>>>>>someone is downloading file:"+path);
        File file=new File(path);
        if (!file.exists()){
            //return new byte[0];
        }
        logger.info("the file size is:"+file.length());
        if (file.length()>maxDownloadFileLimit){
            throw  new RPCApplicationException("The file is greater than "+maxDownloadFileLimit+" bytes",null,101);
        }
        byte[] buffer=null;
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos=new ByteArrayOutputStream(1000);) {
            byte[] b=new byte[1000];
            int n;
            while ((n=fis.read(b))!=-1){
                bos.write(b,0,n);
            }
            buffer=bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ByteBuffer.wrap(buffer);
    }

    @Override
    public ByteBuffer ReadFile(String path, int position, int length) throws RPCApplicationException, TException {
        logger.info("someone is reading the file,path:"+path+",position:"+position+",size:"+length);
        if (position<0){
            throw new RPCApplicationException("Invalid arguments:position",105);
        }
        if (length>maxDownloadFileLimit){
            throw new RPCApplicationException("The length is greatter than "+maxDownloadFileLimit+" bytes",101);
        }
        File file=new File(path);
        if (position>file.length()){
            throw new RPCApplicationException("Invalid arguments:position",105);
        }
        if ((position+length)>file.length()){
            throw new RPCApplicationException("Invalid arguments:position+length>FileSize",106);
        }
        int retry =10;
        boolean success=false;
        byte[] buffer=null;
        while(retry>0) {
            try (FileInputStream fis = new FileInputStream(file);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);) {
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                buffer = bos.toByteArray();
                success = true;
                break;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!success){
            throw new RPCApplicationException("读取文件的尝试次数使用完，可能因为文件一直被占用");
        }
        return ByteBuffer.wrap(buffer);
    }

    @Override
    public long GetFileSize(String path) throws RPCApplicationException, TException {
        logger.info(">>>>>>get file size,path"+path);
        //此处考虑到可能超出int 的范围，因此返回类型变为long
        File file =new File(path);
        if (!file.exists()){
            throw new RPCApplicationException("the file is not exist ",102);
        }

        return file.length();
    }

    @Override
    public String LockFile(String path) throws RPCApplicationException, TException {
        //文件锁其实就是建立一个文件的副本
        logger.info(">>>>>lock file:"+path);
        clearOldTempFiles();
        File file=new File(path);
        if (!file.exists()){
            throw new RPCApplicationException(">>>>>the file is not exist,path:"+path,102);
        }
        if (!new File(getTempFolder()).exists()){
            try {
                new File(getTempFolder()).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        String newPath=getTempFolder().concat(UUIDUtil.getRandomUUID());
        int retry=10;
        boolean isSuccess=false;
        while(--retry>0){
            try {
                FileUtil.copyfile(path,newPath);
                isSuccess=true;
                break;
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("拷贝文件"+path+"失败，重试剩余次数:"+retry);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        if (!isSuccess){
            throw new RPCApplicationException(">>>>>重试次数用完，文件无法锁定");
        }
        return newPath;
    }
    private void clearOldTempFiles(){
        logger.info(">>>>>>clear old temp files");
        File file=new File(getTempFolder());
        if (!file.exists()){
            return;
        }
        if (getTempFolder().toLowerCase().contains(System.getProperty("user.dir").toLowerCase())){
            File[] files=file.listFiles();
            try {
                for (File f : files) {
                    f.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e);
            }
        }
    }
    @Override
    public boolean UnlockFile(String path) throws RPCApplicationException, TException {
        logger.info(">>>>>unlock file ,path:"+path);
        if (path.toLowerCase().contains(getTempFolder().toLowerCase())){
            try {
              return new File(path).delete();
            }catch (Exception e){
                logger.error(">>>>>>文件删除失败");
                throw new RPCApplicationException(e.getMessage());
            }
        }else{
            throw new RPCApplicationException("you have no authority to release this file",104);
        }
    }

    @Override
    public List<String> GetChildFolders(String folderPath) throws RPCApplicationException, TException {
        logger.info("get child folder,path:"+folderPath);
        File file=new File(folderPath);
        if (!file.exists()){
            throw new RPCApplicationException("the directory is not exist");
        }
        List<String> list=new ArrayList<>();
        File[] files=file.listFiles();
        for (int i=0;i<files.length;i++){
            if (files[i].isDirectory()){
                list.add(files[i].getAbsolutePath());
            }
        }
        return list;
    }

    @Override
    public List<String> GetChildFiles(String folderPath) throws RPCApplicationException, TException {
        logger.info("get child file,path:"+folderPath);
        File file=new File(folderPath);
        if (!file.exists()){
            throw new RPCApplicationException("the directory is not exist");
        }
        List<String> list=new ArrayList<>();
        File[] files=file.listFiles();
        for (int i=0;i<files.length;i++){
            if (files[i].isFile()){
                list.add(files[i].getAbsolutePath());
            }
        }
        return list;
    }

    @Override
    public List<String> SearchFile(String searchPattern, String folder) throws RPCApplicationException, TException {
        logger.info(">>>>>>search file，searchPattern："+searchPattern+">>>folder"+folder);
        File file=new File(folder);
        if (!file.exists()){
            throw new RPCApplicationException(">>>>>>>the directory is not exist:"+folder,102);
        }
        File[] files=file.listFiles((FileFilter) getFilter(searchPattern,true));
        List<String> list=new ArrayList<>();
        for (File f:files){
            list.add(f.getAbsolutePath());
        }
        return list;
    }

    @Override
    public boolean ExistsFolder(String path) throws RPCApplicationException, TException {
        logger.info("check if exist folder:path"+path);
        File file = new File(path);
        return file.exists()&&file.isDirectory();
    }

    @Override
    public boolean ExistsFile(String path) throws RPCApplicationException, TException {
        logger.info("check if exist the file,path:"+path);
        return new File(path).exists();
    }

    @Override
    public boolean RegisterFolderWatcher(String path, String filter, boolean includeSubDirectories, String notifyIP, int notifyPort) throws RPCApplicationException, TException {
        logger.info("register folder watcher ,path:"+path+",filter:"+filter+",is includeSubDirectory+"+includeSubDirectories+",notifyIP"+notifyIP+",notifyPort:"+notifyPort);
        try {
            synchronized (o){
                path=path.toLowerCase();
                filter=filter.toLowerCase();
                this.notifyIP=notifyIP;
                this.notifyPort=notifyPort;

                File file=new File(path);
                if (!file.exists()){
                    logger.error("the directory is not exist,path:"+path);
                    throw new RPCApplicationException("the directory is not exist,path:"+path);
                }
                String key=path+filter;
                FileAlterationMonitor monitor= null;
                if (registedWathersMap.containsKey(key)){
                    logger.info("the path and the filter has already been registered");
                    monitor=registedWathersMap.get(key);
                    monitor.start();
                    FolderWatcherInfo watcherInfo=loadFromFIle();
                    watcherInfo.setNotifyIP(notifyIP);
                    watcherInfo.setNotifyPort(notifyPort);
                    saveIntoFile(watcherInfo);
                    return true;
                }else{
                    logger.info("add a new path and filter:"+key);
                    /*
                     *创建监听器
                     */
                    //轮询间隔
                    long interval = TimeUnit.SECONDS.toMillis(1);
                    FileAlterationObserver observer=new FileAlterationObserver(new File(path),getFilter(filter,includeSubDirectories));
                    observer.addListener(new FileListener());
                    monitor=new FileAlterationMonitor(interval,observer);
                    try {
                        monitor.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                    FolderWatcherInfo watcherInfo=loadFromFIle();
                    watcherInfo.setNotifyIP(notifyIP);
                    watcherInfo.setNotifyPort(notifyPort);
                    if (watcherInfo.getItemList()==null){
                        List<FolerWatcherItem> list=new ArrayList<>();
                        watcherInfo.setItemList(list);
                    }
                    watcherInfo.getItemList().add(new FolerWatcherItem(includeSubDirectories,path,filter));
                    saveIntoFile(watcherInfo);
                    registedWathersMap.put(key,monitor);
                    return true;

                }


            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        return false;
    }

    /**
     * @author 黄帆
     * @param filter
     * @return
     * @desc 根据输入的关键字创建文件过滤器，支持以下几种的模糊匹配
     *  文件前缀、后缀，文件名(模糊)
     *  若需要更多功能的匹配或者监控文件更多的属性，需要自定义FileFilter
     *
     */
    private IOFileFilter getFilter(String filter,boolean includeSubDirectories){
        if (filter==null){
            filter="";
        }
        IOFileFilter nameFilter=FileFilterUtils.nameFileFilter(filter, IOCase.INSENSITIVE);
        IOFileFilter suffixFilter=FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(filter));
        IOFileFilter prefixFilter=FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.prefixFileFilter(filter));
        IOFileFilter fileFilter=null;
        if (includeSubDirectories){//监控子目录
            IOFileFilter directories= FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
            fileFilter=FileFilterUtils.or(directories,nameFilter,suffixFilter,prefixFilter);
        }else{
            fileFilter=FileFilterUtils.or(nameFilter,suffixFilter,prefixFilter);
        }

        return fileFilter;
    }
    @Override
    public boolean UnRegisterFolderWatcher(String path, String filter) throws RPCApplicationException, TException {
        logger.info("unRegister folder watcher ,path:"+path+",filter:"+filter);
        synchronized (o){
            FolderWatcherInfo watcherInfo= null;
            try {
                watcherInfo = loadFromFIle();
            } catch (IOException e) {
                logger.error(e);
                e.printStackTrace();
            }
            if (watcherInfo.getItemList()!=null){
                List<FolerWatcherItem> list=watcherInfo.getItemList();
                list.forEach(e-> {
                    if (e.getPath()!=null&&e.getPath().equals(path)){
                        list.remove(e);
                    }
                });
                saveIntoFile(watcherInfo);
            }
            String key=path+filter;
            logger.info("unregister file system watcher:"+key);
            if (!registedWathersMap.containsKey(key)){
                return false;
            }
            FileAlterationMonitor monitor=registedWathersMap.get(key);
            try {
                monitor.stop();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
           // monitor.getObservers().iterator().next().getListeners().iterator().next().;
            registedWathersMap.remove(key);
            return true;
        }
    }

    @Override
    public boolean UnRegisterAllFolderWatcher() throws RPCApplicationException, TException {
        logger.info("unregister all folder watcher");
        synchronized (o){
            FolderWatcherInfo watcherInfo=new FolderWatcherInfo();
            saveIntoFile(watcherInfo);
            if (registedWathersMap!=null&&(!registedWathersMap.isEmpty())){
                try {
                    for (Map.Entry<String,FileAlterationMonitor> entry:registedWathersMap.entrySet()){
                        entry.getValue().stop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            registedWathersMap.clear();
            return  true;
        }
    }

    @Override
    public String GetNMPSN() throws RPCApplicationException, TException {
        logger.info(">>>>>>get NMPSN");
        try {
            return HardwareInfo.GetInstance().getSN();
        }catch (Exception e){
            logger.error(e);
            e.printStackTrace();
            throw new RPCApplicationException("SN号获取异常"+e.getMessage());
        }
    }

    @Override
    public boolean SetUpgradeAddress(String ip) throws RPCApplicationException, TException {
        logger.info("set upgrade address");
        String path=System.getProperty("user.dir").concat("\\nuc_ip.txt");
        File file=new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            String str=ip.toString()+"\r\n";
            FileOutputStream out=new FileOutputStream(file,true);//true 表示在结尾追加
            FileChannel channel=out.getChannel();
            ByteBuffer buffer=ByteBuffer.wrap(str.getBytes());
            buffer.clear();
            buffer.put(str.getBytes());
            buffer.flip();//将写模式转变为读模式
            while (buffer.hasRemaining()){
                channel.write(buffer);
            }
            channel.close();
            out.flush();
            out.close();
        }catch (Exception e){
            logger.error(e);
            throw new RPCApplicationException(e.getMessage());
        }
        return true;
    }

    @Override
    public SystemStatus GetSystemStatus() throws RPCApplicationException, TException {
        logger.info("Get SystemStatus");
        SystemStatus result=new SystemStatus();
        try {
            SystemInfo si=new SystemInfo();
            result.setDiskCapacity_Total(si.getTotalDisk());
            result.setDiskCapacity_Used(result.getDiskCapacity_Total()-si.getFreeDisk());
            result.setMemoryCapacity_Total(si.getTotalMemory());
            result.setMemoryCapacity_Used(result.getMemoryCapacity_Total()-si.getFreeMemory());
            result.setCPU_Percentage(si.getCpuRatio());
          //  result.setCurrentProcCPU(si.getProcessPerform("NMPConsole"));
            List<DiskInfo> list= si.getDiskDetails();
            if (list!=null){
                result.DiskInfos=list;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<Map<String, String>> GetCPUInfo() throws RPCApplicationException, TException {
        logger.info(">>>get cpu info");
        SystemInfo si=new SystemInfo();
        List<Map<String, String>> list=null;
        try {
            list=si.getSystemInfo();
        } catch (SigarException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return list;
    }

    @Override
    public String GetProgramVersion() throws RPCApplicationException, TException {
        return ConfigUtil.getProperty("program_version");
    }

    @Override
    public List<ProcStatus> GetProcStatus(String processName) throws RPCApplicationException, TException {
        List<ProcStatus> result=new ArrayList<>();
        SystemInfo si=new SystemInfo();
        try {
            result=si.getProcessStatus(processName);
            if (result.size()==0){
                throw new RPCApplicationException("找不到相关进程");
            }
        } catch (SigarException e) {
            e.printStackTrace();
            throw new RPCApplicationException(e.getMessage());
        }
        return result;
    }

    private FolderWatcherInfo loadFromFIle() throws IOException {
        logger.info("LoadFrom FolderWatcherInfo");
        String path = System.getProperty("user.dir").concat("\\watcherCallback\\folderWatcher.json");
        File file = new File(path);
        if (file.exists()){
            String str = FileUtil.readFile(path);
            JSONObject jsonObject=new JSONObject().fromObject(str);
            FolderWatcherInfo watcherInfo= (FolderWatcherInfo) JSONObject.toBean(jsonObject,FolderWatcherInfo.class);
            return watcherInfo;
        }
        return new FolderWatcherInfo();

    }

    private void restoreWatchers(FolderWatcherInfo watcherInfo){
        if (watcherInfo==null){
            return;
        }
        logger.info(">>>>>restore watchers");
        if (!StringUtils.isEmpty(watcherInfo.getNotifyIP())){
            this.notifyIP=watcherInfo.getNotifyIP();
            this.notifyPort=watcherInfo.getNotifyPort();
            if (watcherInfo.getItemList()!=null){
                List<FolerWatcherItem> list=watcherInfo.getItemList();
                for (int i=0;i<list.size();i++){
                    String path=list.get(i).getPath();
                    String filter=list.get(i).getFilter();
                    boolean includeSub=list.get(i).isIncludeSub();

                    String key=path+filter;

                    if (!new File(path).isDirectory()){
                        logger.error(">>>>the directory is not exist:"+path);
                        continue;
                    }
                    /*
                     *创建监听器
                     */
                    //轮询间隔
                    long interval = TimeUnit.SECONDS.toMillis(1);
                    //使用过滤器
                    FileAlterationObserver observer=new FileAlterationObserver(new File(path),getFilter(filter,true));
                    observer.addListener(new FileListener());
                    FileAlterationMonitor monitor=new FileAlterationMonitor(interval,observer);
                    try {
                        monitor.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                    registedWathersMap.put(key,monitor);
                }

            }
        }
    }
    private void fileWatcherCallback(String fullname,WatcherChangeTypes type){
        System.out.println(222);
        try {
            logger.info(">>>>fileWatcherCallback,fullname:"+fullname+",type:"+type.toString());
            if (StringUtils.isEmpty(notifyIP)||notifyPort==0){
                return;
            }
            //方式一
            TTransport transport = new TSocket(notifyIP,notifyPort);
            System.out.println(">>>IP="+notifyIP+">>>>Port="+notifyPort);
            TProtocol protocol=new TCompactProtocol(transport);
           // TFramedTransport t=new TFramedTransport(transport);
            TMultiplexedProtocol multiplexedProtocol=new TMultiplexedProtocol(protocol,"com.fiberhome.nmpDataBusPlugin.thrift.FileService");
            System.out.println(">>>>>service name>>>>>"+this.javaFileServiceName);
            FileService.Client client=new FileService.Client(multiplexedProtocol);



            //方式二 已测试 可行
           /* TTransport transport=new TSocket(notifyIP,notifyPort);
            TProtocol protocol=new TBinaryProtocol(transport);
            FileService.Client client =new FileService.Client(protocol);
            System.out.println(">>>>>>>>>>>>>B开启客户端>>>>>>>>>>>>");
            */

            transport.open();
            FileState fs;
            switch (type){
                case renamed:
                    fs=FileState.renamed;
                    break;
                case changed:
                    fs=FileState.changed;
                    break;
                case deleted:
                    fs=FileState.deleted;
                    break;
                case created:
                    fs=FileState.created;
                    break;
                default:
                    return;
            }
            boolean flag=client.recvFileState(fullname,fs);
            System.out.println(">>>>>>>>>>>>>B关闭客户端>>>>>>>>>>>>and  flag="+flag);
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }
    }

    private void saveIntoFile(FolderWatcherInfo watcherInfo){
        logger.info("save folder watcher info into file ");
        String dir = System.getProperty("user.dir").concat("\\watcherCallback\\folderWatcher.json");
        System.out.println(dir);
        try {
            File file=new File(dir);
            if (!file.exists()){
                file.createNewFile();
            }
            JSONObject jsonObject=JSONObject.fromObject(watcherInfo);
            String str=jsonObject.toString()+"\r\n";
            FileOutputStream out=new FileOutputStream(file,true);//true 表示在结尾追加
            FileChannel channel=out.getChannel();
            ByteBuffer buffer=ByteBuffer.wrap(str.getBytes());
            buffer.clear();
            buffer.put(str.getBytes());
            buffer.flip();//将写模式转变为读模式
            while (buffer.hasRemaining()){
                channel.write(buffer);
            }
            channel.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
