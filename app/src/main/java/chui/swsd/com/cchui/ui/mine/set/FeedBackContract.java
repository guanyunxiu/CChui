package chui.swsd.com.cchui.ui.mine.set;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\23 0023.
 */

public interface FeedBackContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String content);
    }
}
