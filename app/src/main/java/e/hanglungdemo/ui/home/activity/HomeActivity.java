package e.hanglungdemo.ui.home.activity;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.ui.home.fragment.HomeFragment;
import e.hanglungdemo.ui.home.fragment.SettingFragment;
import e.library.BaseActivity;
import e.library.NoScrollViewPager;

public class HomeActivity extends BaseActivity{
    @Bind(R.id.pager_home)
    NoScrollViewPager homePager;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_mine)
    RadioButton rbMine;
    private List<Fragment> list=new ArrayList<>();
    HomeFragment homeFragment=new HomeFragment();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick({R.id.rb_home,R.id.rb_mine})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                homePager.setCurrentItem(0);
                break;
            case R.id.rb_mine:
                homePager.setCurrentItem(1);

                break;
        }
    }
    @Override
    protected void initData() {
        super.initData();
        /**
         * 判断是否支持滑动切换
         * true：既支持滑动，又支持点击
         * false：只支持手指点击指定按钮进行切换fragment
         */
        homePager.setNoScroll(true);
        list.add(homeFragment);
        list.add(new SettingFragment());
        MyAdapter myAdapter=new MyAdapter(getSupportFragmentManager());
        homePager.setAdapter(myAdapter);
        homePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    rbHome.setChecked(true);
                }else {
                    rbMine.setChecked(true);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }
}
