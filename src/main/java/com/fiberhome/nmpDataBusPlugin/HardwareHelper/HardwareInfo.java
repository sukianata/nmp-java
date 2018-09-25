package com.fiberhome.nmpDataBusPlugin.HardwareHelper;

import com.fiberhome.nmpDataBusPlugin.util.DesUtil;
import com.fiberhome.nmpDataBusPlugin.util.HardWareUtils;
import sun.nio.cs.US_ASCII;

/**
 * @author 黃帆
 * @date 2018/9/21 14:39
 */
public final class HardwareInfo {

    private static String sSN;
    private static String sCpuID;
    private static String sHardDiskID;

    /*
     * 单例模式
     */
    private static volatile HardwareInfo instance = null;

    private HardwareInfo() {
    }

    public static HardwareInfo GetInstance() {
        if (instance == null) {
            synchronized (HardwareInfo.class) {
                if (instance == null) {
                    instance = new HardwareInfo();
                }
            }
        }
        return instance;
    }

    private String CpuID;

    private String HardDiskID;

    private String SN;

    public String getCpuID() {
        return sCpuID == null ? GetCpuID():sCpuID;
    }

    public String getHardDiskID() {
        return sHardDiskID==null?GetHardDiskID():sHardDiskID;
    }

    public String getSN() {
        return sSN==null?GetSN(CpuID,HardDiskID):sSN;
    }

    private static String GetCpuID() {
        return HardWareUtils.getCPUSerial();
    }


    //取第一块硬盘编号
    private static String GetHardDiskID() {
       return HardWareUtils.getHardDiskSN("c");
    }

    private static String GetSN(String sCpuID, String sHardDiskID) {
        return DesUtil.encrypt(GetCpuID(),new US_ASCII(),GetHardDiskID().substring(0,8));
    }
}