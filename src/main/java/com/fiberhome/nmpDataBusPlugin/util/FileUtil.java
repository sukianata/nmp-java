package com.fiberhome.nmpDataBusPlugin.util;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author 黃帆
 * @date 2018/9/18 11:39
 */
public class FileUtil {

    private static final Logger logger= LogManager.getLogger(FileUtil.class);
    /*
     * read file
     */
    public static String readFile(String path) throws IOException {
        File file = new File(path);
        StringBuilder sb=new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String temp=null;
            //使用socket之类的数据流时，要避免使用readLine()，以免为了等待一个换行/回车符而一直阻塞
            while ((temp=bufferedReader.readLine())!=null){
                sb.append(temp);//此处不能bufferedReader.readLine()
            }
        } catch (FileNotFoundException e) {
            logger.error(e);
            throw  e;
        } catch (IOException e) {
            logger.error(e);
            throw  e;
        }
        return sb.toString();
    }

    public static void copyfile(String oldpath,String newpath) throws IOException {
        File file =new File(newpath);
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error(e);
            throw  e;
        }
        try (FileChannel inputChannel = new FileInputStream(oldpath).getChannel();
                 FileChannel outputChannel=new FileOutputStream(newpath).getChannel()) {
                outputChannel.transferFrom(inputChannel,0,inputChannel.size());
            } catch (IOException e) {
                logger.error(e);
                throw  e;
            }
    }
}
