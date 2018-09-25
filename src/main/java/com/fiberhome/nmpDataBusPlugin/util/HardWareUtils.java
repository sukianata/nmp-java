package com.fiberhome.nmpDataBusPlugin.util;

import org.hyperic.sigar.SigarException;

import java.io.*;

/**
 * @author 黃帆
 * @date 2018/9/21 15:12
 */
public class HardWareUtils {

    /**
     * 获取主板序列号
     *
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取硬盘序列号
     *
     * @param drive
     *            盘符
     * @return
     */
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            System.out.println(vbs);
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取CPU序列号
     *
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String sysname=System.getProperty("os.name");
            String vbs ="";
            if (sysname.toLowerCase().contains("window")) {
                 vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                        + "Set colItems = objWMIService.ExecQuery _ \n"
                        + "   (\"Select * from Win32_Processor\") \n"
                        + "For Each objItem in colItems \n"
                        + "    Wscript.Echo objItem.ProcessorId \n"
                        + "    exit for  ' do the first cpu only! \n" + "Next \n";
                // + "    exit for  \r\n" + "Next";
            }
            if (sysname.toLowerCase().contains("linux")) {

            }

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }

    /**
     * 获取MAC地址
     */
    public static String getMac() {
        String result = "";
        try {

            Process process = Runtime.getRuntime().exec("ipconfig /all");

            InputStreamReader ir = new InputStreamReader(
                    process.getInputStream());

            LineNumberReader input = new LineNumberReader(ir);

            String line;

            while ((line = input.readLine()) != null)

                if (line.indexOf("Physical Address") > 0) {

                    String MACAddr = line.substring(line.indexOf("-") - 2);

                    result = MACAddr;

                }

        } catch (java.io.IOException e) {

            System.err.println("IOException " + e.getMessage());

        }
        return result;
    }

    /*
     * 获取系统总内存
     */
    public static long getTotalMemory() throws SigarException {
        return SigarUtil.getTotalMemory();
    }

    /**
     * 获取可用内存
     */
    public static long getFreeMemory() throws SigarException {
        return SigarUtil.getFreeMemory();
    }

    /**
     * 获取总磁盘大小
     * @return
     */
    public static long getTotalDisk(){
       File file=new File("c://");
       return file.getTotalSpace();
    }

    /**
     * 获取可用磁盘大小
     * @return
     */
    public static  long getFreeDisk(){
        File file=new File("c://");
        return file.getFreeSpace();
    }

    /**
     * 获取cpu使用率
     */
    public static double getCpuRatio() throws SigarException {
        return SigarUtil.getCpuRatio();
    }

    /**
     * 获取某个进程的cpu使用率
     */
    public static double getProcessPerform(String processName) throws SigarException {

        return SigarUtil.getProcessPerform(processName);
    }
    public static void main(String[] args) {
        System.out.println("CPU  SN:" + HardWareUtils.getCPUSerial());
        System.out.println("主板  SN:" + HardWareUtils.getMotherboardSN());
        System.out.println("C盘   SN:" + HardWareUtils.getHardDiskSN("c"));
        System.out.println("MAC  SN:" + HardWareUtils.getMac());
        //System.out.println("系统版本:"+System.setProperty("os.name","Window 10"));
       /* Properties properties=System.getProperties();
        properties.setProperty("os.name","Window 10");*/
    }

}
