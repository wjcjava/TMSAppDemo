package e.hanglungdemo.welcome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import e.hanglungdemo.R;
import e.hanglungdemo.SpContent;
import e.hanglungdemo.when_page.PageFrameLayout;
import e.library.SPUtil;

public class GuideActivity extends AppCompatActivity {

    private PageFrameLayout contentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //导航页只在第一次登录展示
        SPUtil.put(GuideActivity.this, SpContent.IsFirst,"1");
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        contentFrameLayout =findViewById(R.id.contentFrameLayout);
        // 设置资源文件和选中圆点
        contentFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab3,
                R.layout.page_tab4
        }, R.mipmap.banner_on, R.mipmap.banner_off);

    }
}
