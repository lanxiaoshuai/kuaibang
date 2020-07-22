package com.witkey.witkeyhelp.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * Created by jie on 2020/6/4.
 */

public class CarouselBean extends  SimpleBannerInfo {


        private String url;

        @Override
        public Object getXBannerUrl() {
            return url;
        }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CarouselBean(String url) {
        this.url = url;
    }
}
