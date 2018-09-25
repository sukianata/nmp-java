package com.fiberhome.nmpDataBusPlugin;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * @author 黃帆
 * @date 2018/9/18 10:37
 * @description: 文件變化監聽器
 *  文件监控的原理如下：
 *      由文件监控类FileAlterationMonitor中的线程不停的扫描文件观察器FileAlterationObserver，
 *      如果有文件的变化，则根据相关的文件比较器，判断文件时新增，还是删除，还是更改。（默认为1000毫秒执行一次扫描）
 *
 */
public class FileListener extends FileAlterationListenerAdaptor {

    private Logger logger = LogManager.getLogger(FileListener.class);

    private  Object o=new Object();
    /**
     * 文件创建执行
     */
    public void onFileCreate(File file) {
        System.out.println("[新建]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.created));
            }
        }
    }

    /**
     * 文件创建修改
     */
    public void onFileChange(File file) {
        System.out.println("[修改]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.changed));
            }
        }
    }

    /**
     * 文件删除
     */
    public void onFileDelete(File file) {
        System.out.println("[删除]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.deleted));
            }
        }
    }

    /**
     * 目录创建
     */
    public void onDirectoryCreate(File file) {
        System.out.println("[新建]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.created));
            }
        }
    }

    /**
     * 目录修改
     */
    public void onDirectoryChange(File file) {
        System.out.println("[修改]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.changed));
            }
        }
    }

    /**
     * 目录删除
     */
    public void onDirectoryDelete(File file) {
        System.out.println("[删除]:" + file.getAbsolutePath());
        Map<String, FileWatcherEventArgs> map=DataServiceImp.lazyFileEventsMap;
        if (!map.containsKey(file.getAbsolutePath())){
            synchronized (o){
                DataServiceImp.lazyFileEventsMap.put(file.getAbsolutePath(),new FileWatcherEventArgs(file.getAbsolutePath(), WatcherChangeTypes.deleted));
            }
        }
    }
}

