package com.witkey.witkeyhelp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.ChoosePicAdapter;
import com.witkey.witkeyhelp.bean.Tag;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.witkey.witkeyhelp.Contacts.Contacts.imgPath;


/**
 * @author lingxu
 * @date 2019/7/12 9:35
 * @description 图片工具类
 */
public class ImgUtil {

    public static void loadImg(Context context, String avatar, ImageView ivImg) {
        if (avatar != null) {
            try {
                Glide.with(context).load(avatar)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//None明显会慢
                        .skipMemoryCache(true)
                        .into(ivImg);
            } catch (Exception e) {
                ExceptionUtil.CatchException(e);
            }
        }
    }

    public static void loadImgNoCache(Context context, String avatar, ImageView ivImg) {
        try {
            Glide.with(context).load(avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//None明显会慢
                    .skipMemoryCache(true)
                    .into(ivImg);
        } catch (Exception e) {
            ExceptionUtil.CatchException(e);
        }
    }

    public static void loadImg(Context context, String avatar, ImageView ivImg, int errorId) {
        try {
            Glide.with(context).load(avatar).error(errorId)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivImg);
        } catch (Exception e) {
            ExceptionUtil.CatchException(e);
        }
    }

    /**
     * 从矢量图SVG获取位图bitmap
     *
     * @param context
     * @param drawableId
     * @return
     */
    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }


    /**
     * 转换为圆形图片
     */
    public static Bitmap toRound(Bitmap bitmap) {

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int l = w > h ? h : w;

        float roundPx = l / 2;

        Bitmap output = Bitmap.createBitmap(l, l, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, l, l);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 转换为圆形图片
     */
    public static Bitmap makeRoundCorner(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }

        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /*
     * 图片加圆角
     */
    public static Bitmap makeRoundCorner(Bitmap bitmap, int px) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, px, px, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

//    //Bitmap对象保存味图片文件
//    //将bitmap转为file
//    public static File saveBitmapFile(Bitmap bitmap, String imgValue) throws FileNotFoundException {
//        // 判断目录是否存在，不存在则创建
//        File filePath = new File(Contacts.imgPath);
//        if (!filePath.exists()) {
//            filePath.mkdirs();
//        }
////                new File(Contacts.imgPath1).mkdirs();
//        Log.d(Contacts.Use_TAG, "saveBitmapFile: "+Contacts.imgPath + imgValue);
//        File file = new File(Contacts.imgPath + imgValue);//将要保存图片的路径
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
////            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            //TODO
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//            bos.flush();
//            bos.close();
//            Log.d(Contacts.Use_TAG, "saveBitmapFile: "+file);
////            Log.d(Contacts.Use_TAG, "saveBitmapFile: "+ FileUtil.getFileOrFilesSize(Contacts.imgPath + imgValue,3));
//        } catch (IOException e) {
//            Log.d(Contacts.Use_TAG, "saveBitmapFile: fail");
//            e.printStackTrace();
//        }
//        return file;
//    }

    /**
     * 裁剪照片
     *
     * @param uri
     */
    public static void startPhotoZoom(String imgName, Context context, Uri uri, int sizex, int sizey, int requestCode) {
        // 判断目录是否存在，不存在则创建
        File filePath = new File(imgPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", sizex);
        intent.putExtra("aspectY", sizey);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", sizex);
        intent.putExtra("outputY", sizey);
        intent.putExtra("return-data", false);//返回里是否有了bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Contacts.imgPathCathe + imgName))
                //不会在文件后面带时间戳
                Uri.parse("file://" + "/" + imgPath + imgName));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        IntentUtil.startActivityForResult(((Activity) context), intent, requestCode);
    }


    /**
     * 异步在工作线程中执行图片模糊化处理
     *
     * @param bitmap
     * @param r
     * @param callback
     */
    public static void loadBluredBitmap(final Bitmap bitmap, final int r,
                                        final BitmapCallback callback) {
        new AsyncTask<String, String, Bitmap>() {
            protected Bitmap doInBackground(String... params) {
                Bitmap b = createBlurBitmap(bitmap, r);
                return b;
            }

            protected void onPostExecute(Bitmap b) {
                callback.onBitmapLoaded(b);
            }
        }.execute();
    }

    /**
     * 传递bitmap 传递模糊半径 返回一个被模糊的bitmap
     *
     * @param sentBitmap
     * @param radius
     * @return
     */
    public static Bitmap createBlurBitmap(Bitmap sentBitmap, int radius) {
        if (sentBitmap == null) {
            return null;
        }
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;

            }
            yw += w;

        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * 通过一个网络的路径加载一张图片
     * 会得到bitmap
     *
     * @param errorResId 有占位图id
     *                   无占位图传0
     */
    public static void loadBitmap(final Context context, final String path,
                                  final int width, final int height, final int errorResId, final BitmapCallback callback) {
        // 先去文件中找找 看看有没有下载过
        String filename = path.substring(path.lastIndexOf("/") + 1);
        final File file = new File(context.getCacheDir(), filename);
        Bitmap bitmap = loadBitmap(file.getAbsolutePath());
        if (bitmap != null) {
            callback.onBitmapLoaded(bitmap);
            return;
        }
        // 文件中没有图片 则去下载
        new AsyncTask<String, String, Bitmap>() {
            protected Bitmap doInBackground(String... params) {
                try {
                    InputStream is = HttpUtils.getStream(path);
                    Bitmap b = null;
                    if (width == 0 && height == 0) {
                        b = BitmapFactory.decodeStream(is);
                    } else {
                        b = loadBitmap(is, width, height);
                    }
                    // 图片一旦下载成功 需要存入文件
                    save(b, file.getAbsolutePath());
                    return b;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return errorResId == 0 ? null : BitmapFactory.decodeResource(context.getResources(), errorResId);
            }

            protected void onPostExecute(Bitmap bitmap) {
                callback.onBitmapLoaded(bitmap);
            }
        }.execute();
    }


    /**
     * 从某个路径下读取一个bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap loadBitmap(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 保存图片文件
     *
     * @param bitmap
     * @param path   图片的目标路径
     */
    public static void save(Bitmap bitmap, String path) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists()) { // 父目录不存在
            file.getParentFile().mkdirs(); // 创建父目录
        }
        FileOutputStream os = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * 通过流加载图片
     *
     * @param is     数据源
     * @param width  图片的目标宽度
     * @param height 图片的目标高度
     * @return 压缩过后的图片
     */
    public static Bitmap loadBitmap(InputStream is, int width, int height)
            throws IOException {
        // 通过is 读取 到一个 byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            bos.write(buffer, 0, length);
            bos.flush();
        }
        byte[] bytes = bos.toByteArray();
        // 使用BitmapFactory获取图片的原始宽和高
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 仅仅加载图片的边界属性
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        // 通过目标宽和高计算图片的压缩比例
        int w = opts.outWidth / width;
        int h = opts.outHeight / height;
        int scale = w > h ? h : w;
        // 给Options属性设置压缩比例
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        // 重新解析byte[] 获取Bitmap
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
    }

    /**
     * loadBitmap 获取到bitmap的回调
     */
    public interface BitmapCallback {
        void onBitmapLoaded(Bitmap bitmap);
    }

    /**
     * 代码设置 setDrawableRight
     * 右
     */
    public static void setDrawableRight(Context context, int resId, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 代码设置 setDrawableLeft
     * 左
     */
    public static void setDrawableLeft(Context context, int resId, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 代码设置 setDrawableLeftAndRight
     * 左右
     */
    public static void setDrawableLeftAndRight(Context context, int resLId, int resRId, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(resLId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());//必须设置图片大小，否则不显示
        Drawable drawable1 = context.getResources().getDrawable(resRId);
        drawable.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(drawable, null, drawable, null);
    }


    /**
     * 选择拍照还是选择的回调
     */
    public interface ChoosePic {
        void pick(String imgPath);
    }

    /**
     * 可选择拍照还是选择
     *
     * @param code      requestcode
     * @param ChoosePic 回调
     * @param imgName   图片名称
     */
    public static void addPic(final Context context, final int code, final ChoosePic ChoosePic, final String imgName) {
        final Dialog chooseChannelDialog = new Dialog(context);
        final ListView lv = new ListView(context);
        lv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        List<Tag> tags = new ArrayList<>();
//        tags.add(new Tag(1, "拍照", R.drawable.ic_choose_camera));
        tags.add(new Tag(2, "选择图片", R.drawable.ic_choose_pick_pic));
        ChoosePicAdapter adapter = new ChoosePicAdapter(context, tags);
        lv.setAdapter(adapter);
        adapter.setListener(new ChoosePicAdapter.OnClickListener() {
            @Override
            public void onClick(Tag tag) {
                chooseChannelDialog.dismiss();
                if (tag.getId() == 1) {
                    ChoosePic.pick(startCamera(((Activity) context), code, tag.getId(), imgName));
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    ((Activity) context).startActivityForResult(intent, code);
                    ChoosePic.pick(null);
                }
            }
        });
        chooseChannelDialog.setContentView(lv);
        chooseChannelDialog.show();
    }

    private static void addPic(Context context, int code) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        IntentUtil.startActivityForResult((Activity) context, intent, code);
    }

    /**
     * 启动相机
     */
    public static String startCamera(Activity activity, int requestCode, int stateId, String imgName) {
        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            if (stateId == 1) {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            } else {
                intent.setAction(Intent.ACTION_PICK);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            }
//            File outDir = Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File outDir = new File(imgPath);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, imgName);
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(outFile);
            Log.d("llx", "getAbsolutePath=" + outFile.getAbsolutePath());
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            activity.startActivityForResult(intent, requestCode);
            return outFile.getAbsolutePath();
        } else {
            Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /**
     * 压缩图片
     *
     * @param srcPath
     * @param pixelW
     * @param pixelH
     */
    public static void compress(String srcPath, float pixelW, float pixelH) {
        float ww = pixelW;
        float hh = pixelH;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
        opts.inJustDecodeBounds = false;
        int w = opts.outWidth;
        int h = opts.outHeight;
        int size = 0;
        if (w <= ww && h <= hh) {
            size = 1;
        } else {
            double scale = w >= h ? w / ww : h / hh;
            double log = Math.log(scale) / Math.log(2);
            double logCeil = Math.ceil(log);
            size = (int) Math.pow(2, logCeil);
        }
        opts.inSampleSize = size;
        bitmap = BitmapFactory.decodeFile(srcPath, opts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        System.out.println(baos.toByteArray().length);
        while (baos.toByteArray().length > 45 * 1024) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            quality -= 20;
            System.out.println(baos.toByteArray().length);
        }
        try {
            baos.writeTo(new FileOutputStream(srcPath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 改变SVG图片着色
     *
     * @param imageView
     * @param iconResId svg资源id
     * @param color     期望的着色
     */
    public void changeSVGColor(Context mContext, ImageView imageView, int iconResId, int color) {
        @SuppressLint("RestrictedApi") Drawable drawable = AppCompatDrawableManager.get().getDrawable(mContext, iconResId);
        imageView.setImageDrawable(drawable);
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, ContextCompat.getColor(mContext, color));
        imageView.setImageDrawable(drawable1);
    }


}
