package com.yiku.kdb_flat.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.model.bean.StoageDetailBean;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.WareHoseManageDetailAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.GlideUtls;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;

public class WarehouseManageDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private WarehouseManageDetailActivity vThis = this;
    private String TAG = WarehouseManageDetailActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private int SourceID;
    public static String EXTRA_SOURCEID = "EXTRASOURCEID";
    private WareHoseManageDetailAdapter adapter;
    private ImageView item_cover;
    private TextView item_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_manage_detail);
        vThis = this;
        findViewById(R.id.tvTLeft).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTitleCenter)).setText("库存详情");
        recyclerView = findKdbViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(vThis));
        adapter=new WareHoseManageDetailAdapter(vThis);
        View view=LayoutInflater.from(vThis).inflate(R.layout.warehouse_detail_head,null);
        item_cover= (ImageView) view.findViewById(R.id.item_cover);
        item_right= (TextView) view.findViewById(R.id.item_right);
        adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
        if (getIntent() != null)
            SourceID = getIntent().getIntExtra(vThis.EXTRA_SOURCEID, 0);
        getData();
    }

    private void getData() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetInStoageDetail(SourceID)
                .compose(RxUtil.<KDBResponse<StoageDetailBean>>rxSchedulerHelper())
                .compose(RxUtil.<StoageDetailBean>handleResult())
                .subscribeWith(new CommonSubscriber<StoageDetailBean>(vThis, true, R.string.loading) {
                    @Override
                    public void onNext(StoageDetailBean bean) {
                        super.onNext(bean);
                        if (bean != null) {
                            if (!ListUtils.isEmpty(bean.getColorSize())){
                                adapter.setNewData(bean.getColorSize());
                            }else {
                                adapter.setNewData(null);
                            }
                            if (item_cover!=null){
                                GlideUtls.glidePic(vThis, ImageUrlExtends.getImageUrl(bean.getCover(),12),item_cover);
                            }
                            if (item_right!=null)
                                item_right.setText("款号：" + bean.getCode() + "\n\n库存数量："
                                    + bean.getStockQty() + "\n\n上次盘点：" + bean.getLastStockTime());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTLeft:
                finish();
                break;
        }
    }
}
