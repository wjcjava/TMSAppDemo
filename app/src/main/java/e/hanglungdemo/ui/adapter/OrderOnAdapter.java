package e.hanglungdemo.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import e.hanglungdemo.R;
import e.hanglungdemo.ui.bean.OrderOnBean;

public class OrderOnAdapter extends BaseQuickAdapter <OrderOnBean,BaseViewHolder>{
    public OrderOnAdapter(int layoutResId, List<OrderOnBean> list) {
        super(layoutResId,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderOnBean item) {

        helper.setText(R.id.item_name,item.getItemName());
    }


}
