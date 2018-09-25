package com.fiberhome.nmpDataBusPlugin;

public class FileWatcherEventArgs  {

    private  String path;
    /*
     * 此处需要监控目录文件的创建，删除，重命名，更改
     * .net 用到了一个WatcherChangeTypes 是一个枚举类型，记录这四种操作
     * 待补充：文件变更行为枚举
     */
    private WatcherChangeTypes eventType;


    public FileWatcherEventArgs(){}

    public FileWatcherEventArgs(String path,WatcherChangeTypes type){
        this.path=path;
        this.eventType=type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public WatcherChangeTypes getEventType() {
        return eventType;
    }

    public void setEventType(WatcherChangeTypes eventType) {
        this.eventType = eventType;
    }
}
