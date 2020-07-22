package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.witkey.witkeyhelp.view.impl.RegistrationActivity;
import com.witkey.witkeyhelp.view.impl.WebActivity;

/**
 * Created by jie on 2020/5/12.
 */

public class WebLinkMethod extends LinkMovementMethod {

    private static WebLinkMethod instance;
    private Context context;

    private WebLinkMethod(Context context) {
        this.context = context;
    }

    public static MovementMethod getInstance(Context context) {
        if (instance == null)
            instance = new WebLinkMethod(context);
        return instance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
//          ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                  link[0].onClick(widget);
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("weburl", link[0].getURL());
                    context.startActivity(intent);
                    Log.e("tag",link[0].getURL()+"1314");
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }


}
