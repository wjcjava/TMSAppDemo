package e.hanglungdemo.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.ui.adapter.GridImageAdapter;
import e.hanglungdemo.ui.bean.PictureBean;
import e.library.BaseActivity;
import e.library.BaseDialog;
import e.library.CustomRatingBar;
import e.library.T;

/**
 * 确认签收
 */
public class SignInActivity extends BaseActivity {

    @Bind(R.id.title_title)
    TextView trackTitle;
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.et_lob)
    EditText etLob;
    @Bind(R.id.box_cargo_damage)
    CheckBox damageBox;
    @Bind(R.id.box_goods_lost)
    CheckBox lostBox;
    @Bind(R.id.score_custom)
    CustomRatingBar customRatingBar;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int maxSelectNum = 9;

    private List<PictureBean> pictureBeanList = new ArrayList<>();
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private BaseDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView() {
        super.initView();
        trackTitle.setTextColor(getResources().getColor(R.color.white));
        trackTitle.setText("确认签收");
        String etLobString = etLob.getText().toString().trim();
        customRatingBar.setmOnStarChangeListener(new CustomRatingBar.onStarChangedListener() {
            @Override
            public void onStarChange(CustomRatingBar ratingBar, float mark) {
                etLob.setText(mark + "");
            }
        });

    }

    @OnClick({R.id.title_back, R.id.box_cargo_damage, R.id.box_goods_lost,
            R.id.tv_receiving, R.id.et_lob
    })
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.box_cargo_damage:
                T.showLongToast("你点击了我");
                break;
            case R.id.box_goods_lost:
                T.showLongToast("你点击了我");
                break;
            case R.id.tv_receiving:
                T.showShortToast("你点击了收货功能");
                break;
            case R.id.et_lob:
                etLob.setCursorVisible(true);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        themeId = R.style.picture_default_style;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

    }



    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
        }

    };

    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog = builder.setViewId(R.layout.photo_choose_dialog)
                .setPaddingdp(10, 0, 10, 0)
                .setGravity(grary)
                .setAnimation(animationStyle)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.but_choose_one, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 进入相册 以下是例子：不需要的api可以不写
                        PictureSelector.create(SignInActivity.this)
                                .openGallery(chooseMode)
                                .theme(themeId)// 主题样式设置 具体参考 values/styles
                                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                                .minSelectNum(1)// 最小选择数量
                                .imageSpanCount(4)// 每行显示个数
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认 true
                                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1 之间 如设置 .glideOverride()无效
                                .selectionMode(true ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                                .previewImage(true)// 是否可预览图片
                                .isCamera(true)// 是否显示拍照按钮
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                .enableCrop(false)// 是否裁剪
                                .compress(true)// 是否压缩
                                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅
                                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1
                                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                                .openClickSound(false)// 是否开启点击声音
                                .selectionMedia(selectList)// 是否传入已选图片
                                .minimumCompressSize(100)// 小于100kb的图片不压缩

                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        dialog.close();
                    }
                })
                .addViewOnClickListener(R.id.but_choose_two, new View.OnClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onClick(View view) {
                        //  单独拍照
                        PictureSelector.create(SignInActivity.this)
                                .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                                .theme(themeId)// 主题样式设置 具体参考 values/styles
                                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                                .minSelectNum(1)// 最小选择数量
                                .selectionMode(true ?
                                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                                .previewImage(true)// 是否可预览图片
                                .isCamera(true)// 是否显示拍照按钮
                                .compress(true)// 是否压缩
                                .glideOverride(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)// glide 加载宽高，越小图片列表越流畅
                                .withAspectRatio(0, 0)//    裁剪比例 如16:9 3:2 3:4 1:1
                                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                                .openClickSound(true)// 是否开启点击声音
                                .selectionMedia(selectList)// 是否传入已选图片
                                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                .enableCrop(true)// 是否裁剪 true or false
                                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                                .cropCompressQuality(50)// 裁剪压缩质量 默认 90 int
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        dialog.close();
                    }
                })
                .builder();
        dialog.show();

        Button button = dialog.getView(R.id.but_choose_three);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.close();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
