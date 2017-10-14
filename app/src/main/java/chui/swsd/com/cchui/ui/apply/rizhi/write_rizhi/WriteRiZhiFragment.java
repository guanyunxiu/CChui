package chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseFragment;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class WriteRiZhiFragment extends BaseFragment {
    @BindView(R.id.rb_tv)
    TextView rbTv;
    @BindView(R.id.zb_tv)
    TextView zbTv;
    @BindView(R.id.yb_tv)
    TextView ybTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_write_rizhi;
    }

    @Override
    protected void initViews() {
       initTitle(true,"日志");
    }

    @Override
    protected void updateViews() {

    }

    @OnClick({R.id.rb_tv, R.id.zb_tv, R.id.yb_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_tv:
                startActivity(new Intent(mContext,WriteRbActivity.class));
                break;
            case R.id.zb_tv:
                startActivity(new Intent(mContext,WriteZbActivity.class));
                break;
            case R.id.yb_tv:
                startActivity(new Intent(mContext,WriteYbActivity.class));
                break;
        }
    }
}
