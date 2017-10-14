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
 * Created by HanHailong on 15/10/19.
 */
public class TagSelectAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    public TagSelectAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
    public interface OnItemClickListener {
        void onAddItemClicked(int position, List<Node> mDataList);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_select_people_item , null);
        TextView textView = (TextView) view.findViewById(R.id.name_tv);
        Node t = (Node) mDataList.get(position);
        textView.setBackgroundResource(R.drawable.photo_bg2);
        textView.setText(t.getName());
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
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }
}
