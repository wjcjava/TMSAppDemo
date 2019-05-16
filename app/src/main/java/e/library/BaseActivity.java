package e.library;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import e.hanglungdemo.R;
import e.hanglungdemo.app.MyApplication;
import e.library.base.AppManager;
import e.library.commonwidget.StatusBarCompat;
import e.library.network.CommonDialog;
import e.library.network.InterNetBroadCastManager;
import e.library.network.NetWorkUtils;


public abstract class BaseActivity extends SwipeActivity{
    private InterNetBroadCastManager mInterNetBroadCastManager;
    private NetWorkUtils mNetWorkUtils;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_code_text));
        //StatusBarUtil.getStatusBarLightMode(this.getWindow());
        doBeforeSetcontentView();
        //Android中软键盘弹出时底部布局上移问题(解决方式之一)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutId());
        mNetWorkUtils = new NetWorkUtils(this);
        registerNetBroadCast();
        mNetWorkUtils.registerNetBroadCast();
        ButterKnife.bind(this);
        initData();
        initView();
        doFirstRequest();

    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把activity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
       /* // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        //setStatusBarColor();
        if (!isGPSOPen()) {
            CommonDialog dialog = new CommonDialog(this, "GPS提醒", "您未开启GPS位置服务，请开启。", "取消", new CommonDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {

                }
            });
            dialog.dismiss();
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }
    @Override
    public void onStop() {
        super.onStop();

    }

    protected void initView() {
    }
    protected void initData() {
    }


    protected void doFirstRequest() {

    }

    protected abstract int getLayoutId();

    /**
     * 手机网络变化监听
     */
    private void registerNetBroadCast() {
        mInterNetBroadCastManager = new InterNetBroadCastManager();
        registerReceiver(mInterNetBroadCastManager, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unregisterNetBroadCast() {
        if (mInterNetBroadCastManager != null) {
            unregisterReceiver(mInterNetBroadCastManager);
        }
    }

    /**
     * 判断GPS是否打开
     * @return
     */
    private boolean isGPSOPen() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.tranparent));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void setTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    //点击空白区域隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
        unregisterNetBroadCast();
        mNetWorkUtils.unregisterNetBroadCast();

    }






    public void testCallPhone(String phone) {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone(phone);
            }

        } else {
            callPhone(phone);
        }
    }


    /**
     * 请求权限的回调方法
     * @param requestCode    请求码
     * @param permissions    请求的权限
     * @param grantResults   权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            T.showLongToast( "授权成功");

            callPhone(grantResults.toString());
        }
    }

    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }

    private final int REQUEST_CODE = 0x1001;

}
