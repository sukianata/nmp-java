package com.fiberhome.nmpDataBusPlugin;

/**
 * @author 黃帆
 * @date 2018/9/18 10:43
 */
public enum   WatcherChangeTypes {
    changed(1),created(2),deleted(3),renamed(4);

    private  int value;

    private WatcherChangeTypes(int value){
        this.value=value;
    }
  /*  public static final int changed=1;

    public static final int created=2;

    public static final int deleted=3;

    public static final int renamed=4;*/
}
