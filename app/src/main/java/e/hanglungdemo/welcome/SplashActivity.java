package e.hanglungdemo.welcome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import e.hanglungdemo.MainActivity;
import e.hanglungdemo.R;
import e.hanglungdemo.SpContent;
import e.library.BaseActivity;
import e.library.SPUtil;


/**
 * 启动画面
 */
public class SplashActivity extends BaseActivity{


    private int requestCode;

    @Override
    public void initView() {
        handler.sendEmptyMessageDelayed(1,1000*4);


    }

    @Override
    protected int getLayoutId() {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        return R.layout.activity_splash;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    if(SPUtil.get(SplashActivity.this, SpContent.IsFirst,"0").equals("0")){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        SplashActivity.this.finish();
                    }else{
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        SplashActivity.this.finish();
                    }

                    break;
                default:
                    break;
            }
        }
    };
}
