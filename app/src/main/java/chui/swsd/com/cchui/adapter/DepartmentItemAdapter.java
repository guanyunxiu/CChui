package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao.SpBaoxiaoActivity;

/**
 * 作者： 关云秀 on 2016/12/30.
 * 描述：
 */
public class DepartmentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    public static List<DepartmentBean> datas;
    public DepartmentItemAdapter(Context context, List<DepartmentBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public interface OnItemClick {
        void onItemDel(int position);
    }
    private OnItemClick onClick;
    public void setOnItemClickListener(OnItemClick onItemClickListener) {
        this.onClick = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        // return 3;
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载每个列表项
        View view = LayoutInflater.from(context).inflate(R.layout.activity_zc_department_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //加载每个列表项的数据
        final DepartmentBean departmentBean = datas.get(position);
        if (holder instanceof ItemViewHolder) {
            if(position == 0){
                ((ItemViewHolder) holder).delTv.setVisibility(View.GONE);
            }else{
                ((ItemViewHolder) holder).delTv.setVisibility(View.VISIBLE);
            }
            ((ItemViewHolder) holder).delTv.setText("删除");
            ((ItemViewHolder) holder).titleTv.setText("部门"+(position+1));
            ((ItemViewHolder) holder).nameTv.setText(departmentBean.getName());
            ((ItemViewHolder) holder).nameTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!TextUtils.isEmpty(editable.toString())) {
                        departmentBean.setName(editable.toString());
                    }
                }
            });

            ((ItemViewHolder) holder).delTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NormalDialogStyleTwo(position);
                }
            });
        }
    }

    public static List<DepartmentBean> getList(){
        return  datas;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.del_tv)
        TextView delTv;
        @BindView(R.id.name_tv)
        EditText nameTv;
        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private void NormalDialogStyleTwo(final int position) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("是否确定删除")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        SpBaoxiaoActivity.i--;
                        datas.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
    }
}