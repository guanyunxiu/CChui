package chui.swsd.com.cchui.net.util;

import java.util.Map;

import chui.swsd.com.cchui.net.UrlAddress;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Ｔａｍｉｃ on 2016-07-08.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public interface BaseApiService {

    public static final String Base_URL = UrlAddress.URL;
    @Headers("content-type:application/json;charset=UTF-8")
    @GET
    Observable<BaseResponse> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps
    );

    @POST("{url}")  //这个是不能地址拼接path
    Observable<BaseResponse> executePost(@Path("url") String url, @Body Object postQueryInfo);

    @Headers("content-type:application/json;charset=UTF-8")
    @POST
    Observable<BaseResponse> executePost2(@Url String url, @Body Map<String, String> map);
    @Headers("content-type:application/json;charset=UTF-8")
    @POST
    Observable<BaseResponse> executePost3(@Url String url, @Body Map<String, Object> map);
    @Multipart
    @POST("{url}")
    Observable<BaseResponse> uploadFile(@Path("url") String url,
                                        @PartMap Map<String, RequestBody> params);
    @Multipart
    @POST("{url}")
    Observable<ResponseBody> uploadFile2(
            @Path("url") String url,
            @QueryMap Map<String, String> usermaps,
            @PartMap Map<String, RequestBody> params
    );




    @Multipart
    @POST
    Observable<ResponseBody> uploadFileWithPartMap(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap,
            @Part("file") MultipartBody.Part file);
    @Multipart
    @POST("http://api.stay4it.com/v1/public/core/?service=user.updateAvatar")
    Observable<ResponseBody> upLoadFile(
            @Part("file") String des,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);


    @Multipart
    @POST("http://api.stay4it.com/v1/public/core/?service=user.updateAvatar")
    Observable<ResponseBody> uploadFilePhoto(@Part("file") String des,
                                             @PartMap Map<String, RequestBody> params);

  /*  @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
*/
}
