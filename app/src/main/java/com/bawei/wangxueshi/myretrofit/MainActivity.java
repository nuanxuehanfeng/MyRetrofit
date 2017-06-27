package com.bawei.wangxueshi.myretrofit;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.wangxueshi.myretrofit.cookie.CookiesManager;
import com.bawei.wangxueshi.myretrofit.me.MeLoginBean;
import com.bawei.wangxueshi.myretrofit.me.MeLoginService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends Activity {

    @BindView(R.id.main_btn_get)
    Button mainBtnGet;
    @BindView(R.id.main_btn_post)
    Button mainBtnPost;
    @BindView(R.id.main_btn_up_image)
    Button mainBtnUpImage;
    @BindView(R.id.main_btn_up_url)
    Button mainBtnUrl;
    @BindView(R.id.main_btn_post_String)
    Button mainBtnPostString;
    @BindView(R.id.main_btn_get_String)
    Button mainBtnGetString;
    @BindView(R.id.main_btn_get_down)
    Button mainBtnGetDown;

    static  String username="11111111111";
    static  String password="1";
    static  String postkey="1503d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    //    @OnClick(R.id.main_btn_get)
//    public void get(View v){
//        System.out.println("\"dian\" = " + "dian");
//    }
    @OnClick({R.id.main_btn_get, R.id.main_btn_post,R.id.main_btn_up_image, R.id.main_btn_up_url,R.id.main_btn_post_String,R.id.main_btn_get_String,R.id.main_btn_get_down})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_get:
                //创建对象，
                Retrofit retrofit = new Retrofit.Builder()
                        //主域名  .com/结尾，注意有/
                        .baseUrl("http://qhb.2dyt.com/")
                        //返回的内容  用Gson 转化bean
                        .addConverterFactory(GsonConverterFactory.create()) // 加上这句话
                        //    .addConverterFactory(ScalarsConverterFactory.create())
                        // 改变回调的线程
//                .callbackExecutor(exe)
//                .callFactory(client)
//                        .client(client)
//                检测接口中定义的所有方法的注解是否ok
//                  .validateEagerly(false)
//                .addCallAdapterFactory()
                        .build();
                //这个是重点  属于动态代理 要仔细研究下
                MeLoginService meLoginService1 = retrofit.create(MeLoginService.class);
                //第一种  有参数的get请求
                // getQuery1(meLoginService1);
                //第二种 无参数的get 请求
              //  getQuery2WuCan(meLoginService1);
                //第三种   将参数封装到 map集合中
                //  getQueryMap(meLoginService1);
                //第四种
              // getPath(meLoginService1);

                Map map1 = new HashMap();
                map1.put("username","11111111111");
                map1.put("password","1");
                map1.put("postkey","1503d");
                Call string = meLoginService1.getString("http://qhb.2dyt.com/Bwei/login", map1);
                meLoginService1.getString("http://qhb.2dyt.com/Bwei/login",map1).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        System.out.println("response = " + response.body());

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                break;

            case R.id.main_btn_post:
                //session   启动一次网页 ，它每次访问都是一个session  若关闭，第二次启动，session就变了
                //cookie
                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();
                ExecutorService exe = Executors.newCachedThreadPool() ;
                Retrofit rePost = new Retrofit.Builder()
                        //主域名  .com/结尾，注意有/
                        .baseUrl("http://qhb.2dyt.com/")
                        .callbackExecutor(exe)
                                                  //添加一个线程池  此线程为子线程，运行环境为子线程，这时：吐司会报错 了
                                                  //若不加这个  则运行在主线程 可以吐司
                                                  //改变回调的线程，  默认在主线程

                        //返回的内容  用Gson 转化bean
                        .addConverterFactory(GsonConverterFactory.create())
                          //下面这两个方法 不能一起调用，因为他们的底层功能是一样的
                     //   .callFactory(client)
                        .client(client)    //若没有调用这个方法，程序每次都会创建一个okhttp
                                       //即没有配置，则每次创建一个okhtpp，也就每次都创建一个用户
                                      //如果配置 了 ，，那就调用这一个用户了

                       //  检测接口中定义的所有方法的注解是否ok   true:会自动检测接口中所有的方法和注释  是否有错误   ，false则不检测了
                        .validateEagerly(false)
                        .build();
                MeLoginService meLoginService = rePost.create(MeLoginService.class);

                //post  请求
                //第一种
                post1(meLoginService);
                //第二种
              //  postMap(meLoginService);
              //  第三种
               // postBody(meLoginService);
                //  第四种
                // postBody(meLoginService); 第四种和第三种的区别 在于添加头了。而这个头是再 interfa 中添加的





                break;
            case R.id.main_btn_up_image:
                toPic();
                break;
            case  R.id.main_btn_up_url:
                postUrl();
                break;
            case  R.id.main_btn_post_String:
                postString();


                break;
            case  R.id.main_btn_get_String:
                getString();
                break;
            case  R.id.main_btn_get_down:

                final String path =  Environment.getExternalStorageDirectory() +"/aa/";
                System.out.println("path = " + path);

                final String url = "http://gdown.baidu.com/data/wisegame/41a04ccb443cd61a/QQ_692.apk" ;
                 Retrofit retrofit2 = new Retrofit.Builder()
                //主域名
                .baseUrl("http://www.2dyt.com/")
                    //返回的内容  用Gson 转化bean
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // 改变回调的线程
//                .callFactory(client)
//                检测接口中定义的所有方法的注解是否ok
                    .validateEagerly(false)
//                .addCallAdapterFactory()
                    .build();
                //动态代理
                MeLoginService loginService = retrofit2.create(MeLoginService.class);
                loginService.downloadFile("http://gdown.baidu.com/data/wisegame/41a04ccb443cd61a/QQ_692.apk").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,final Response<ResponseBody> response) {
                        new Thread(new Runnable() {  //耗时操作在子线程
                            @Override
                            public void run() {
                                InputStream inputStream = null;
                                FileOutputStream fileOutputStream = null;
                                File file = null ;
                                final String[] arrays =  url.split("/");
                                try {
                                    long total = response.body().contentLength();
                                    long sum = 0;
                                    inputStream = response.body().byteStream();  // 获得流
                                    int len = 0;
                                    byte[] buff = new byte[1024];
                                    file = new File(path);
                                    if (!file.exists()) {
                                        boolean result =  file.mkdirs();
                                        System.out.println("result = " + result);
                                    }
                                    file = new File(file.getPath(),arrays[arrays.length - 1]);
                                    if(file.exists()){
                                        file.delete();
                                    }
                                    file.createNewFile();

                                    fileOutputStream = new FileOutputStream(file);

                                    while ((len = inputStream.read(buff)) != -1) {
                                        fileOutputStream.write(buff, 0, len);
                                        sum += len;
                                        int progress = (int) (sum * 1.0f / total * 100);

                                        System.out.println("progress = " + progress);
                                    }
                                    fileOutputStream.flush();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        inputStream = null;
                                        fileOutputStream = null;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });

                break;
        }
    }

    private void postString() {
        Retrofit retrofit2 = new Retrofit.Builder()
                //主域名
                .baseUrl("http://www.2dyt.com/")
                //返回的内容  用Gson 转化bean
                .addConverterFactory(ScalarsConverterFactory.create())
                // 改变回调的线程
//                .callbackExecutor(exe)
//                .callFactory(client)
                // .client(client)
//                检测接口中定义的所有方法的注解是否ok
                .validateEagerly(false)
//                .addCallAdapterFactory()
                .build();
        //动态代理
        MeLoginService loginService = retrofit2.create(MeLoginService.class);
        Map map = new HashMap();
        map.put("username","11111111111");
        map.put("password","1");
        map.put("postkey","1503d");
        loginService.postString("http://qhb.2dyt.com/Bwei/login",map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                System.out.println("response = " + response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getString() {
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();

          ExecutorService exe =  Executors.newCachedThreadPool() ;

//        http://qhb.2dyt.com/Bwei/long
        Retrofit retrofit2 = new Retrofit.Builder()
                //主域名
                .baseUrl("http://www.2dyt.com/")
                //返回的内容  用Gson 转化bean
                .addConverterFactory(ScalarsConverterFactory.create())
                // 改变回调的线程
                .callbackExecutor(exe)
//                .callFactory(client)
                 .client(client)
//                检测接口中定义的所有方法的注解是否ok
                .validateEagerly(false)
//                .addCallAdapterFactory()
                .build();
        //动态代理
        MeLoginService loginService = retrofit2.create(MeLoginService.class);
        Map map = new HashMap();
        map.put("username","11111111111");
        map.put("password","1");
        map.put("postkey","1503d");
        loginService.getString("http://qhb.2dyt.com/Bwei/login",map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                System.out.println("response = " + response.body());

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void postUrl() {
        //为什么这个client1是null呢
        //     OkHttpClient client1 = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();
        ExecutorService exe1 =  Executors.newCachedThreadPool() ;
// http://qhb.2dyt.com/Bwei/long
        Retrofit retrofit1 = new Retrofit.Builder()
                //主域名
                .baseUrl("http://www.2dyt.com/")
                //返回的内容  用Gson 转化bean
                .addConverterFactory(GsonConverterFactory.create())
                //返回的内容  转化成 String
              //.addConverterFactory(ScalarsConverterFactory.create())
                // 改变回调的线程
       .callbackExecutor(exe1)
   //    .callFactory(client1)
          //      .client(client1)
//                检测接口中定义的所有方法的注解是否ok
                .validateEagerly(false)
//                .addCallAdapterFactory()
                .build();


        //动态代理
        MeLoginService loginService =  retrofit1.create(MeLoginService.class);

        Map map1 = new HashMap();
        map1.put("username","11111111111");
        map1.put("password","1");
        map1.put("postkey","1503d");
        loginService.loginUrl("http://qhb.2dyt.com/Bwei/login",map1).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {

                System.out.println("response = " + response.body().getRet_msg());

            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }

    private void postBody(MeLoginService meLoginService) {
        meLoginService.postBody(new User(username,password,postkey)).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response = " + response.body().getRet_msg());
                //这里可能出现权限不足
                //这种 方式 是将body 包装成json 传给服务器 ，若服务器不支持 gson  就会出现权限不足
            }
            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {
            }
        });
    }

    private void postMap(MeLoginService meLoginService) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "11111111111");
        map.put("password", "1");
        map.put("postkey", "1503d");
        meLoginService.postMap(map).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response = " + response.body().getRet_msg());
                //说明在主线程
                Toast.makeText(MainActivity.this, ""+response.body().getRet_msg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }

    private void  post1(MeLoginService meLoginService) {
        meLoginService.post1(username,password,postkey).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response = " + response.body().getRet_msg());
                //在主线程
                Toast.makeText(MainActivity.this, ""+response.body().getRet_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {
            }
        });
    }

    private void getPath(MeLoginService meLoginService1) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "11111111111");
        map.put("password", "1");
        map.put("postkey", "1503d");
        //login为地址，map为参数集合
        meLoginService1.getLoginType("login",map).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response = " + response.body().getRet_msg());
            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }

    private void getQuery2WuCan(MeLoginService meLoginService1) {
        meLoginService1.getLoginWuCan().enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response = " + response.body().getRet_msg());
            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }

    private void getQueryMap(MeLoginService meLoginService1) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "11111111111");
        map.put("password", "1");
        map.put("postkey", "1503d");
        meLoginService1.getLoginMap(map).enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {
                System.out.println("response.body().getRet_msg() = " + response.body().getRet_msg());
            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }

    private void getQuery1(MeLoginService meLoginService1) {
        //第一种
        meLoginService1.getLogin("11111111111", "1", "1503d").enqueue(new Callback<MeLoginBean>() {
            @Override
            public void onResponse(Call<MeLoginBean> call, Response<MeLoginBean> response) {

                System.out.println("response = " + response.body().getRet_msg());
            }

            @Override
            public void onFailure(Call<MeLoginBean> call, Throwable t) {

            }
        });
    }



    public static final int IMAGE = 1 ;
    public static final int CAMERA = 2 ;

    public static String photoCacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Bwei";
//与图片有关
    public void toPic(){

        if(!new File(photoCacheDir).exists()){
            new File(photoCacheDir).mkdirs();
        }
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 1);

    }
    //与图片有关
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    try {
                        // 相册
                        if (data == null)// 如果没有获取到数据
                            return;
                        Uri originalUri = data.getData();
                        //文件大小判断
                        if (originalUri != null) {
                            File file = null;
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor actualimagecursor = managedQuery(originalUri, proj, null, null, null);
                            if (null == actualimagecursor) {
                                if (originalUri.toString().startsWith("file:")) {
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                    if(!file.exists()){
                                        //地址包含中文编码的地址做utf-8编码
                                        originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(),"UTF-8"));
                                        file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                    }
                                }
                            } else {
                                // 系统图库
                                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                actualimagecursor.moveToFirst();
                                String img_path = actualimagecursor.getString(actual_image_column_index);
                                if (img_path == null) {
                                    InputStream inputStrean = getContentResolver().openInputStream(originalUri);
                                    file = new File(photoCacheDir+"/aa.jpg");
                                    if(!file.exists()){
                                        file.createNewFile();
                                    }
                                    System.out.println(" - " + file.exists());
                                    FileOutputStream outputStream = new FileOutputStream(file);

                                    byte[] buffer = new byte[1024];
                                    int len = 0;
                                    while ((len = inputStrean.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, len);
                                    }
                                    outputStream.flush();

                                    if (inputStrean != null) {
                                        inputStrean.close();
                                        inputStrean = null;
                                    }

                                    if (outputStream != null) {
                                        outputStream.close();
                                        outputStream = null;
                                    }
                                } else {
                                    file = new File(img_path);
                                }
                            }
                            String camerFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                            File newfilenew = new File(photoCacheDir,camerFileName);
//                            if (!newfilenew.exists()) {
//                                newfilenew.createNewFile();
//                            }
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outStream = new FileOutputStream(newfilenew);
                            try {
                                byte[] buffer = new byte[1024];
                                int len = 0;
                                while ((len = inputStream.read(buffer)) != -1) {
                                    outStream.write(buffer, 0, len);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                try {
                                    inputStream.close();
                                    outStream.close();
                                    inputStream = null;
                                    outStream = null;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            uploadFile(newfilenew.toString());
//                            uploadFile(newfilenew);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

            }


        }
    }
    //上传图片文件  参数为图片的绝对路径
    private void uploadFile(String string) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://qhb.2dyt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MeLoginService loginservice =  retrofit.create(MeLoginService.class);
        String [] arr =  string.split("\\|");
        //创建请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),new File(string));
//       //多参数体
//        MultipartBody multiBody = new MultipartBody.Builder()
//                //     name filename body
//                .addFormDataPart("1111111",arr[arr.length-1],requestBody).build();
//
//        loginservice.uploadPhoto(multiBody).enqueue(new Callback<Ret>() {
//            @Override
//            public void onResponse(Call<Ret> call, Response<Ret> response) {
//                try {
//                    System.out.println("response = " + response.body().getPath());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<Ret> call, Throwable t) {
//
//            }
//        });
        //与上一个方法不同，这里是通过    MultipartBody.Part  来  上传参数的  key（与服务器协商）,filename,体
        MultipartBody.Part part =  MultipartBody.Part.createFormData("image",arr[arr.length-1],requestBody);

        loginservice.uploadPhoto1(part).enqueue(new Callback<Ret>() {
            @Override
            public void onResponse(Call<Ret> call, Response<Ret> response) {
                System.out.println("response = " + response.body().getPath());
            }

            @Override
            public void onFailure(Call<Ret> call, Throwable t) {

            }
        });
    }
}
