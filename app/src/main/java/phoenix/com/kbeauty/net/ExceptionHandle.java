package phoenix.com.kbeauty.net;

import android.net.ParseException;
import android.os.Environment;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.HttpException;


public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseException handleException(Throwable e) {
        ResponseException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    writeLog(e);
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseException(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseException(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException || e instanceof java.net.SocketException) {
            ex = new ResponseException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseException(e, ERROR.UNKONW_HOST_ERROR);
            ex.message = "连接错误";
            return ex;
        }else if(e instanceof IOException &&"json_exception" .equals(e.getMessage())){
            ex = new ResponseException(e, ERROR.PARSE_ERROR);
            ex.message = "JSON格式错误";
            return ex;

        }

        else {
            ex = new ResponseException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            writeLog(e);

            return ex;
        }



    }

    public static void writeLog(final Throwable ex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                sb.append("#######################################################################################\n");
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                ex.printStackTrace(printWriter);
                Throwable cause = ex.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }

                printWriter.close();
                String result = writer.toString();
                sb.append(result+"\n");
                sb.append("#######################################################################################\n\n\n\n\n");

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                try {
                    long timestamp = System.currentTimeMillis();
                    String time = formatter.format(new Date());
                    String fileName = "neterror" + "-" + time + "-" + timestamp
                            + ".log";
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        String path = Environment.getExternalStorageDirectory().toString()
                                + File.separator+"exception";
                        File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(new File(path, fileName));
                        fos.write(sb.toString().getBytes());
                        fos.close();
                    }
                } catch (Exception e) {
                }
            }
        }).run();
    }


    /**
     * 约定异常
     */
  public   class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /*
            连接错误
         */
        public static final int UNKONW_HOST_ERROR = 1007;
    }

    public static class ResponseException extends Exception {
        public int code;
        public String message;

        public ResponseException(Throwable throwable, int code) {
            super(throwable);
            this.code = code;

        }
    }



    public static class ServerException extends RuntimeException {
        public ServerException(String detailMessage, Throwable throwable, int code) {
            super(detailMessage, throwable);
            this.code = code;
            this.message = detailMessage;
        }

        public int code;
        public String message;
    }
}

