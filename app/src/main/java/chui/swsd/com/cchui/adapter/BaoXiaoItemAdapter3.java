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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao.SpBaoxiaoActivity;

/**
 * 作者： 关云秀 on 2016/12/30.
 * 描述：
 */
public class BaoXiaoItemAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    public static List<BaoXiaoBean> datas;
    public TextView textView;
    public BaoXiaoItemAdapter3(Context context, List<BaoXiaoBean> datas,TextView textView) {
        this.context = context;
        this.datas = datas;
        this.textView = textView;
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
        View view = LayoutInflater.from(context).inflate(R.layout.activity_sp_baoxiao_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //加载每个列表项的数据
        final BaoXiaoBean baoXiaoBean = datas.get(position);
        if (holder instanceof ItemViewHolder) {
            if(position == 0){
                ((ItemViewHolder) holder).delTv.setVisibility(View.GONE);
            }else{
                ((ItemViewHolder) holder).delTv.setVisibility(View.VISIBLE);
            }
            if(baoXiaoBean.getMoney() != 0){
                ((ItemViewHolder) holder).moneyTv.setText(baoXiaoBean.getMoney()+"");
            }
            ((ItemViewHolder) holder).titleTv.setText("报销明细("+baoXiaoBean.getId()+")");
            ((ItemViewHolder) holder).typeTv.setText(baoXiaoBean.getType());
            ((ItemViewHolder) holder).detailsTv.setText(baoXiaoBean.getDetails());
            ((ItemViewHolder) holder).moneyTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!TextUtils.isEmpty(editable.toString())) {
                        baoXiaoBean.setMoney(Double.parseDouble(editable.toString()));
                        setSumTv();
                    }
                }
            });
            ((ItemViewHolder) holder).typeTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!TextUtils.isEmpty(editable.toString())) {
                        baoXiaoBean.setType(editable.toString());
                    }
                }
            });
            ((ItemViewHolder) holder).detailsTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!TextUtils.isEmpty(editable.toString())) {
                        baoXiaoBean.setDetails(editable.toString());
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
    public void setSumTv(){
        double money = 0;
        for(BaoXiaoBean baoXiaoBean:datas){
            money = money+baoXiaoBean.getMoney();
        }
        textView.setText(money+"");
    }
    public static List<BaoXiaoBean> getList(){
        return  datas;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.del_tv)
        TextView delTv;
        @BindView(R.id.money_tv)
        EditText moneyTv;
        @BindView(R.id.type_tv)
        EditText typeTv;
        @BindView(R.id.details_tv)
        EditText detailsTv;

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