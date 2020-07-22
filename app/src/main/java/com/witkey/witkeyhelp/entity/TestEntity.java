package com.witkey.witkeyhelp.entity;

/**
 * Created by jie on 2020/4/9.
 */

public class TestEntity implements IPieElement {

    private float mValue;
    private String mColor;
    private String mDescription;

    public TestEntity(float value,String color,String description){
        mValue=value;
        mColor=color;
        mDescription =description;
    }
    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public String getColor() {
        return mColor;
    }

    public String getDescription() {
        return mDescription;
    }
}
