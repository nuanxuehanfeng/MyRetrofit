package com.bawei.wangxueshi.myretrofit.proxy;

/**
 * Created by Administrator on 2017/6/16.
 */

public interface LianJia {
    //参数为几个人看房
    public  void kanfang(int persion);
    //参数为看房的人，返回值为交的租金
    public  String zufang(int persion);
}
