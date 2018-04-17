package phoenix.com.kbeauty.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by phoenix
 * <p>
 * 创建时间 on 2017/5/31.
 * <p>
 * 功能：
 */

public interface NetApi {
    /**
     * GIT方式返回Observable对象
     * @param url
     * @param maps
     * @return
     */
    @GET("{url}")
    Observable<BaseResponse<Object>> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps
    );


    /**
     * POST方式返回Observable对象
     * @param url
     * @param maps
     * @return
     */
    @POST("{url}")
    Observable<BaseResponse<Object>> executePost(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);


    /**
     * POST方式表单提交返回Observable对象
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResponse<Object>> executePost2UrlEncoded(
            @Path("url") String url,
            //  @Header("") String authorization,
            @FieldMap Map<String, String> maps);


    /**
     * GET方式返回原始json数据
     * @param
     * @return
     */
    @GET("{url}")
    Observable<ResponseBody> json(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);


    /**
     * POST方式返回原始json数据
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("{url}")
    Observable<ResponseBody> jsonPost(
            @Path("url") String url,
            @FieldMap Map<String, String> maps);

}
