package com.witkey.witkeyhelp.widget.pickerimage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.widget.pickerimage.fragment.PickerAlbumFragment;
import com.witkey.witkeyhelp.widget.pickerimage.fragment.PickerImageFragment;
import com.witkey.witkeyhelp.widget.pickerimage.model.AlbumInfo;
import com.witkey.witkeyhelp.widget.pickerimage.model.PhotoInfo;
import com.witkey.witkeyhelp.widget.pickerimage.model.PickerContract;
import com.witkey.witkeyhelp.widget.pickerimage.utils.Extras;
import com.witkey.witkeyhelp.widget.pickerimage.utils.PickerConfig;
import com.witkey.witkeyhelp.widget.pickerimage.utils.PickerImageLoadTool;
import com.witkey.witkeyhelp.widget.pickerimage.utils.RequestCode;
import com.witkey.witkeyhelp.widget.pickerimage.view.ToolBarOptions;
import com.witkey.witkeyhelp.widget.pickerimage.view.UIView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Inner image picker, no longer use third-part application 
 */
public class PickerAlbumActivity extends UIView implements PickerAlbumFragment.OnAlbumItemClickListener,
        PickerImageFragment.OnPhotoSelectClickListener, OnClickListener {

	private FrameLayout pickerAlbumLayout;
	
	private FrameLayout pickerPhotosLayout;
	
	private PickerAlbumFragment photoFolderFragment;
	
	private PickerImageFragment photoFragment;
	
	private RelativeLayout pickerBottomBar;
	
	private TextView pickerPreview;
	
	private TextView pickerSend;
	
	private List<PhotoInfo> hasSelectList = new ArrayList<PhotoInfo>();
	
	private boolean isMutiMode;
	
	private boolean isSupportOriginal;
	
	private boolean isSendOriginalImage;
	
	private int mutiSelectLimitSize;
	
	private boolean isAlbumPage;

	@Override
	protected void onCreateActivity() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picker_album_activity);
         ImageView imageView= findViewById(R.id.ivBarLeft);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		TextView include_title= findViewById(R.id.include_title);
		include_title.setText(R.string.picker_image_folder);
		proceedExtra();

		initUI();
	}
	
	private void proceedExtra(){
		Intent intent = getIntent();
		if(intent != null){
			isMutiMode = intent.getBooleanExtra(Extras.EXTRA_MUTI_SELECT_MODE, false);
			mutiSelectLimitSize = intent.getIntExtra(Extras.EXTRA_MUTI_SELECT_SIZE_LIMIT, 9);
			isSupportOriginal = intent.getBooleanExtra(Extras.EXTRA_SUPPORT_ORIGINAL, false);	
		}
	}
	


	private void initUI(){
		// bottom bar
		pickerBottomBar = (RelativeLayout) findViewById(R.id.picker_bottombar);
		if(isMutiMode){
			pickerBottomBar.setVisibility(View.VISIBLE);
		}else{
			pickerBottomBar.setVisibility(View.GONE);
		}
		pickerPreview = (TextView) findViewById(R.id.picker_bottombar_preview);
		pickerPreview.setOnClickListener(this);
		pickerSend = (TextView) findViewById(R.id.picker_bottombar_select);
		pickerSend.setOnClickListener(this);
		
		// fragment
		pickerAlbumLayout = (FrameLayout) findViewById(R.id.picker_album_fragment);
		pickerPhotosLayout = (FrameLayout) findViewById(R.id.picker_photos_fragment);
		photoFolderFragment = new PickerAlbumFragment();
		switchContent(photoFolderFragment);
		
		isAlbumPage = true;
	}
	
	@Override
	public void OnAlbumItemClick(AlbumInfo info) {
		// check photo list if has already be choose
		List<PhotoInfo> photoList = info.getList();
		if(photoList == null){
			return;
		}		
		for (PhotoInfo photo : photoList) {
			if(checkSelectPhoto(photo)){
				photo.setChoose(true);
			}else{
				photo.setChoose(false);
			}
		}
		// switch to photo fragment
		pickerAlbumLayout.setVisibility(View.GONE);
		pickerPhotosLayout.setVisibility(View.VISIBLE);
		if (photoFragment == null) {
			photoFragment = new PickerImageFragment();
			photoFragment.setArguments(makeDataBundle(photoList, isMutiMode, mutiSelectLimitSize));
			switchContent(photoFragment);
		} else {
			int hasSelectSize = hasSelectList.size();
			photoFragment.resetFragment(photoList, hasSelectSize);
		}
		// update title
		setTitle(info.getAlbumName());	
		isAlbumPage = false;
	}
	
	public Bundle makeDataBundle(List<PhotoInfo> photos, boolean mutiMode, int mutiSelectLimitSize) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(Extras.EXTRA_PHOTO_LISTS, new ArrayList<PhotoInfo>(photos));
		bundle.putBoolean(Extras.EXTRA_MUTI_SELECT_MODE, mutiMode);
		bundle.putInt(Extras.EXTRA_MUTI_SELECT_SIZE_LIMIT, mutiSelectLimitSize);
		
		return bundle;
	}

	@Override
	public void onPhotoSingleClick(List<PhotoInfo> photos, int position) {
		if(isMutiMode){
			PickerAlbumPreviewActivity.start(this, photos, position, isSupportOriginal, 
					isSendOriginalImage, hasSelectList, mutiSelectLimitSize);
		}else{			
			if(photos != null){
				PhotoInfo photo = photos.get(position);
				List<PhotoInfo> selectList = new ArrayList<PhotoInfo>();
				selectList.add(photo);
				
				setResult(RESULT_OK, PickerContract.makeDataIntent(selectList, false));
				finish();
			}
		}	
	}
	
	@Override
	public void onPhotoSelectClick(PhotoInfo selectPhoto) {
		if(selectPhoto == null)
			return;
		// check
		boolean isChoose = selectPhoto.isChoose();
		if(isChoose){
			boolean isSelect = checkSelectPhoto(selectPhoto);
			if(!isSelect){
				addSelectPhoto(selectPhoto);
			}
		}else{
			removeSelectPhoto(selectPhoto);
		}
		// update bottom bar
		updateSelectBtnStatus();
	}
	
	private boolean checkSelectPhoto(PhotoInfo photo){
		boolean isSelect = false;
		for(int i = 0; i < hasSelectList.size(); i++){
			PhotoInfo select = hasSelectList.get(i);
			if(select.getImageId() == photo.getImageId()){
				isSelect = true;
				break;
			}
		}
		
		return isSelect;
	}
	
	private void removeSelectPhoto(PhotoInfo photo){
		Iterator<PhotoInfo> lIterator = hasSelectList.iterator();
		while (lIterator.hasNext()) {
			PhotoInfo select = lIterator.next();
            if(select.getImageId() == photo.getImageId()) {
            	lIterator.remove();
            } 
        }
	}
	
	private void addSelectPhoto(PhotoInfo photo){
		hasSelectList.add(photo);
	}
	
	private void resetSelectPhotos(List<PhotoInfo> photos){
		if(hasSelectList != null){
			hasSelectList.clear();
		}else{
			hasSelectList = new ArrayList<PhotoInfo>();
		}
		hasSelectList.addAll(photos);
	}

	private void updateSelectBtnStatus(){
		int selectSize = hasSelectList.size();
		if(selectSize > 0){
			pickerPreview.setEnabled(true);
			pickerSend.setEnabled(true);
			pickerSend.setText(String.format(this.getResources().getString(
					R.string.picker_image_send_select), selectSize));
		}else{
			pickerPreview.setEnabled(false);
			pickerSend.setEnabled(false);
			pickerSend.setText(R.string.btn_send);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.picker_bottombar_preview) {//预览图片
            PickerAlbumPreviewActivity.start(this, hasSelectList, 0, isSupportOriginal,
                    isSendOriginalImage, hasSelectList, mutiSelectLimitSize);
        } else if (v.getId() == R.id.picker_bottombar_select) {//选择图片发送按钮
			setResult(RESULT_OK, PickerContract.makeDataIntent(hasSelectList, isSendOriginalImage));
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
        //true进入一层相册直接返回 false进入了二层相册中,点击返回到一层
		if(isAlbumPage){
			finish();
		}else {
			backToAlbumPage();
		}
	}
	
	private void backToAlbumPage(){
		setTitle(R.string.picker_image_folder);
		isAlbumPage = true;
		pickerAlbumLayout.setVisibility(View.VISIBLE);
		pickerPhotosLayout.setVisibility(View.GONE);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.PICKER_IMAGE_PREVIEW) {
            if (resultCode == RESULT_OK) {
            	if(data != null){
            		setResult(RESULT_OK, new Intent(data));
        			finish();
            	}
            }
            else if(resultCode == PickerAlbumPreviewActivity.RESULT_FROM_USER){
            	if(data != null){    
            		// update photo fragment
            		isSendOriginalImage = data.getBooleanExtra(Extras.EXTRA_IS_ORIGINAL, false);
            		List<PhotoInfo> list = PickerContract.getPhotos(data);
            		if(photoFragment != null && list != null){
            			photoFragment.updateGridview(list);
            		}
            		// update select photo list
            		List<PhotoInfo> selectList = PickerContract.getSelectPhotos(data);
            		resetSelectPhotos(selectList);         		
            		updateSelectBtnStatus();
            		if(photoFragment != null && hasSelectList != null){
            			photoFragment.updateSelectedForAdapter(hasSelectList.size());
            		}
            	}
            }
        }
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		PickerConfig.checkImageLoaderConfig(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PickerImageLoadTool.clear();
	}
}