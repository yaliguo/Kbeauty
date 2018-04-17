package phoenix.com.kbeauty.net;

/**
 * Created by phoenix
 * <p>
 * 创建时间 on 2017/5/31.
 * <p>
 * 功能：
 */

public class BaseResponse <T> {
    public T results;
    public int status;
    public String note;
}
