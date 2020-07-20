package com.witkey.witkeyhelp.model.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ConsultModelImpl implements com.witkey.witkeyhelp.model.IConsultModel {

    private MultipartBody.Part filePart;

    @Override
    public void saveConsult(MissionBean missionBean, final AsyncCallback callback) {

        // form 表单形式上传


        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

//        for (Map.Entry<String, String> entry : missionBean.getPhotoMap().entrySet()) {
//            Log.e(TAG, "getFileUploads: key= " + entry.getKey() + " and value= " + entry.getValue());
//            Log.e("TAG", Contacts.imgPath + entry.getValue());
//            File file = new File(Contacts.imgPath + entry.getValue());
//            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
//            requestBody.addFormDataPart(entry.getKey(), entry.getKey(), body);
//        }
        //Log.e("tag","经度"+missionBean.getLatitude()+"纬度"+missionBean.getLongitude());
        requestBody.addFormDataPart("title", missionBean.getTitle() + "");
        requestBody.addFormDataPart("describes", missionBean.getDescribes() + "");
        requestBody.addFormDataPart("businessImgUrl", missionBean.getBusinessImgUrl() + "");
        requestBody.addFormDataPart("price", missionBean.getPrice() + "");
        requestBody.addFormDataPart("contactsPhone", missionBean.getContactsPhone() + "");
        requestBody.addFormDataPart("createUserId", missionBean.getCreateUserId() + "");
        requestBody.addFormDataPart("businessType", missionBean.getBusinessType() + "");
        requestBody.addFormDataPart("productType", missionBean.getProductType() + "");
        requestBody.addFormDataPart("longitude", missionBean.getLongitude() + "");
        requestBody.addFormDataPart("latitude", missionBean.getLatitude() + "");
        requestBody.addFormDataPart("businessNum", missionBean.getBusinessNum() + "");
        requestBody.addFormDataPart("paymentType", missionBean.getPaymentType() + "");
        requestBody.addFormDataPart("endDate", missionBean.getEndDate() + "");
        requestBody.addFormDataPart("bargainingType", missionBean.getBargainingType() + "");
        requestBody.addFormDataPart("biddingType", missionBean.getBiddingType() + "");
        requestBody.addFormDataPart("bondType", missionBean.getBondType() + "");
        requestBody.addFormDataPart("placeName", missionBean.getPlaceName() + "");
        requestBody.addFormDataPart("deposit", missionBean.getDeposit() + "");
        requestBody.addFormDataPart("payType", missionBean.getPayType() + "");
        requestBody.addFormDataPart("userId", missionBean.getCreateUserId() + "");
        requestBody.addFormDataPart("unareaType", missionBean.getUnareaType() + "");

        for (int i = 0; i < missionBean.getCircleIds().size(); i++) {
            requestBody.addFormDataPart("circleIds", missionBean.getCircleIds().get(i));
        }


        api.addBusiness(
                requestBody.build()
        ).enqueue(new Callback(callback, "保存失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "ConsultModelImpl-saveConsult: " + body);
                String businessId = JSONUtil.getValueToString(body, "returnObject");
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

    @Override
    public void puhotoConsult(final List<ReleasePhotoBean> missionBean, final AsyncCallback callback) {

        List<File> files=new ArrayList<>();

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i <missionBean.size() ; i++) {
            files.add(new File(missionBean.get(i).getUrl()));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), requestBody);
            parts.add(part);
        }
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        api.upLoadImg(parts).enqueue(new Callback(callback, "上传失败") {
            @Override
            public void getSuc(String body) {
                String businessImgUrl = JSONUtil.getValueToString(body, "returnObject");
                callback.onSuccess(businessImgUrl);
            }
        });
    }


    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


    @Override
    public void receipt(int orderId, int userId, final AsyncCallback callback) {

        Log.d(TAG, "MissionModelImpl-receipt: orderId=" + orderId + ",userId=" + userId);
        api.receipt(orderId + "", userId + "").enqueue(new Callback(callback, "接单失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-receipt: " + body);
                callback.onSuccess("");
            }
        });
    }

    @Override
    public void wxAppletPay(String body, String price, String ip, String businessId, final AsyncCallback callback) {
        api.wxAppletPay(body, price, ip, businessId).enqueue(new Callback(callback, "微信支付失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-receipt: " + body);
                PayInfoBean beanFromJson = JsonUtils.getBeanFromJson(body, PayInfoBean.class);
                callback.onSuccess(beanFromJson);
            }
        });

    }

    @Override
    public void apiwxpayResult(String outTradeNo, final AsyncCallback callback) {
        api.apiwxpayresult(outTradeNo).enqueue(new Callback(callback, "微信支付查询结果失败") {
            @Override
            public void getSuc(String body) {
                Log.e(TAG, "MissionModelImpl-receipt: " + body);
                callback.onSuccess(body);

            }
        });
    }


}
