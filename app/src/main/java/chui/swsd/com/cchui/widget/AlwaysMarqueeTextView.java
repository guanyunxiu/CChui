package chui.swsd.com.cchui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

public class AlwaysMarqueeTextView extends TextView {

    public AlwaysMarqueeTextView(Context context) {
        super(context);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
