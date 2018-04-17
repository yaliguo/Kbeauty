package phoenix.com.kbeauty.net;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import phoenix.com.kbeauty.BuildConfig;
import phoenix.com.kbeauty.base.AppConfig;
import phoenix.com.kbeauty.base.BaseBean;
import phoenix.com.kbeauty.net.fastjson.FastJsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by phoenix
 * <p>
 * 创建时间 on 2017/5/31.
 * <p>
 * 功能：主要的网络请求端口
 *      待处理问题：1如果有helder因为单例，第二次hedler是传不过来的额
 *                2强转换ResponseException 检查转换异常
 */

public class NetClient {
    private volatile static NetClient mInstance;
    private volatile static NetClient mInstance2Headers;

    private Retrofit sim;
    private NetApi netApi;

    public static NetClient getInstance() {
        if(null== mInstance){
            synchronized (NetClient.class){
                if(null == mInstance){
                    mInstance = new NetClient();
                }
            }
        }
        return mInstance;
    }

    public static NetClient getInstance(HashMap<String,String> headers) {
        if(null== mInstance){
            synchronized (NetClient.class){
                if(null == mInstance){
                    mInstance = new NetClient(headers);
                }
            }
        }
        return mInstance;
    }


    private NetClient(HashMap<String,String> headers){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();


        //增加helder
        addHeaders(okHttpClientBuilder,headers==null?new HashMap<String, String>():headers);

                /* cache   暂时不开放*/
//          Cache cache = null;
//          File cacheFile =null;
//          try {
//                cacheFile = new File(UIUtils.getAppContext().getCacheDir(), "net_cache");
//                cache = new Cache(cacheFile, 10 * 1024 * 1024);
//          } catch (Exception e) {
//                Log.e("OKHttp", "Could not create http cache", e);
//          }
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));
        dispatcher.setMaxRequests(20);
        dispatcher.setMaxRequestsPerHost(1);
        OkHttpClient okHttpClient = okHttpClientBuilder
                .addInterceptor(new HttpLoggingInterceptorM().setLevel(
                        BuildConfig.DEBUG?HttpLoggingInterceptorM.Level.BODY:
                                HttpLoggingInterceptorM.Level.NONE))
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(100, 30, TimeUnit.SECONDS))
                //.cache(cache)
                // .addInterceptor(new CaheInterceptor())
                // .addNetworkInterceptor(new CaheInterceptor())
                .retryOnConnectionFailure(false)
                //.writeTimeout(AppConfig.NET_TIMEOUT, TimeUnit.SECONDS)
                .build();
        sim = new Retrofit.Builder()
                .baseUrl(AppConfig.Companion.getBase_url())
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        netApi = sim.create(NetApi.class);

    }


    private NetClient() {
        this(null);
    }

    private void addHeaders(OkHttpClient.Builder okHttpClientBuilder, HashMap<String,String> headers) {
//        headers.put("TOKEN", (String) SPUtils.get(CoreUtils.getAppContext(), SpContants.TOKEN,""));
//        LogUtil.d("TOKEN--"+(String) SPUtils.get(CoreUtils.getAppContext(), SpContants.TOKEN,""));
//        /* add header */
//        if(headers.size() > 0){
//            okHttpClientBuilder.addInterceptor(new HeaderInterceptor(headers));
//        }
    }

    /**
     * 清理实例重建
     */

    public static void reCreate(){
        mInstance = null;
    }

    @SuppressWarnings("unchecked")
    public <U extends BaseBean> Observable get(String url, Map<String,String> parameters, U cls) {
        return netApi.executeGet(url, parameters)
                .compose(schedulersTransformer())
                .map(new TransDataFun(cls))
                .onErrorResumeNext(new HttpErrorDespatchFun<>());

    }
    @SuppressWarnings("unchecked")

    public <U extends BaseBean> Observable post(String url, Map<String,String> parameters, U cls) {
        cls.getClass();
        return netApi.executePost2UrlEncoded(url, parameters)
                .compose(schedulersTransformer())
                .map(new TransDataFun(cls))
                .onErrorResumeNext(new HttpErrorDespatchFun<>());

    }

    @SuppressWarnings("unchecked")

    public <U extends BaseBean> Observable postNom(String url, Map parameters, U cls) {
        cls.getClass();
        return netApi.executePost(url, parameters)
                .compose(schedulersTransformer())
                .map(new TransDataFun(cls))
                .onErrorResumeNext(new HttpErrorDespatchFun<>());

    }
    @SuppressWarnings("unchecked")

    public Observable<ResponseBody> getJsonBody(String url, Map parameters){
        return netApi.json(url,parameters)
                .compose(schedulersTransformer())
                .onErrorResumeNext(new HttpErrorDespatchFun<>());
    }
    @SuppressWarnings("unchecked")

    public Observable<ResponseBody> getJsonBody_post(String url, Map parameters){
        return netApi.jsonPost(url,parameters)
                .compose(schedulersTransformer())
                .onErrorResumeNext(new HttpErrorDespatchFun<>());
    }


   ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static class HttpErrorDespatchFun<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    /**
     * 用来解析具体data数据到模型,判断了服务器status错误
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public class TransDataFun<T> implements io.reactivex.functions.Function<BaseResponse<T>, T> {
        T cls;
        public TransDataFun(T s) {
            this.cls=s;
        }

        @Override
        public T apply(BaseResponse<T> response) throws Exception {
          //  if(response.status != 1){
            //    throw new ExceptionHandle.ServerException(response.note, new Throwable(response.note),response.status);
          //  }
            T  re = (T) JSON.parseObject(response.results.toString(),cls.getClass());
            return re;
        }
    }

//    public class TokenErrorFun implements Func1<BaseResponse,Boolean>{
//
//        @Override
//        public Boolean call(BaseResponse baseResponse) {
//            if(baseResponse.errno == 2){
//                BaseApplication.getApplication().cleanLoginInfo(); //清除登陆信息
//                DialogFactory.getInstance().alertTipDialog("提示", "您的账号登录已过期，请重新登录", "重新登录", "退出", new View.OnClickListener() {
//                    @Override
//                    public void onItemClick(View v) {
//                        BaseApplication.getApplication().exit();
//                        LocationService.stop(UIUtils.getAppContext());
//                    }
//                }, new View.OnClickListener() {
//                    @Override
//                    public void onItemClick(View v) {
//                        BaseApplication.getApplication().finishAllActivities();
//                        LocationService.stop(UIUtils.getAppContext());
//                        UIUtils.startActivity(new Intent(BaseActivity.getForegroundActivity(), LoginActivity.class));
//                    }
//                });
//
//                return false;
//            }
//            return true;
//        }
//    }
}
