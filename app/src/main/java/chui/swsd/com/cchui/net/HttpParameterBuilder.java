package chui.swsd.com.cchui.net;

import android.net.Uri;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 链式结构写法
 */
public class HttpParameterBuilder {

    private static HttpParameterBuilder mParameterBuilder;
    private static Map<String, RequestBody> params;

    /**
     * 构建私有方法
     */
    private HttpParameterBuilder() {

    }

    /**
     * 初始化对象
     */
    public static HttpParameterBuilder newBuilder(){
        if (mParameterBuilder==null){
            mParameterBuilder = new HttpParameterBuilder();
            if (params==null){
                params = new HashMap<>();
            }
        }
        return mParameterBuilder;
    }

    /**
     * 添加参数
     * 根据传进来的Object对象来判断是String还是File类型的参数
     */
    public HttpParameterBuilder addParameter(String key, Object o) {
        if (o instanceof String) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), (String) o);
            params.put(key, body);
        } else if (o instanceof File) {
            RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), (File) o);
            params.put(key + "\"; filename=\"" + ((File) o).getName() + "", body);
        }

        return this;
    }

    /**
     * 初始化图片的Uri来构建参数
     * 一般不常用
     * 主要用在拍照和图库中获取图片路径的时候
     */
    public HttpParameterBuilder addFilesByUri(String key, List<Uri> uris) {

        for (int i = 0; i < uris.size(); i++) {
            File file = new File(uris.get(i).getPath());
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            params.put(key + i + "\"; filename=\"" + file.getName() + "", body);
        }

        return this;
    }

    /**
     * 构建RequestBody
     */
    public Map<String, RequestBody> bulider() {
        return params;
    }
}
