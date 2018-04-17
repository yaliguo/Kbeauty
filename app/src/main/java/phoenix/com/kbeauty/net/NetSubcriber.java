package phoenix.com.kbeauty.net;


import org.reactivestreams.Subscriber;

/**
 * Created by phoenix
 * <p>
 * 创建时间 on 2017/5/31.
 * <p>
 * 功能：
 */

public abstract class NetSubcriber<T> implements Subscriber<T> {

    @Override
    public void onComplete() {
        onNetCompleted();

    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponseException){
            onNetError((ExceptionHandle.ResponseException) e);
        }else {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T o) {
        onNetnext(o);
    }

    public  void onNetCompleted(){};
    public abstract void onNetError(ExceptionHandle.ResponseException e);
    public abstract void onNetnext(T bean);
}
