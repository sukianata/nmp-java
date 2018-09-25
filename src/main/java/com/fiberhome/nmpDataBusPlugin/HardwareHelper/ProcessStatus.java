package com.fiberhome.nmpDataBusPlugin.HardwareHelper;

import com.fiberhome.nmpDataBusPlugin.thrift.ProcStatus;

/**
 * @author 黃帆
 * @date 2018/9/25 11:46
 */
public class ProcessStatus extends ProcStatus{
    private double memoryCost;
    private double cpu;
    private String path;

    public double getMemoryCost() {
        return memoryCost;
    }


    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    @Override
    public String getPath() {
        return path;
    }

}
