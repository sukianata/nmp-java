package com.fiberhome.nmpDataBusPlugin;


import com.fiberhome.nmpDataBusPlugin.thrift.NMPDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.File;
import java.util.Map;


/**
 * @author 黄帆
 */
public class XDataBus {

    private static Logger logger= LogManager.getLogger(XDataBus.class);

    private DataServiceImp dataService=null;

    private boolean isRunning =false;

    private int port;

    private int timeout;

    private boolean useBufferedSockeds=false;

    private String tempFolder = null;

    private String fileServiceName=null;

    //待补充 ，此处怀疑是第三方类
    private TServerSocket transport;

    private TServer server;

    //getters and setters
    //------start-------

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    //------end-------

    public void dispose(){
        stop();
        server = null;
        transport =null;
    }

    public void init(){

    }

    public void reStart(){
        if(isRunning){
            stop();
        }
        try {
            start();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public void setParams(Map<String,String> params){
       if (params.containsKey("port")){
           int tempPort = 1081;
           //此处需要做个处理，判断传进来的参数是否能转成int型，若不行则采用1081
           if(NumberUtils.isDigits(params.get("port"))){
                tempPort=Integer.valueOf(params.get("port"));
           }
           this.port=tempPort;
       }
       if (params.containsKey("timeout")){
           int tempTimeout=3000;
           if (NumberUtils.isDigits(params.get("timeout"))){
               tempTimeout=Integer.valueOf(params.get("timeout"));
           }
           this.timeout=tempTimeout;
       }
       //省略了bufferedSockets ，java中TServerSocket 構造函數 中沒有這個參數

       this.tempFolder=System.getProperty("user.dir").concat("\\TempFolder\\");
       if (params.containsKey("tempFolder")){
           String path=params.get("tempFolder");
           File file = new File(path);
           if (file.exists()){
                this.tempFolder=path;
           }

       }

       if (params.containsKey("fileServiceServiceName")){
           if (!StringUtils.isEmpty(params.get("fileServiceServiceName"))){
               this.fileServiceName=params.get("fileServiceServiceName");
           }
       }
    }

    public void start() throws TTransportException {

        dataService =new DataServiceImp();
        dataService.setTempFolder(this.tempFolder);
        dataService.setJavaFileServiceName(this.fileServiceName);
        //transport=new TServerSocket(port,timeout);

        System.out.println(">>>>>>>>>>>>>>B开启服务端>>>>>>>>>>>");
        TProcessor tProcessor=new NMPDataService.Processor<NMPDataService.Iface>(dataService);
        try (TServerSocket serverSocket = new TServerSocket(this.port)) {
            TServer.Args tArgs=new TServer.Args(serverSocket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server=new TSimpleServer(tArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    /*    dataService.setNotifyIP("127.0.0.1");
        TProcessor tProcessor=new NMPDataService.Processor<NMPDataService.Iface>(dataService);
        try (TServerSocket serverSocket = new TServerSocket(this.port)) {
            TServer.Args tArgs=new TServer.Args(serverSocket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server=new TSimpleServer(tArgs);
            //开启多线程处理请求
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("Start thrift service");
                        server.serve();
                    }catch (Exception e){
                        logger.error(e);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
*/

      /*  NMPDataService.Processor processor=new NMPDataService.Processor(dataService);
        server=new TThreadPoolServer(new TThreadPoolServer.Args(transport));*/

       isRunning=true;

    }
    public void stop(){
        if(server != null){
            server.stop();
            if(dataService!=null){
                try {
                    dataService.UnRegisterAllFolderWatcher();
                } catch (TException e) {
                    e.printStackTrace();
                }
            }
        }
        if(transport != null ){
            transport.close();
        }
        isRunning=false;
    }
}
