package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.bean.ConsultBean;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ConsultModelImpl implements com.witkey.witkeyhelp.model.IConsultModel {

    @Override
    public void saveConsult(ConsultBean consultBean,final AsyncCallback callback) {
        Log.d(TAG, "ConsultModelImpl-saveConsult-request: "+consultBean.toString());
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : consultBean.getPhotoMap().entrySet()) {
            Log.d(TAG, "getFileUploads: key= " + entry.getKey() + " and value= " + entry.getValue());
            RequestBody body = RequestBody.create(MediaType.parse("image/*"),
                    new File(Contacts.imgPath + entry.getValue()));
            requestBody.addFormDataPart(entry.getKey(), entry.getKey(), body);
        }
        requestBody.addFormDataPart("title", consultBean.getTitle()+"");
        requestBody.addFormDataPart("describes", consultBean.getDescribes()+"");
        requestBody.addFormDataPart("businessImgUrl", consultBean.getBusinessImgUrl()+"");
        requestBody.addFormDataPart("price", consultBean.getPrice()+"");
        requestBody.addFormDataPart("contactsPhone", consultBean.getContactsPhone()+"");
        requestBody.addFormDataPart("createUserId", consultBean.getCreateUserId() + "");
        requestBody.addFormDataPart("businessType", consultBean.getBusinessType()+"");
        requestBody.addFormDataPart("productType", consultBean.getProductType()+"");
        requestBody.addFormDataPart("longitude", consultBean.getLongitude()+"");
        requestBody.addFormDataPart("latitude", consultBean.getLatitude()+"");
        requestBody.addFormDataPart("businessNum", consultBean.getBusinessNum() + "");
        requestBody.addFormDataPart("paymentType", consultBean.getPaymentType()+"");
        requestBody.addFormDataPart("endDate", consultBean.getEndDate()+"");
        requestBody.addFormDataPart("bargainingType", consultBean.getBargainingType()+"");
        requestBody.addFormDataPart("biddingType", consultBean.getBiddingType()+"");
        requestBody.addFormDataPart("bondType", consultBean.getBondType()+"");
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
