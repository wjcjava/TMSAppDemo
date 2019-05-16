package e.hanglungdemo.ui.home.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.ui.adapter.TraceListAdapter;
import e.hanglungdemo.ui.bean.TraceBean;
import e.library.BaseActivity;

/**
 * 订单跟踪
 */
public class  TrackingActivity extends BaseActivity {
    @Bind(R.id.title_title)
    TextView trackTitle;
    @Bind(R.id.lvTrace)
    ListView lvTrace;
    @Bind(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.re_track_orders)
    RelativeLayout re_track_orders;
    private List<TraceBean> traceBeanList = new ArrayList<>(10);
    public TraceListAdapter adapter;
    private static boolean isFirstEnter = true;
    private Handler handler = new Handler();
    private String[] time = {"2018年9月2日 上午12:04:01", "2018年9月2日 上午9:57:25",
            "2018年9月1日 下午4:43:29", "2018年9月1日 上午9:9:21",
            "2018年9月1日 上午1:53:14", "2018年9月1日 上午1:50:18",
            "2018年9月2日 上午9:27:58", "2018年9月1日 上午9:9:21",
            "2018年9月1日 上午1:53:14", "2018年9月1日 上午1:50:18"};
    private String[] schedule = {"在湖17846921506北武汉洪山区17846921506光谷公司长江社17846921506区便民服务站进行签收扫描，快件已被17846921506 已签收 签收联系电话：17846921506",
            "在湖北武汉洪山区光谷公司长江社区便民服务站进行派件扫描；派送业务员：老王；联系电话：17846921506",
            "在湖北武汉洪山区光谷公司进行快件扫描，将发往：湖北武汉洪山区光谷公司长江社区便民服务站",
            "从湖北武汉分拨中心发出，本次转运目的地：湖北武汉洪山区光谷公司",
            "在湖南长沙分拨中心进行装车扫描，即将发往：湖北武汉分拨中心",
            "在分拨中心湖南长沙分拨中心进行称重扫描",
            "在湖南隆回县公司进行到件扫描",
            "从湖北武汉分拨中心发出，本次转运目的地：湖北武汉洪山区光谷公司",
            "在湖南长沙分拨中心进行装车扫描，即将发往：湖北武汉分拨中心",
            "在分拨中心湖南长沙分拨中心进行称重扫描"};
    private TraceBean traceBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_tracking;
    }

    @Override
    protected void initView() {
        super.initView();
        trackTitle.setTextColor(getResources().getColor(R.color.white));
        trackTitle.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initData() {
        super.initData();
        initSwLayout();
        for (int i = 0; i < 5; i++) {
            traceBean = new TraceBean(time[i], schedule[i]);
            traceBeanList.add(traceBean);
        }
        adapter = new TraceListAdapter(this, traceBeanList);
        lvTrace.setAdapter(adapter);
    }



    public void initSwLayout() {

        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        //第一次进入演示刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.autoRefresh();
                }
            },1000);
        }

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        traceBeanList.clear();
                        for (int i = 0; i < 5; i++) {
                            TraceBean traceBean = new TraceBean(time[i], schedule[i]);
                            traceBeanList.add(traceBean);
                        }
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
                    }
                }, 2000);
            }


            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getCount() > time.length) {
                            Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                        } else {
                            for (int i = 6; i < time.length; i++) {
                                TraceBean traceBean = new TraceBean(time[i], schedule[i]);
                                traceBeanList.add(traceBean);
                            }
                            adapter.notifyDataSetChanged();
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 1000);
            }
        });
    }

    @OnClick({R.id.title_back, R.id.re_track_orders})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.re_track_orders:
                startActivity(new Intent(this, SignInActivity.class));
                break;

        }
    }
}
