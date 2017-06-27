package com.bawei.wangxueshi.myretrofit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {


    public static void main(String [] args) {
        final LianJia lianJia=new Fangdong();
        /**
         *
         * getClassLoader 类加载器
         * new Class[]{Lianjia.class} 固定的
         *InvocationHandler
         */
        LianJia object = (LianJia) Proxy.newProxyInstance(LianJia.class.getClassLoader(), new Class[]{LianJia.class}, new InvocationHandler() {
            @Override                   //method 代理类的方法   args 代理的方法的参数
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                System.out.println("method = " + method);

                System.out.println("args = " + args[0]);

               Object object =   method.invoke(lianJia,args);

                return object;
            }
        });
        object.kanfang(10);

        object.zufang(5);



    }


}