package com.yiku.kdb_flat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nahuo.library.controls.LoadingDialog;
import com.nahuo.library.helper.FunctionHelper;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.model.bean.ShopItemListModel;
import com.yiku.kdb_flat.model.bean.WareHoseBean;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.WareHoseManageAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseManageActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public static String TAG = WarehouseManageActivity.class.getSimpleName();
    private WarehouseManageActivity vThis = this;
    private static final String ERROR_PREFIX = "error:";
    private LoadingDialog mloadingDialog;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private WareHoseManageAdapter adpater;
    private int mPageIndex = 1;
    private int mPageSize = 20;
    String searchKeyword = "";
    String itemcode = "";
    private View layout_search;
    private EditText et_search_code, et_search_word;
    private ImageView iv_right_01;
    private boolean isShowSearch = true;
    List<ShopItemListModel> itemList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_manage);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        vThis = this;
        findViewById(R.id.tvTLeft).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTitleCenter)).setText("库存列表");
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.sw);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.bg_red), getResources().getColor(R.color.red));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onLoadRefresh();
            }
        });
        swipeLayout.setRefreshing(true);
        adpater = new WareHoseManageAdapter(this);
        recyclerView.setAdapter(adpater);
        adpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ShopItemListModel> data = adapter.getData();
                ShopItemListModel bean = data.get(position);
                if (bean != null) {
                    startActivity(new Intent(vThis, WarehouseManageDetailActivity.class)
                            .putExtra(WarehouseManageDetailActivity.EXTRA_SOURCEID, bean.getAgentItemId()));
                }
            }
        });
        adpater.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        }, recyclerView);
        View empty = LayoutInflater.from(this).inflate(R.layout.textview_empty, null);
        TextView tv_empty = (TextView) empty.findViewById(R.id.tv_empty);
        tv_empty.setText("沒有数据");
        adpater.setEmptyView(empty);
        layout_search = findKdbViewById(R.id.layout_search);
        iv_right_01 = (ImageView) findViewById(R.id.iv_right_01);
        iv_right_01.setImageResource(R.drawable.find);
        iv_right_01.setOnClickListener(this);
        iv_right_01.setVisibility(View.VISIBLE);
        layout_search.setVisibility(View.GONE);
        et_search_code = findKdbViewById(R.id.et_search_code);
        et_search_word = findKdbViewById(R.id.et_search_word);
        et_search_code.setFocusable(true);
        et_search_code.setFocusableInTouchMode(true);
        et_search_code.requestFocus();
        et_search_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP)) {
                    String scan = et_search_code.getText().toString().trim();
                    String text2 = et_search_word.getText().toString().trim();
                    et_search_code.setText("");
                    et_search_code.setHint(scan + "");
                    et_search_code.setHintTextColor(ContextCompat.getColor(vThis, R.color.kdb_gray));
                    String scanTxt = et_search_code.getHint().toString().trim();
                    if (!TextUtils.isEmpty(text2)) {
                        searchKeyword = text2;
                    } else {
                        searchKeyword = "";
                    }
                    clearScanItemcodeData(true, scanTxt);
                } else if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    String text = et_search_code.getText().toString().trim();
                    String text2 = et_search_word.getText().toString().trim();
                    if (!TextUtils.isEmpty(text2)) {
                        searchKeyword = text2;
                    } else {
                        searchKeyword = "";
                    }
                    if (!TextUtils.isEmpty(text)) {
                        itemcode = text;
                    } else {
                        itemcode = "";
                    }
                    onLoadRefresh();
                }
                return false;
            }
        });
        et_search_word.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    String text = et_search_word.getText().toString().trim();
                    String text2 = et_search_code.getText().toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        searchKeyword = text;
                    } else {
                        searchKeyword = "";
                    }
                    if (!TextUtils.isEmpty(text2)) {
                        itemcode = text2;
                    } else {
                        itemcode = "";
                    }
                    onLoadRefresh();
                }
                return false;
            }
        });

        onLoadRefresh();
    }

    private void onLoadMore() {
        bindItemData(false);
    }

    private void onLoadRefresh() {
        bindItemData(true);
    }

    private void bindItemData(boolean isRefresh) {
        if (isRefresh) {
            adpater.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }
        getWareHoseData(isRefresh, mPageIndex, mPageSize, searchKeyword, itemcode);
    }

    private void clearScanItemcodeData(boolean isRefresh, String code) {
        itemcode = "";
        if (isRefresh) {
            adpater.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }
        getWareHoseData(isRefresh, mPageIndex, mPageSize, searchKeyword, code);
    }

    /**
     * 获取库存
     */
    public void getWareHoseData(final boolean mIsRefresh, int pageIndex, int pageSize, String searchKeyword,
                                String itemcode) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("keyword", URLEncoder.encode(searchKeyword, "UTF-8"));
            params.put("itemcode", itemcode);
            params.put("pageSize", pageSize);
            params.put("pageIndex", pageIndex);
            addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
            ).GetInStoageList(params)
                    .compose(RxUtil.<KDBResponse<WareHoseBean>>rxSchedulerHelper())
                    .compose(RxUtil.<WareHoseBean>handleResult())
                    .subscribeWith(new CommonSubscriber<WareHoseBean>(vThis, true, R.string.loading) {
                        @Override
                        public void onNext(WareHoseBean saleBean) {
                            super.onNext(saleBean);
                            if (mIsRefresh)
                                swipeLayout.setRefreshing(false);
                            if (saleBean != null) {
                                List<ShopItemListModel> list = saleBean.getList();
                                if (mIsRefresh) {
                                    if (itemList != null)
                                        itemList.clear();
                                    if (!ListUtils.isEmpty(list))
                                        itemList.addAll(list);
                                } else {
                                    if (!ListUtils.isEmpty(list))
                                        itemList.addAll(list);
                                }
                                adpater.setNewData(itemList);
//                                    if (itemList.size() > 0) {
//                                        vThis.showEmptyView(false, "");
//                                    } else {
//                                        vThis.showEmptyView(true, "您还没有销售数据");
//                                    }

                                if (mIsRefresh) {
                                    adpater.setEnableLoadMore(true);
                                    if (ListUtils.isEmpty(list)) {
                                        adpater.loadMoreEnd(true);
                                    }

                                } else {
                                    if (ListUtils.isEmpty(list)) {
                                        adpater.loadMoreEnd(false);
                                    }
                                }
                            }

                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }
//    List<InventoryBean.ListBean> itemList = new ArrayList<>();
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
//            mloadingDialog = new LoadingDialog(vThis);
//            mloadingDialog.start(getString(R.string.items_loadData_loading));
//        }
//
//        @Override
//        protected void onPostExecute(Object object) {
//            mloadingDialog.stop();
//            if (mIsRefresh)
//                swipeLayout.setRefreshing(false);
//            if (object instanceof String && ((String) object).startsWith(ERROR_PREFIX)) {
//                String msg = ((String) object).replace(ERROR_PREFIX, "");
//                ViewHub.showLongToast(vThis, msg);
//                return;
//            }
//            InventoryBean inventoryBean = (InventoryBean) object;
//
//            if (inventoryBean != null) {
//                List<InventoryBean.ListBean> data = inventoryBean.getList();
//                if (mIsRefresh) {
//                    if(itemList!=null)
//                        itemList.clear();
//                    if (!ListUtils.isEmpty(data))
//                        itemList.addAll(data);
//                } else {
//                    if (!ListUtils.isEmpty(data))
//                        itemList.addAll(data);
//                }
//                adpater.setNewData(itemList);
//                adpater.notifyDataSetChanged();
//                if (mIsRefresh) {
//                    adpater.setEnableLoadMore(true);
//                    //  swipeLayout.setRefreshing(false);
//                    if (ListUtils.isEmpty(data)) {
//                        adpater.loadMoreEnd(true);
//                    }
//
//                } else {
//                    //
//                    if (ListUtils.isEmpty(data)) {
//                        adpater.loadMoreEnd(false);
//                    }
//                }
//            }
//        }
//
//        @Override
//        protected Object doInBackground(Void... params) {
//            try {
//                InventoryBean saleBean = OtherAPI.getSkuStockList(vThis, mPageIndex, mPageSize);
//                return saleBean;
//            } catch (Exception ex) {
//                Log.e(TAG, "获取盘点列表发生异常");
//                ex.printStackTrace();
//                return ex.getMessage() == null ? ERROR_PREFIX : ex.getMessage();
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTLeft:
                finish();
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
                searchKeyword = "";
                itemcode = "";
                et_search_word.setText("");
                et_search_code.setText("");
                break;
        }
    }
}
