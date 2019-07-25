package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ConsultModelImpl implements com.witkey.witkeyhelp.model.IConsultModel {

    @Override
    public void saveConsult(MissionBean missionBean, final AsyncCallback callback) {
        Log.d(TAG, "ConsultModelImpl-saveConsult-request: "+ missionBean.toString());
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : missionBean.getPhotoMap().entrySet()) {
            Log.d(TAG, "getFileUploads: key= " + entry.getKey() + " and value= " + entry.getValue());
            RequestBody body = RequestBody.create(MediaType.parse("image/*"),
                    new File(Contacts.imgPath + entry.getValue()));
            requestBody.addFormDataPart(entry.getKey(), entry.getKey(), body);
        }
        requestBody.addFormDataPart("title", missionBean.getTitle()+"");
        requestBody.addFormDataPart("describes", missionBean.getDescribes()+"");
        requestBody.addFormDataPart("businessImgUrl", missionBean.getBusinessImgUrl()+"");
        requestBody.addFormDataPart("price", missionBean.getPrice()+"");
        requestBody.addFormDataPart("contactsPhone", missionBean.getContactsPhone()+"");
        requestBody.addFormDataPart("createUserId", missionBean.getCreateUserId() + "");
        requestBody.addFormDataPart("businessType", missionBean.getBusinessType()+"");
        requestBody.addFormDataPart("productType", missionBean.getProductType()+"");
        requestBody.addFormDataPart("longitude", missionBean.getLongitude()+"");
        requestBody.addFormDataPart("latitude", missionBean.getLatitude()+"");
        requestBody.addFormDataPart("businessNum", missionBean.getBusinessNum() + "");
        requestBody.addFormDataPart("paymentType", missionBean.getPaymentType()+"");
        requestBody.addFormDataPart("endDate", missionBean.getEndDate()+"");
        requestBody.addFormDataPart("bargainingType", missionBean.getBargainingType()+"");
        requestBody.addFormDataPart("biddingType", missionBean.getBiddingType()+"");
        requestBody.addFormDataPart("bondType", missionBean.getBondType()+"");
        api.addBusiness(
                requestBody.build()
        ).enqueue(new Callback(callback, "保存失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "ConsultModelImpl-saveConsult: " + body);
                String businessId= JSONUtil.getValueToString(body,"returnObject");
                Log.d(TAG, "ConsultModelImpl-saveConsult: businessId==" + businessId);
                callback.onSuccess(businessId);
            }
        });
    }

    @Override
    public void publishConsult(String businessId, final AsyncCallback callback) {
        api.addOrder(businessId).enqueue(new Callback(callback, "发布失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "ConsultModelImpl-publishConsult: " + body);
                //发布成功
                callback.onSuccess("");
            }
        });
    }
}
