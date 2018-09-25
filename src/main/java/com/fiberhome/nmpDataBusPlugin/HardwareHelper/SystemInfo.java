package com.fiberhome.nmpDataBusPlugin.HardwareHelper;

import com.fiberhome.nmpDataBusPlugin.thrift.DiskInfo;
import com.fiberhome.nmpDataBusPlugin.thrift.ProcStatus;
import com.fiberhome.nmpDataBusPlugin.util.HardWareUtils;
import com.fiberhome.nmpDataBusPlugin.util.SigarUtil;
import org.hyperic.sigar.SigarException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 黃帆
 * @date 2018/9/25 8:53
 */
public class SystemInfo {

    /*
     * 获取当前总内存
     */
    public long getTotalMemory() throws SigarException {
        return HardWareUtils.getTotalMemory();
    }

    /**
     * 获取可用内存
     */
    public long getFreeMemory() throws SigarException {
        return HardWareUtils.getFreeMemory();
    }

    /**
     * 获取总磁盘大小
     */
    public long getTotalDisk(){
        return HardWareUtils.getTotalDisk();
    }

    /**
     * 获取可用磁盘大小
     * @return
     */
    public long getFreeDisk(){
        return HardWareUtils.getFreeDisk();
    }

    /**
     * 获取cpu使用率
     * @return
     */
    public double getCpuRatio() throws SigarException {
        return HardWareUtils.getCpuRatio();
    }

    /**
     * 获取某个进程cpu使用率
     * @return
     */
    public double getProcessPerform(String processName) throws SigarException {
        return HardWareUtils.getProcessPerform(processName);
    }

    /**
     * 获取磁盘信息
     * @return
     */
    public List<DiskInfo> getDiskDetails() {
        List<DiskInfo> list=new ArrayList<>();
        File[] files=File.listRoots();
        for (File file:files){
            DiskInfo info=new DiskInfo();
            info.setName(file.getName());
            info.setDescription("");
            info.setFreeSize(file.getFreeSpace());
            info.setTotalSize(file.getTotalSpace());
            list.add(info);
        }
        return list;
    }

    /**
     * 获取系统信息
     * @return
     */
    public List<Map<String,String>> getSystemInfo() throws SigarException {
        return SigarUtil.getCPUInfo();
    }

    /**
     *查看进程状态
     * @return
     */
    public List<ProcStatus> getProcessStatus(String processName) throws SigarException {
        return SigarUtil.getProcessStatus(processName);
    }
}
