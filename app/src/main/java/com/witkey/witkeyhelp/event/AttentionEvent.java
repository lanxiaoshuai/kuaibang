package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/6/29.
 */

public class AttentionEvent {
    private String ciclerId;
    private boolean attention;

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public String getCiclerId() {
        return ciclerId;
    }

    public void setCiclerId(String ciclerId) {
        this.ciclerId = ciclerId;
    }

    public AttentionEvent(String ciclerId, boolean attention) {
        this.ciclerId = ciclerId;
        this.attention = attention;
    }
}
