package e.hanglungdemo.ui.home.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.ui.adapter.OrderAdapter;
import e.hanglungdemo.ui.adapter.OrderOnAdapter;
import e.hanglungdemo.ui.bean.OrderBean;
import e.hanglungdemo.ui.bean.OrderOnBean;
import e.hanglungdemo.ui.home.activity.TrackingActivity;
import e.library.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.recycler_home)
    RecyclerView recyclerView;
    @Bind(R.id.cb_numbers)
    CheckBox numbers;
    @Bind(R.id.et_order_home)
    EditText orderNumber;

    int[] orderImage = {R.drawable.ic_management_home, R.drawable.ic_scheduling_home,
            R.drawable.ic_tracking_home, R.drawable.ic_price_home};
    String[] order = {"订单管理", "订单调度", "订单跟踪", "订单询价"};
    String[] ordertitle = {"创建订单", "调度订单", "订单轨迹", "发布询价"};
    String[] orderfunction = {"提交调度", "中转，拆单", "客服反馈", "抢单报价"};
    String[] orderOn = {"POP单号", "WSE单号", "TER单号", "ERT单号"};
   private PopupWindow window;
    private List<OrderOnBean> onBeanList = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    private List<OrderBean> list = new ArrayList<>();

    @Override
    protected void init() {
        super.init();

        for (int i = 0; i < order.length; i++) {
            OrderBean orderBean = new OrderBean();
            orderBean.setManagement(order[i]);
            orderBean.setHomeLogo(orderImage[i]);
            orderBean.setOrderTitle(ordertitle[i]);
            orderBean.setOrderfunction(orderfunction[i]);
            list.add(orderBean);
        }
        OrderAdapter adapter = new OrderAdapter(R.layout.recycler_item, list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getActivity(), TrackingActivity.class);
                        intent.putExtra("title", list.get(position).getManagement());
                        getActivity().startActivity(intent);

            }
        });

    }

    @Override
    protected void initDate() {
        super.initDate();
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_orderno_pop, null, false);
        window = new PopupWindow(contentView, 300, RecyclerView.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        RecyclerView recyclerOrder = contentView.findViewById(R.id.recycler_orderon_home);
        for (int i = 0; i < orderOn.length; i++) {
            OrderOnBean orderBean = new OrderOnBean();
            orderBean.setItemName(orderOn[i]);
            onBeanList.add(orderBean);
        }
        OrderOnAdapter onAdapter = new OrderOnAdapter(R.layout.recycler_pop_item, onBeanList);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerOrder.setAdapter(onAdapter);
        onAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                numbers.setText(onBeanList.get(position).getItemName());
                window.dismiss();
                numbers.setChecked(false);
            }
        });
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                numbers.setChecked(false);
            }
        });

    }


    @OnClick({R.id.cb_numbers, R.id.et_order_home})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cb_numbers:
                numbers.setChecked(true);
                numbers.setCursorVisible(true);
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
               window.showAsDropDown(numbers);
                break;
            case R.id.et_order_home:
                orderNumber.setCursorVisible(true);
                orderNumber.setTextColor(this.getResources().getColor(R.color.white));
                orderNumber.setHint(null);
                break;


        }
    }
}
