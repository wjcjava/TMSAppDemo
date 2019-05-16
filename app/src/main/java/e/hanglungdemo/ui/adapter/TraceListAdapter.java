package e.hanglungdemo.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import e.hanglungdemo.R;
import e.hanglungdemo.utils.SpannableStringUtils;
import e.hanglungdemo.ui.bean.TraceBean;
import e.library.BaseActivity;
import e.library.BaseDialog;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<TraceBean> traceBeanList;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private Intent intent;
    private BaseDialog dialog;
    private int REQUEST_CODE=0x1001;

    public TraceListAdapter(Context context, List<TraceBean> traceBeanList) {
        this.context = context;
        this.traceBeanList = traceBeanList;
    }
    @Override
    public int getCount() {
        return traceBeanList.size();
    }

    @Override
    public TraceBean getItem(int position) {
        return traceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final TraceBean traceBean = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine = convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = convertView.findViewById(R.id.tvDot);
            holder.tv_new = convertView.findViewById(R.id.tv_new);
            holder.ivGoods = convertView.findViewById(R.id.iv_goods);
            holder.ivGoodsTwo = convertView.findViewById(R.id.iv_goods_two);
            convertView.setTag(holder);
        }
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tv_new.setVisibility(View.VISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.black));
            holder.ivGoods.setVisibility(View.VISIBLE);
            holder.ivGoodsTwo.setVisibility(View.VISIBLE);
            holder.ivGoods.setImageResource(R.drawable.home_middle_five);

            holder.tvDot.setBackgroundResource(R.mipmap.timelline_dot_secord);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tv_new.setVisibility(View.INVISIBLE);
            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.alpha_40_black));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.alpha_40_black));
            holder.ivGoods.setVisibility(View.GONE);
            holder.ivGoodsTwo.setVisibility(View.GONE);
            holder.tvDot.setBackgroundResource(R.mipmap.timelline_dot_first);

        }
        if(getItemViewType(position) == TYPE_TOP||getItemViewType(position) == TYPE_NORMAL){
            holder.tvAcceptTime.setText(traceBean.getAcceptTime());
            SpannableStringUtils sp = new SpannableStringUtils(traceBean.getAcceptStation());
            checkPhoneText(holder.tvAcceptStation, sp, traceBean.getAcceptStation());
        }

        return convertView;
    }
    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation;
        public TextView tvTopLine, tvDot, tv_new;
        public ImageView ivGoods, ivGoodsTwo;
    }
    // 正则表达式，提取我们所有匹配的内容；
    private void checkPhoneText(TextView tvAcceptStation, SpannableStringUtils sp, String text) {
        Pattern pattern = Pattern
                .compile("。\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
        final Matcher matcher = pattern.matcher(text);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            sp.setColor(Color.BLUE, matcher.start(), matcher.end())
                    .setBackGround(ContextCompat.getColor(context,R.color.white), matcher.start(), matcher.end())
                    .setBold( matcher.start(), matcher.end())
                    .setOnClick(matcher.start(), matcher.end(), ContextCompat.getColor(context,R.color.blue))
                    .setOnClickSpanListener(new SpannableStringUtils.OnClickSpanListener() {
                        @Override
                        public void OnClickSpanListener() {
                            showDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion,matcher.group());
                        }
                    });
            if (start >= text.length()) {
                break;
            }
        }
        tvAcceptStation.setMovementMethod(LinkMovementMethod.getInstance());
        tvAcceptStation.setTextSize(12);
        tvAcceptStation.setText(sp);
    }
    private void showDialog(int grary, int animationStyle, final String group) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context);
        dialog = builder.setViewId(R.layout.phone_dialog)
                .setPaddingdp(10, 0, 10, 0)
                .setGravity(grary)
                .setAnimation(animationStyle)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.bt_callPhone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Build.VERSION.SDK_INT >= 23) {

                            //判断有没有拨打电话权限
                            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                //请求拨打电话权限
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

                            } else {
                                callPhone(group);
                            }

                        } else {
                            callPhone(group);
                        }
//                        intent = new Intent(Intent.ACTION_CALL);
//                        Uri data = Uri.parse("tel:" + group);
//                        intent.setData(data);
//                        context.startActivity(intent);
//                        dialog.close();

                    }
                })
                .addViewOnClickListener(R.id.bt_dissmess, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.close();
                    }
                })
                .builder();
        dialog.show();
        Button phone= dialog.getView(R.id.bt_callPhone);
        phone.setText(group);


    }

    public void callPhone(String phoneNum){
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            context.startActivity(intent);
        }
    }
}
