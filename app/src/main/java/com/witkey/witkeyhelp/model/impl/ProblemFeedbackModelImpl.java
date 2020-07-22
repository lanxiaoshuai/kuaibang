package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.ProblemFeedbackModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2019/12/6.
 */

public class ProblemFeedbackModelImpl implements ProblemFeedbackModel {


    @Override
    public void UploadFeedback(String content, int userId , String imgUrl, int type, Integer  businessId ,int orderId ,final AsyncCallback callback) {

          api.feedback(content,userId,imgUrl,type,businessId,orderId).enqueue(new Callback(callback, "上传举报反馈失败") {
              @Override
              public void getSuc(String body) {
                  try {
                      JSONObject jsonObject=new JSONObject(body);
                      String errorMessage = jsonObject.getString("errorMessage");

                      callback.onSuccess(errorMessage);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }
          });


    }

    @Override
    public void saveImgSuc(List<ReleasePhotoBean> missionBean, final AsyncCallback callback) {
        List<File> files=new ArrayList<>();

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i <missionBean.size() ; i++) {
            files.add(new File(missionBean.get(i).getUrl()));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), requestBody);
            parts.add(part);
        }
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        //  requestBody.addFormDataPart(entry.getKey(), entry.getKey(), body);
        api.upLoadImg(parts).enqueue(new Callback(callback, "上传失败") {
            @Override
            public void getSuc(String body) {
                String businessImgUrl = JSONUtil.getValueToString(body, "returnObject");
                callback.onSuccess(businessImgUrl);
            }
        });
    }
}
