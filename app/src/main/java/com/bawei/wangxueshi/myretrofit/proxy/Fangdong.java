package com.bawei.wangxueshi.myretrofit.proxy;

/**
 * Created by Administrator on 2017/6/16.
 */

public class Fangdong implements  LianJia {
    @Override
    public void kanfang(int persion) {
        System.out.println("fangdong看房persion = " + persion);
    }

    @Override
    public String zufang(int persion) {
        System.out.println("fangdong租房persion = " + persion);
        return ""+persion;
    }
}
