package com.witkey.witkeyhelp.widget.when_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.MainActivity;


/**
 * author：luck
 * project：AppWhenThePage
 * package：com.luck.app.page.when_page
 * email：893855882@qq.com
 * data：2017/2/22
 */
public class PageFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int index = args.getInt("index");
        int layoutId = args.getInt("layoutId");
        int count = args.getInt("count");
        rootView = inflater.inflate(layoutId, null);


        // 滑动到最后一页有点击事件
        if (index == count -1) {


            rootView.findViewById(R.id.id_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    SpUtils.putBoolean(getContext(), "GUIDE", true);
                }
            });
        }

        return rootView;
    }
}
