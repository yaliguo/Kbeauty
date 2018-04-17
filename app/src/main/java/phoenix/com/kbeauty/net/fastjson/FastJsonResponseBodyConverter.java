package phoenix.com.kbeauty.net.fastjson;


import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import phoenix.com.kbeauty.base.BaseBean;
import retrofit2.Converter;


/**
 * <b>类名称：</b> FastJsonResponseBodyConverter <br/>
 * <b>类描述：</b> ResponseBody转换器<br/>
 * <b>创建人：</b> Lincoln <br/>
 * <b>修改人：</b> Lincoln <br/>
 * <b>修改时间：</b> 2016年03月08日 下午3:58<br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
    * 转换方法
    */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        //Todo 修改首次解析方式 或则工厂模式设计解析
      //  BaseBean bean =  JSON.parseObject(tempStr,BaseBean.class);
        //if(bean==null|| bean.getStatus() ==null){
      //      throw  new IOException("json_exception");
       // }
        return JSON.parseObject(tempStr, type);

    }
}