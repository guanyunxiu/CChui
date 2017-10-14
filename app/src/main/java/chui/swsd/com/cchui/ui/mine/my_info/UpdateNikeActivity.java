package chui.swsd.com.cchui.ui.mine.my_info;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\14 0014.
 */

public class UpdateNikeActivity extends BaseSwipeBackActivity {
    @BindView(R.id.nike_et)
    EditText nikeEt;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_update_unik;
    }

    @Override
    protected void initViews() {
       initTitle(true,"修改昵称");
    }

    @Override
    protected void updateViews() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_channel,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_channel:
                if(!TextUtils.isEmpty(nikeEt.getText().toString())) {
                    Intent intent = new Intent();
                    intent.putExtra("nike", nikeEt.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    CommonUtil.showToast(this,"请填写昵称");
                }
                break;
        }
        return true;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

}
