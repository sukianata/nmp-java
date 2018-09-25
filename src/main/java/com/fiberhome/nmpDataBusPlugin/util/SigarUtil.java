package com.fiberhome.nmpDataBusPlugin.util;

import com.fiberhome.nmpDataBusPlugin.HardwareHelper.ProcessInfo;
import com.fiberhome.nmpDataBusPlugin.thrift.ProcStatus;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黃帆
 * @date 2018/9/25 10:07
 */
public class SigarUtil {

    private static Sigar sigar =new Sigar();


    public static Sigar getInstance() {
        return sigar;
    }
    public static  double getCpuRatio() throws SigarException {
        return  sigar.getCpuPerc().getSys();
    }
    /**
     * 获取某个进程的cpu使用率
     */
    public static double getProcessPerform(String processName) throws SigarException {
        List<ProcessInfo> list=getProcessInfo(processName);
        double rt=0;
        rt= sigar.getProcCpu(list.get(0).getPid()).getPercent();
        return rt;
    }

    private static List<ProcessInfo> getProcessInfo(String processName){
        Ps ps = new Ps();
        List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();
        try {
            long[] pids = sigar.getProcList();
            for(long pid : pids){
                List<String> list = ps.getInfo(sigar, pid);
//                    /*switch(i){
//                        case 0 : info.setPid(list.get(0)); break;
//                        case 1 : info.setUser(list.get(1)); break;
//                        case 2 : info.setStartTime(list.get(2)); break;
//                        case 3 : info.setMemSize(list.get(3)); break;
//                        case 4 : info.setMemUse(list.get(4)); break;
//                        case 5 : info.setMemhare(list.get(5)); break;
//                        case 6 : info.setState(list.get(6)); break;
//                        case 7 : info.setCpuTime(list.get(7)); break;
//                        case 8 : info.setName(list.get(8)); break;
//                    }*/
                if (list.get(8).contains(processName)){
                    System.out.println(list.get(8));
                    ProcessInfo info = new ProcessInfo();
                    info.setPid(list.get(0));
                    info.setUser(list.get(1));
                    info.setStartTime(list.get(2));
                    info.setMemSize(list.get(3));
                    info.setMemUse(list.get(4));
                    info.setMemhare(list.get(5));
                    info.setState(list.get(6));
                    info.setCpuTime(list.get(7));
                    info.setName(list.get(8));
                    processInfos.add(info);
                }
        }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return processInfos;
    }

    /**
     * 获取cpu信息
     * @return
     * @throws SigarException
     */
    public static List<Map<String,String>> getCPUInfo() throws SigarException {
        List<Map<String,String>> list=new ArrayList<>();
        CpuInfo infos[] =sigar.getCpuInfoList();
//        CpuPerc cpuList[]=sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
             Map<String,String> mm=new HashMap<>();
            CpuInfo info = infos[i];
            mm.put("model",info.getModel());
            mm.put("vendor",info.getVendor());
            mm.put("cacheSize",String.valueOf(info.getCacheSize()));
            mm.put("corePerSocket",String.valueOf(info.getCoresPerSocket()));
            mm.put("mhz",String.valueOf(info.getMhz()));
            mm.put("totalCores",String.valueOf(info.getTotalCores()));
            mm.put("totalSocket",String.valueOf(info.getTotalSockets()));
            list.add(mm);
        }
        return list;
    }

    public static List<ProcStatus> getProcessStatus(String processName) throws SigarException {
        List<ProcessInfo> list=getProcessInfo(processName);
        System.out.println(">>>>系统进程：");
        List<ProcStatus> rt=new ArrayList<>();
        for(ProcessInfo info:list){
            System.out.println(info.getName());
            if (info.getName().contains(processName)){
                ProcStatus st=new ProcStatus();
                double d1=sigar.getProcCpu(info.getPid()).getPercent();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //占用多少cpu
                st.setProcCPU(sigar.getProcCpu(info.getPid()).getPercent());
                //占用多少内存
                st.setMemoryCost(sigar.getProcMem(info.getPid()).getSize());
                st.setPath(info.getName());//此处name即path
                if (!rt.contains(st)){
                    rt.add(st);
                }
            }
        }
        return rt;
    }

    /**
     * 获取系统内存
     * @return
     */
    public static long getTotalMemory() throws SigarException {
        return getInstance().getMem().getTotal();
    }

    /**
     * 获取可用内存
     * @return
     */
    public static long getFreeMemory() throws SigarException {
        return getInstance().getMem().getActualFree();
    }

    public static void main(String[] args) throws SigarException {
        long pid=8848L;
        System.out.println(sigar.getProcMem(pid).toMap());
        System.out.println(Double.valueOf(String.valueOf(sigar.getProcMem(pid).toMap().get("Size")))/(1024*1024*1024));
        System.out.println(sigar.getMem().getTotal()/(1024*1024*1024)+"G");
        Ps ps=new Ps();
        List<String> list=ps.getInfo(sigar, pid);
        list.forEach(e->{
            System.out.println(e);
        });

    }
}
