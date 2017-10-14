package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhl.library.OnInitSelectedPosition;
import com.multilevel.treelist.Node;

import java.util.ArrayList;
import java.util.List;

import chui.swsd.com.cchui.R;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\24 0024.
 */

public class ZtTagAdapter <T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;
    private int typeFlag;
    public ZtTagAdapter(Context context,int typeFlag) {
        this.mContext = context;
        mDataList = new ArrayList<>();
        this.typeFlag = typeFlag;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
    public interface OnItemClickListener {
        void onAddItemClicked(int position,List<Node> mDataList);
    }

    private TagAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(TagAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_sp_sx_item , null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        String t = (String) mDataList.get(position);
        textView.setText(t);
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll() {
        mDataList.clear();
        // onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position == typeFlag) {
            return true;
        }
        return false;
    }
}
