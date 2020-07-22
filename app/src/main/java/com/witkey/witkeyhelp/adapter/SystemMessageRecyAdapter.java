package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.view.impl.ActivityNewsDetails;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class SystemMessageRecyAdapter extends BaseRecyAdapter<SystemMessageBean.ReturnObjectBean.RowsBean> {
    public SystemMessageRecyAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = View.inflate(context, R.layout.system_message_item, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).content.setText(data.get(position).getContent());
        ((ViewHolder) holder).time.setText(data.get(position).getCreateTime());
        ((ViewHolder) holder).title.setText(data.get(position).getTitle());

//        ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (PventQuickClick.isFastDoubleClick()) {
//                    return;
//                }
//                Intent intent = new Intent(context, ActivityNewsDetails.class);
//                intent.putExtra("content", data.get(position).getContent());
//                intent.putExtra("time", data.get(position).getSendTime());
//                context.startActivity(intent);
//            }
//        });


        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            ((ViewHolder) holder).to_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(((ViewHolder) holder).to_evaluate, position); // 2
                }
            });
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView time;
        private TextView title;
        private TextView to_evaluate;

        public ViewHolder(View v) {
            super(v);

            content = (TextView) v.findViewById(R.id.content);
            time = (TextView) v.findViewById(R.id.news_time);
            title = (TextView) v.findViewById(R.id.title);
            to_evaluate=v.findViewById(R.id.to_evaluate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
