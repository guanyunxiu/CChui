package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;

/**
 * Created by Administrator on 2016/4/19.
 */
public class BaoXiaoItemAdapter2 extends BaseAdapter {
    private Context mcontext;
    private List<BaoXiaoBean> commentList;
    public LayoutInflater layoutInflater;
    public BaoXiaoItemAdapter2(Context context, List<BaoXiaoBean> commentList){
        this.mcontext = context;
        this.commentList = commentList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaoXiaoBean comment = commentList.get(position);
        ViewHolder holder = null;
     //  if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activity_sp_baoxiao_item, null);

            convertView.setTag(holder);

        return convertView;

    }
    class ViewHolder{
       private ImageView photoImg;
        private TextView nameText;
        private TextView contentText;
        private TextView timeText;
    }
    public interface PhotoOnclickInter{
        void photoOnClick(BaoXiaoBean comment);
    }
    public PhotoOnclickInter photoOnclickInter;

    public PhotoOnclickInter getPhotoOnclickInter() {
        return photoOnclickInter;
    }

    public void setPhotoOnclickInter(PhotoOnclickInter photoOnclickInter) {
        this.photoOnclickInter = photoOnclickInter;
    }
}
