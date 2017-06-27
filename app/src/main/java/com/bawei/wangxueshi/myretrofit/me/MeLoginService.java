package com.bawei.wangxueshi.myretrofit.me;

import com.bawei.wangxueshi.myretrofit.Ret;
import com.bawei.wangxueshi.myretrofit.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/6/15.
 */
public interface MeLoginService {
  //get   请求
   //可以不带参数 "Bwei/login"
  // 和带固定参数 "Bwei/login?username=11111111111"
  //第一种
    @GET("Bwei/login")
    public Call<MeLoginBean> getLogin(@Query("username") String username,@Query("password") String password,@Query("postkey") String postkey);
  //第二种  // 把参数写死 了
    @GET("Bwei/login?username=11111111111&password=1&postkey=1503d")
    public Call<MeLoginBean> getLoginWuCan();
  //第三中map
    @GET("Bwei/login")
    public  Call<MeLoginBean> getLoginMap(@QueryMap Map<String,String> map);
  //表示get请求方式  Path 可以改变请求的路径 和请求的参数 但是  @Path("key") 必须和@GET("Bwei/{key}") 一致
   //第四种 可以变化的地址  这里的两个type 是自拟的，但两个必须相等，type为地址可以变化，map为参数{}前必须有Bwei/
  @GET("Bwei/{type}")
    public  Call<MeLoginBean> getLoginType(@Path("type") String type,@QueryMap Map<String,String> map);


  //post  请求

  //第一种
  @FormUrlEncoded    //用表单提交
  @POST("Bwei/login")  //post 请求    Field 请求的参数
  public   Call<MeLoginBean> post1(@Field("username") String username,@Field("password") String password,@Field("postkey") String postkey);

  //第二种
  @FormUrlEncoded
  @POST("Bwei/login")
  public   Call<MeLoginBean> postMap(@FieldMap Map<String,String> map);

  //第三种
  @Headers({"customkey:value","customkey1:value1"})  //自定义添加请求头，注意  key和value 用 :  来分割，不然会报错  每个请求头 用,号分割 多个是用{}包住
  @POST("Bwei/login")
  public   Call<MeLoginBean> postBody(@Body User user);


  //上传图片
  //    Multipart
  @Multipart   //这个注解 表示上传图片
  @POST("Bwei/upload")    //服务器 以image 接受文件    实际使用时;要与服务器沟通     MultipartBody多参数体
  public Call<Ret> uploadPhoto(@Part("image") MultipartBody body);

  @Multipart
  @POST("Bwei/upload")    //这是通过MultipartBody.Part  来传递的
  public Call<Ret> uploadPhoto1(@Part MultipartBody.Part part);


  //    @Url 这个注解 表示动态改变请求的接口地址   也就是会动态的改变.baseUrl(这里的内容)
  @FormUrlEncoded
  @POST
  public Call<MeLoginBean> loginUrl(@Url String url, @FieldMap Map<String,String> map);

  @GET
  public Call<String> getString(@Url String url,@QueryMap Map<String,String> map);

// 返回值为  String
  @FormUrlEncoded
  @POST
  public Call<String> postString(@Url String url,@FieldMap Map<String,String> map);

  // 下载
  @Streaming   //这个注解是以流来保存
  @GET
  public Call<ResponseBody> downloadFile(@Url String url);

}
