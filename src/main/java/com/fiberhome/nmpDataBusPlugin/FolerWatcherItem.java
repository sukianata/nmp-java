package com.fiberhome.nmpDataBusPlugin;

public class FolerWatcherItem {

    private boolean includeSub;

    private String path;

    private String filter;

    //getter and setter


    public boolean isIncludeSub() {
        return includeSub;
    }

    public void setIncludeSub(boolean includeSub) {
        this.includeSub = includeSub;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    //constructor
    public FolerWatcherItem(boolean includeSub,String path,String filter){
        this.includeSub=includeSub;
        this.path=path;
        this.filter=filter;
    }
}
