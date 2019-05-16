package e.hanglungdemo.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import e.hanglungdemo.R;
import e.hanglungdemo.ui.bean.OrderBean;

public class






OrderAdapter extends BaseQuickAdapter <OrderBean,BaseViewHolder>{
    public OrderAdapter(int layoutResId, List<OrderBean> list) {
        super(layoutResId,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setImageResource(R.id.iv_order_logo,item.getHomeLogo());
        helper.setText(R.id.tv_order_management,item.getManagement());
        helper.setText(R.id.tv_order_create,item.getOrderTitle());
        helper.setText(R.id.tv_oscheduling_submit,item.getOrderfunction());
    }
}
