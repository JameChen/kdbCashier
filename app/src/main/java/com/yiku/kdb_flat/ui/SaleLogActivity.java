package com.yiku.kdb_flat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nahuo.library.controls.LoadingDialog;
import com.nahuo.library.controls.pulltorefresh.PullToRefreshListView;
import com.nahuo.library.helper.FunctionHelper;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.DropDownView;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.dialog.SortTimeDialog;
import com.yiku.kdb_flat.model.bean.OrderModel;
import com.yiku.kdb_flat.model.bean.SaleBean;
import com.yiku.kdb_flat.model.bean.SelectBean;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.SaleListAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleLogActivity extends BaseAppCompatActivity implements OnClickListener, DropDownView.OnItemClickListener {

    private static final String TAG = SaleLogActivity.class.getSimpleName();
    private SaleLogActivity vThis = this;
    private PullToRefreshListView pullRefreshListView;
    //private LoadDataTask loadDataTask;
    private LoadingDialog mloadingDialog;
    private TextView tvEmptyMessage, tv_summary;
    private SaleListAdapter adapter;
    private List<OrderModel.OrderListBean> itemList = null;
    private View emptyView;
    private int mPageIndex = 1;
    private int mPageSize = 20;
    private String Summary = "";
    private int mStatus = -1;
    private String beginTime = "", endTime = "";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int pay_type, sellerUserID;
    private View rl_layout;
    private DropDownView tv_sort_time, tv_sort_order_type, tv_sort_order, tv_sort_seller, tv_sort_pay;
    private List<SelectBean> SellerUsers = new ArrayList<>();
    private List<SelectBean> payList = new ArrayList<>();
    private List<SelectBean> orderList = new ArrayList<>();
    private List<SelectBean> timeList = new ArrayList<>();
    private List<SelectBean> pList = new ArrayList<>();
    public final static int Type_Saler = 1;
    public final static int Type_Pay = 2;
    public final static int Type_Methor = 3;
    public final static int Type_Time = 4;
    public final static int Type_Order_Type = 5;

    private String mobile = "", itemkeyword = "";
    private View layout_search;
    private EditText et_search_mobile, et_search_word;
    private ImageView iv_right_01;
    private boolean isShowSearch = true;
    private int orderTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 设置自定义标题栏
        setContentView(R.layout.activity_sale_list);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//                R.layout.layout_titlebar_default);// 更换自定义标题栏布局
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (loadDataTask != null)
//            loadDataTask.cancel(true);
    }

    private void initView() {
        isShowSearch = true;
        layout_search = findViewById(R.id.layout_search);
        et_search_mobile = (EditText) findViewById(R.id.et_search_mobile);
        et_search_word = (EditText) findViewById(R.id.et_search_word);
        iv_right_01 = (ImageView) findViewById(R.id.iv_right_01);
        iv_right_01.setImageResource(R.drawable.find);
        iv_right_01.setOnClickListener(this);
        iv_right_01.setVisibility(View.VISIBLE);
        layout_search.setVisibility(View.GONE);
        et_search_mobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = et_search_mobile.getText().toString().trim();
                    String text2 = et_search_word.getText().toString().trim();
                    if (!TextUtils.isEmpty(text2)) {
                        itemkeyword = text2;
                    } else {
                        itemkeyword = "";
                    }
                    if (!TextUtils.isEmpty(text)) {
                        mobile = text;
                    } else {
                        mobile = "";
                    }
                    onMyRefresh();
                }
                return false;
            }
        });
        et_search_word.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = et_search_word.getText().toString().trim();
                    String text2 = et_search_mobile.getText().toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        itemkeyword = text;
                    } else {
                        itemkeyword = "";
                    }
                    if (!TextUtils.isEmpty(text2)) {
                        mobile = text2;
                    } else {
                        mobile = "";
                    }
                    onMyRefresh();
                }
                return false;
            }
        });

        emptyView = findViewById(R.id.empty_view);
        rl_layout = findViewById(R.id.rl_layout);
        // 标题栏
        TextView tvTitle = (TextView) findViewById(R.id.tvTitleCenter);
        TextView btnLeft = (TextView) findViewById(R.id.tvTLeft);
//        ImageView titlebar_icon_right = (ImageView) findViewById(R.id.titlebar_icon_right);
//        titlebar_icon_right.setImageResource(R.drawable.pn_message_left_white);
//        titlebar_icon_right.setOnClickListener(this);
//        titlebar_icon_right.setVisibility(View.GONE);
        tvTitle.setText("销售单");
        btnLeft.setText(R.string.titlebar_btnBack);
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(this);
        tv_sort_time = (DropDownView) findViewById(R.id.tv_sort_time);
        tv_sort_time.setNUSELETED_SHOW_NAME("选择时间");
        // tv_sort_time.setOnClickListener(this);
        tv_sort_time.setOnItemClickListener(this);
        tv_sort_order_type = findKdbViewById(R.id.tv_sort_order_type);
        tv_sort_order_type.setType(Type_Order_Type);
        tv_sort_order_type.setNUSELETED_SHOW_NAME("普通订单");
        orderTypeID = 0;
        tv_sort_order_type.setOnItemClickListener(this);
        tv_sort_order = (DropDownView) findViewById(R.id.tv_sort_order);
        tv_sort_seller = (DropDownView) findViewById(R.id.tv_sort_seller);
        tv_sort_pay = (DropDownView) findViewById(R.id.tv_sort_pay);
        tv_sort_order.setType(Type_Methor);
        tv_sort_seller.setType(Type_Saler);
        tv_sort_pay.setType(Type_Pay);
        tv_sort_time.setType(Type_Time);
        tv_sort_order.setNUSELETED_SHOW_NAME("全部订单");
        tv_sort_seller.setNUSELETED_SHOW_NAME("销售员");
        tv_sort_pay.setNUSELETED_SHOW_NAME("支付方式");
        tv_sort_seller.setOnItemClickListener(this);
        payList.clear();
        pList.clear();
        SelectBean p2 = new SelectBean();
        p2.setID(1);
        p2.setName("换货单");
        pList.add(p2);
        tv_sort_order_type.setupDataList(pList);
//        SelectBean s0=new SelectBean();
//        s0.setID(0);
//        s0.setName("支付方式");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       // Date tomorrow = FunctionHelper.GetDateTime(1);
        Date now = FunctionHelper.GetDateTime(0);
        Date yesterday = FunctionHelper.GetDateTime(-1);
        Date weekday = FunctionHelper.GetDateTime(-7);
        Date month = FunctionHelper.GetDateTime(-30);
        SelectBean sst1 = new SelectBean();
        sst1.setID(1);
        sst1.setName("今天");
        sst1.setStart_time(df.format(now));
        sst1.setEnd_time(df.format(now));
        SelectBean sst2 = new SelectBean();
        sst2.setID(2);
        sst2.setName("昨天");
        sst2.setStart_time(df.format(yesterday));
        sst2.setEnd_time(df.format(yesterday));
        SelectBean sst3 = new SelectBean();
        sst3.setID(3);
        sst3.setName("最近7天");
        sst3.setStart_time(df.format(weekday));
        sst3.setEnd_time(df.format(now));
        SelectBean sst4 = new SelectBean();
        sst4.setID(4);
        sst4.setName("最近30天");
        sst4.setStart_time(df.format(month));
        sst4.setEnd_time(df.format(now));
        SelectBean sst6 = new SelectBean();
        sst6.setID(6);
        sst6.setName("上个月份");
        sst6.setStart_time(FunctionHelper.getBeforeFirstMonthdate());
        sst6.setEnd_time(FunctionHelper.getBeforeLastMonthdate());
        SelectBean sst7 = new SelectBean();
        sst7.setID(7);
        sst7.setName("当前月份");
        sst7.setStart_time(FunctionHelper.getCurrentFirstMonthdate());
        sst7.setEnd_time(FunctionHelper.getCurrentLastMonthdate());

        SelectBean sst5 = new SelectBean();
        sst5.setID(-1);
        sst5.setName("任意时间");
        //payList.add(s0);
        timeList.add(sst1);
        timeList.add(sst2);
        timeList.add(sst3);
        timeList.add(sst4);
        timeList.add(sst6);
        timeList.add(sst7);
        timeList.add(sst5);
        tv_sort_time.setupDataList(timeList);
        SelectBean ss1 = new SelectBean();
        ss1.setID(1);
        ss1.setName("待支付");
        SelectBean ss2 = new SelectBean();
        ss2.setID(5);
        ss2.setName("已完成");
        SelectBean ss3 = new SelectBean();
        ss3.setID(10);
        ss3.setName("已取消");
        SelectBean ss4 = new SelectBean();
        ss4.setID(100);
        ss4.setName("退货单");
        //payList.add(s0);
        orderList.add(ss1);
        orderList.add(ss2);
        orderList.add(ss3);
        orderList.add(ss4);
        tv_sort_order.setupDataList(orderList);
        tv_sort_order.setOnItemClickListener(this);
        SelectBean s1 = new SelectBean();
        s1.setID(1);
        s1.setName("现金");
        SelectBean s2 = new SelectBean();
        s2.setID(2);
        s2.setName("微信");
        SelectBean s3 = new SelectBean();
        s3.setID(3);
        s3.setName("支付宝");
        SelectBean s4 = new SelectBean();
        s4.setID(4);
        s4.setName("POS机");
        //payList.add(s0);
        payList.add(s1);
        payList.add(s2);
        payList.add(s3);
        payList.add(s4);

        tv_sort_pay.setupDataList(payList);
        tv_sort_pay.setOnItemClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bg_red), getResources().getColor(R.color.red));
        LinearLayoutManager layoutManager = new LinearLayoutManager(vThis) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();
        // 初始化适配器
        tvEmptyMessage = (TextView) emptyView
                .findViewById(R.id.layout_empty_tvMessage);
        tv_summary = (TextView) findViewById(R.id.tv_summary);
        mloadingDialog = new LoadingDialog(vThis);
        pullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_listview_items);
//        pullRefreshListView.setCanLoadMore(true);
//        pullRefreshListView.setCanRefresh(true);
//        pullRefreshListView.setMoveToFirstItemAfterRefresh(true);
//        pullRefreshListView.setOnRefreshListener(this);
//        pullRefreshListView.setOnLoadListener(this);
//        pullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    position = 1;
//                }
//                OrderModel model = adapter.getItem(position - 1);
//                Intent intent = new Intent(vThis, SaleDetailActivity.class);
//                intent.putExtra("orderCode", model.getOrderCode());
//                startActivity(intent);
//            }
//        });

        // 刷新数据
        showEmptyView(false, "");
        emptyView.setOnClickListener(this);

        initItemAdapter();
        onMyRefresh();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SearchSaleLogActivity.REQUEST_CODE && resultCode == SearchSaleLogActivity.Resutlt_Code) {
//            if (data != null) {
//                mStatus = data.getIntExtra(SearchSaleLogActivity.EXTRA_STATUS, -1);
//                beginTime = data.getStringExtra(SearchSaleLogActivity.EXTRA_START_TIME);
//                endTime = data.getStringExtra(SearchSaleLogActivity.EXTRA_END_TIME);
//                loadDataTask = new LoadDataTask(true);
//                loadDataTask.execute((Void) null);
//            }
//        }
//    }

    private void loadData(boolean mIsRefresh) {
//        loadDataTask = new LoadDataTask(true);
//        loadDataTask.execute((Void) null);
        getSaleLog(mIsRefresh, mPageIndex, mPageSize, mStatus, beginTime, endTime, pay_type, sellerUserID, orderTypeID);

    }

    /**
     * 获取销售列表数据
     */
    public void getSaleLog(final boolean mIsRefresh, int pageIndex, int pageSize, int status, String start_time, String end_time, int payTypeID, int sellerUserID, int orderTypeID) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        params.put("statuID", status + "");
        params.put("fromtime", start_time);
        params.put("totime", end_time);
        params.put("sellerUserID", sellerUserID + "");
        params.put("payTypeID", payTypeID + "");
        params.put("mobile", mobile);
        params.put("itemkeyword", itemkeyword);
        params.put("orderTypeID", orderTypeID);
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetOrderList(params)
                .compose(RxUtil.<KDBResponse<SaleBean>>rxSchedulerHelper())
                .compose(RxUtil.<SaleBean>handleResult())
                .subscribeWith(new CommonSubscriber<SaleBean>(vThis, true, R.string.loading) {
                    @Override
                    public void onNext(SaleBean saleBean) {
                        super.onNext(saleBean);
                        if (mIsRefresh)
                            mSwipeRefreshLayout.setRefreshing(false);
                        if (saleBean != null) {
                            SellerUsers.clear();
                            if (!ListUtils.isEmpty(saleBean.getSellerUsers()))
                                SellerUsers.addAll(saleBean.getSellerUsers());
                            if (tv_sort_seller != null)
                                tv_sort_seller.setupDataList(SellerUsers);
                            Summary = saleBean.getSummary();
                            OrderModel result = saleBean.getOrders();
                            if (result != null) {
                                if (mIsRefresh) {
                                    if (itemList != null)
                                        itemList.clear();
                                    if (!ListUtils.isEmpty(result.getOrderList()))
                                        itemList.addAll(result.getOrderList());
                                } else {
                                    if (!ListUtils.isEmpty(result.getOrderList()))
                                        itemList.addAll(result.getOrderList());
                                }
                                adapter.setMyData(itemList);
                                if (itemList.size() > 0) {
                                    vThis.showEmptyView(false, "");
                                } else {
                                    vThis.showEmptyView(true, "您还没有销售数据");
                                }

                                if (mIsRefresh) {
                                    if (TextUtils.isEmpty(Summary)) {
                                        tv_summary.setVisibility(View.GONE);
                                    } else {
                                        tv_summary.setVisibility(View.VISIBLE);
                                        tv_summary.setText(Summary);
                                    }
                                    adapter.setEnableLoadMore(true);
                                    if (ListUtils.isEmpty(result.getOrderList())) {
                                        adapter.loadMoreEnd(true);
                                    }

                                } else {
                                    if (ListUtils.isEmpty(result.getOrderList())) {
                                        adapter.loadMoreEnd(false);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }));
    }

    private static final String ERROR_PREFIX = "error:";

    // 初始化数据
    private void initItemAdapter() {
        if (itemList == null)
            itemList = new ArrayList<>();

        adapter = new SaleListAdapter(vThis);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        }, mRecyclerView);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                List<OrderModel.OrderListBean> data = adapter.getData();
                OrderModel.OrderListBean bean = data.get(position);
                if (bean != null) {
                    Intent intent = new Intent(vThis, SaleDetailActivity.class);
                    intent.putExtra("orderCode", bean.getCode());
                    startActivity(intent);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onMyRefresh();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        //pullRefreshListView.setAdapter(adapter);

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tvTLeft:
                finish();
                break;
            case R.id.titlebar_icon_right:
//                Intent intent = new Intent(this, SearchSaleLogActivity.class);
//                startActivityForResult(intent, SearchSaleLogActivity.REQUEST_CODE);
                break;
            case R.id.iv_right_01:
                isShowSearch = !isShowSearch;
                if (isShowSearch) {
                    iv_right_01.setImageResource(R.drawable.find);
                    layout_search.setVisibility(View.GONE);
                    FunctionHelper.hideSoftInput(vThis);
                } else {
                    iv_right_01.setImageResource(R.drawable.reback);
                    layout_search.setVisibility(View.VISIBLE);
                }
                itemkeyword = "";
                mobile = "";
                et_search_mobile.setText("");
                et_search_word.setText("");
                break;
            case R.id.empty_view:
                onMyRefresh();
                break;

        }
    }

    public void onMyRefresh() {
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        bindItemData(true);
        if (itemList.size() == 0) {
            showEmptyView(false, "您还没有销售记录");
        } else {

        }

    }

    @Override
    public void onItemClick(SelectBean map, int pos, int realPos, int Type) {
        switch (Type) {
            case Type_Order_Type:
                if (map == null) {
                    orderTypeID=0;
                }else {
                    orderTypeID=map.getID();
                }
                break;
            case Type_Time:
                if (map == null) {
                    // pay_type=0;
                    vThis.beginTime = "";
                    vThis.endTime = "";
                } else {
                    if (map.getID() == -1) {
                        SortTimeDialog.getInstance(this).setPositive(new SortTimeDialog.PopDialogListener() {
                            @Override
                            public void onGetSortTimeDialogButtonClick(String beginTime, String endTime) {
                                vThis.beginTime = beginTime;
                                vThis.endTime = endTime;
                                onMyRefresh();
                            }
                        }).showDialog();
                        return;
                    } else  {
                        vThis.beginTime = map.getStart_time();
                        vThis.endTime = map.getEnd_time();
                    }
                }

                break;
            case Type_Pay:
                if (map == null) {
                    pay_type = 0;
                } else {
                    pay_type = map.getID();
                }
                break;
            case Type_Methor:
                if (map == null) {
                    mStatus = -1;
                } else {
                    mStatus = map.getID();
                }
                break;
            case Type_Saler:
                if (map == null) {
                    sellerUserID = 0;
                } else {
                    sellerUserID = map.getID();
                }
                break;
        }
        onMyRefresh();
    }

//    public class LoadDataTask extends AsyncTask<Void, Void, Object> {
//        private boolean mIsRefresh = false;
//
//        public LoadDataTask(boolean isRefresh) {
//            mIsRefresh = isRefresh;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mloadingDialog.start(getString(R.string.items_loadData_loading));
//        }
//
//        @Override
//        protected void onPostExecute(Object object) {
//            mloadingDialog.stop();
//            if (mIsRefresh)
//                mSwipeRefreshLayout.setRefreshing(false);
//            if (object instanceof String && ((String) object).startsWith(ERROR_PREFIX)) {
//                String msg = ((String) object).replace(ERROR_PREFIX, "");
//                ViewHub.showLongToast(vThis, msg);
//                return;
//            }
//            SaleBean saleBean = (SaleBean) object;
//            if (saleBean != null) {
//                SellerUsers.clear();
//                if (!ListUtils.isEmpty(saleBean.getSellerUsers()))
//                    SellerUsers.addAll(saleBean.getSellerUsers());
//                if (tv_sort_seller != null)
//                    tv_sort_seller.setupDataList(SellerUsers);
//                Summary = saleBean.getSummary();
//                OrderModel result = saleBean.getOrders();
//                if (result != null) {
//                    if (mIsRefresh) {
//                        if (itemList != null)
//                            itemList.clear();
//                        if (!ListUtils.isEmpty(result.getOrderList()))
//                            itemList.addAll(result.getOrderList());
//                    } else {
//                        if (!ListUtils.isEmpty(result.getOrderList()))
//                            itemList.addAll(result.getOrderList());
//                    }
//                    adapter.setMyData(itemList);
//                    if (itemList.size() > 0) {
//                        vThis.showEmptyView(false, "");
//                    } else {
//                        vThis.showEmptyView(true, "您还没有销售数据");
//                    }
//
//                    if (mIsRefresh) {
//                        if (TextUtils.isEmpty(Summary)) {
//                            tv_summary.setVisibility(View.GONE);
//                        } else {
//                            tv_summary.setVisibility(View.VISIBLE);
//                            tv_summary.setText(Summary);
//                        }
//                        adapter.setEnableLoadMore(true);
//                        if (ListUtils.isEmpty(result.getOrderList())) {
//                            adapter.loadMoreEnd(true);
//                        }
//
//                    } else {
//                        //
//                        if (ListUtils.isEmpty(result.getOrderList())) {
//                            adapter.loadMoreEnd(false);
//                        }
//                    }
//                    //adapter.loadMoreComplete();
//                }
//            }
//        }
//
//        @Override
//        protected Object doInBackground(Void... params) {
//            try {
//                // SaleBean saleBean = OtherAPI.getSaleLog(vThis, mPageIndex, mPageSize, mStatus, beginTime, endTime,pay_type,sellerUserID);
//
//                return null;
//            } catch (Exception ex) {
//                Log.e(TAG, "获取销售列表发生异常");
//                ex.printStackTrace();
//                return ex.getMessage() == null ? ERROR_PREFIX : ex.getMessage();
//            }
//        }
//    }

    private void bindItemData(boolean isRefresh) {
        if (isRefresh) {
            showEmptyView(false, "");
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }
        loadData(isRefresh);
    }

    /**
     * 显示空数据视图
     */
    private void showEmptyView(boolean show, String msg) {
        // pullRefreshListView.setVisibility(show ? View.GONE : View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
        tvEmptyMessage.setText(getString(R.string.empty_message));
    }

    public void onLoadMore() {
        bindItemData(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
