package e.hanglungdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Annotation;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.ui.home.activity.HomeActivity;
import e.library.BaseActivity;
import e.library.T;
import e.library.commonwidget.NotifyUtil;
import e.library.commonwidget.StatusBarCompat;
import e.library.commonwidget.StatusBarUtil;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_btn_login)
    TextView mBtnLogin;
    @Bind(R.id.layout_progress)
    View progress;
    @Bind(R.id.input_layout)
    View mInputLayout;
    @Bind(R.id.input_layout_name)
    LinearLayout mName;
    @Bind(R.id.input_layout_psw)
    LinearLayout mPsw;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_pwt)
    EditText etPwt;
    int mWidth;
    int mHeight;
    @Bind(R.id.title_right)
    TextView righrTitle;
    @Bind(R.id.title_back)
    ImageView black;
    private int requestCode;
    @OnClick(R.id.main_btn_login)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.main_btn_login:
                // 计算出控件的高与宽
                mWidth = mBtnLogin.getMeasuredWidth();
                mHeight = mBtnLogin.getMeasuredHeight();
                // 隐藏输入框
                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);
                inputAnimator(mInputLayout, mWidth, mHeight);
                break;
            case R.id.title_right:
                break;
        }
    }
    /**
     * 输入框的动画效果
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });


    }

    /**
     * 出现进度动画
     * @param view
     */
    void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        final ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(3000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etPwt.getText())) {
                    T.showShortToast("账号密码输入有误");
                    recovery();
                } else {
                    recovery();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    T.showShortToast("登录成功");
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * 恢复初始状态
     */
    void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        super.initView();
        black.setVisibility(View.GONE);
        StatusBarUtil.immersive(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this,
        requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.video_icon;
        String ticker = "欢迎使用TMS!";
        String title = "TMS登录系统! ! !";
        String content = "你正处在TMS登录页面哦,欢迎您的使用!";
        NotifyUtil notify1 = new NotifyUtil(this, 1);
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
    }
}
