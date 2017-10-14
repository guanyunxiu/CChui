package chui.swsd.com.cchui.ui.mine.set;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public interface UpdatePassContract {
  interface view{
      void onSuccess();
      void onFail();
  }
  interface  presenter{
      void onSubmit(String oldpass,String newpass);
      void onLogot();
  }
}
