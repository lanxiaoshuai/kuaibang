package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.wight.EmoticonClickListener;
import com.witkey.witkeyhelp.util.wight.EmoticonEntity;
import com.witkey.witkeyhelp.util.wight.EmoticonPageEntity;
import com.witkey.witkeyhelp.util.wight.ImageLoadUtils;
import com.witkey.witkeyhelp.widget.Constants;

import java.io.IOException;



public class BigEmoticonsAndTitleAdapter extends BigEmoticonsAdapter {

    protected final double DEF_HEIGHTMAXTATIO = 1.6;

    public BigEmoticonsAndTitleAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        super(context, emoticonPageEntity, onEmoticonClickListener);
        this.mItemHeight = (int) context.getResources().getDimension(R.dimen.item_emoticon_size_big);
        this.mItemHeightMaxRatio = DEF_HEIGHTMAXTATIO;
    }

    protected void bindView(int position, ViewHolder viewHolder) {
        final boolean isDelBtn = isDelBtn(position);
        final EmoticonEntity emoticonEntity = mData.get(position);
        if (isDelBtn) {
            viewHolder.iv_emoticon.setImageResource(R.mipmap.icon_del);
            viewHolder.iv_emoticon.setBackgroundResource(R.drawable.bg_emoticon);
        } else {
            if (emoticonEntity != null) {
                try {
                    ImageLoadUtils.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    viewHolder.tv_content.setVisibility(View.VISIBLE);
                    viewHolder.tv_content.setText(emoticonEntity.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewHolder.iv_emoticon.setBackgroundResource(R.drawable.bg_emoticon);
            }
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEmoticonClickListener != null) {

                    mOnEmoticonClickListener.onEmoticonClick(emoticonEntity, Constants.EMOTICON_CLICK_BIGIMAGE, isDelBtn);
                }
            }
        });
    }
}