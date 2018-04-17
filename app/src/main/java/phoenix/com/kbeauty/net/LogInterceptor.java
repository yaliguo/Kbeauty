package phoenix.com.kbeauty.net;

/**
 * Created by phoenix on 2016/11/25.
 *
 * @Package com.farmlink.ps.engine.netLayer
 * @Description:
 */
public class LogInterceptor implements HttpLoggingInterceptorM.Logger {
    public static String INTERCEPTOR_TAG_STR = "OkHttp";



    public LogInterceptor() {

    }



    public LogInterceptor(String tag) {

        INTERCEPTOR_TAG_STR = tag;

    }



    @Override
    public void log(String message, @LogUtil.LogType int type) {

        LogUtil.printLog(false, type, INTERCEPTOR_TAG_STR, message);

    }
}
