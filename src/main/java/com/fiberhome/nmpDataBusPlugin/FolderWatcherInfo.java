package com.fiberhome.nmpDataBusPlugin;

import java.util.List;

public class FolderWatcherInfo {

    private  String notifyIP;

    private  int notifyPort;

    private List<FolerWatcherItem> itemList;

    //getters and setters

    public String getNotifyIP() {
        return notifyIP;
    }

    public void setNotifyIP(String notifyIP) {
        this.notifyIP = notifyIP;
    }

    public int getNotifyPort() {
        return notifyPort;
    }

    public void setNotifyPort(int notifyPort) {
        this.notifyPort = notifyPort;
    }

    public List<FolerWatcherItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FolerWatcherItem> itemList) {
        this.itemList = itemList;
    }
}
