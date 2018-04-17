package phoenix.com.kbeauty.net;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import phoenix.com.kbeauty.KApplication;
import phoenix.com.kbeauty.utils.NetworkUtils;

/**
 * Created by guoyali
 * <p>
 * on 2018/4/17.
 * <p>
 * use
 */

class CaheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isNetworkAvailable(KApplication.Companion.instance())) {
            Response response = chain.proceed(request);
            // read from cache for 0 s  有网络不会使用缓存数据
            int maxAge = 10;
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            //无网络时强制使用缓存数据
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times ; value is useless at all !
//            int maxStale = 60;
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

    }
}
