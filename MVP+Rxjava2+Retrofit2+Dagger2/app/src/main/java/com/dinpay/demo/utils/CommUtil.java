package com.dinpay.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dinpay.demo.DouApplication;
import com.dinpay.demo.R;
import com.dinpay.demo.view.LoadDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 公用方法工具类
 * Created by yzh-t105 on 2016/9/20.
 */
public class CommUtil {

    private static LoadDialog frameDialog;
    static Toast toast;
    public static final String qq_key = "1105824118";
    public static final String WECHAT_APP_ID = "wx45177b7a5c561cfb";
    public static final String QQ = "com.tencent.mobileqq";
    public static final String WeChat = "com.tencent.mm";
    public static final String WEIBA = "Qweiba";
    public static String FLAG;
    public static final long DETAIL=400L;
    public static final String TAG="TAG";


    public static final String BASE_URL = "http://api.jiefu.tv/app2/api/";

    /**
     * 默认头像的网络图片地址
     **/
    public static final String ICON = "http://h.hiphotos.baidu.com/image/pic/item/34fae6cd7b899e51601a7b9c40a7d933c9950da5.jpg";
    /**
     * 最热表情图片列表
     **/
    public static final String HOT_URL = "http://api.jiefu.tv/app2/api/dt/item/hotList.html";
    //public static final String HOT_URL = "http://api.jiefu.tv/app2/api/dt/item/newList.html";
    /**
     * 最新表情图片列表
     **/
    public static final String NEW_URL = "http://api.jiefu.tv/app2/api/dt/shareItem/newList.html";
    /**
     * 真人表情图片列表
     **/
    public static final String REALMAN_URL = "http://api.jiefu.tv/app2/api/dt/tag/getByType.html";
    /**
     * 真人表情图片列表
     **/
    public static final String REALMANINFO_URL = "http://api.jiefu.tv/app2/api/dt/item/getByTag.html";
    /**
     * 表情分类列表
     **/
    public static final String ALLTYPE = "http://api.jiefu.tv/app2/api/dt/tag/allList.html";
    /**
     * 表情分类列表详情
     **/
    //public static final String ALLTYPEBYID="http://api.jiefu.tv/app2/api/dt/shareItem/getByTag.html";
    public static final String ALLTYPEBYID = "http://api.jiefu.tv/app2/api/dt/item/getByTag.html";
    /**
     * 关键字搜索表情
     **/
    public static final String KEYWORD_SEARCH = "http://api.jiefu.tv/app2/api/dt/shareItem/search.html";


    public static final String DDSQ="http://mobile.shenmeiguan.cn";//http://mobile.bugua.com
    /**
     * 热门模板
     **/
    public static final String TEMP_HOT= DDSQ+"/template/hot/list/";
    /**
     * 全部表情分类
     */
    public static final String ALLPIC=DDSQ+"/folder/cherrypick/";
    private static CommUtil commUtil;

    public static CommUtil getInstance() {
        if (commUtil == null) {
            commUtil = new CommUtil();
        }
        return commUtil;
    }


    public static void showWaitDialog(Context context, String msg, boolean cancelAble) {
        if (frameDialog != null && frameDialog.isShowing()) {
            return;
        }
        closeWaitDialog();
        frameDialog = new LoadDialog(context, msg, cancelAble);
        frameDialog.show();
    }

    public static void closeWaitDialog() {
        if (frameDialog != null) {
            frameDialog.dismiss();
            frameDialog = null;

        }
    }

    /**
     * @param tip
     * @param flag 0短 时间  1长时间
     */
    public static void showToast(final String tip, int flag) {
        if (TextUtils.isEmpty(tip)) {
            Log.e("", "toast的字符串为空!");
            return;
        }
        TextView text = null;
        if (toast == null) {
            toast = Toast.makeText(DouApplication.getInstance(), tip, Toast.LENGTH_SHORT);
        } else {
            // toast.setText(tip);
            if (flag == 0) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

        }
        View layout = LayoutInflater.from(DouApplication.getInstance()).inflate(R.layout.toast, null);
        text = (TextView) layout.findViewById(R.id.text);
        toast.setView(layout);
        text.setText(tip);
        toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.show();

    }

    public static void showToast(final String tip) {
        if (TextUtils.isEmpty(tip)) {
            Log.e("", "toast的字符串为空!");
            return;
        }
        TextView text = null;
        if (toast == null) {
            toast = Toast.makeText(DouApplication.getInstance(), tip, Toast.LENGTH_SHORT);
        } else {
            // toast.setText(tip);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        View layout = LayoutInflater.from(DouApplication.getInstance()).inflate(R.layout.toast, null);
        text = (TextView) layout.findViewById(R.id.text);
        toast.setView(layout);
        text.setText(tip);
        toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.show();

    }



    public static int dip2px( float dipValue) {
        final float scale = DouApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = DouApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    Intent intent;




    /**
     * 屏幕宽度
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = DouApplication.getInstance().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 屏幕高度
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = DouApplication.getInstance().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }





}
