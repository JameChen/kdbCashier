package com.yiku.kdb_flat.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nahuo.library.helper.FunctionHelper;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.DropDownView;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.model.bean.RefundBean;
import com.yiku.kdb_flat.model.bean.SelectBean;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.RefundAadpater;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;

public class RefundActivity extends BaseAppCompatActivity implements RefundAadpater.EditChangQty, View.OnClickListener, DropDownView.OnItemClickListener {
    private String TAG = RefundActivity.class.getSimpleName();
    private RefundActivity vThis = this;
    public static final String EXTRA_ORDER = "EXTRA_ORDER";
    public static final String EXTRA_ORDER_MOBILE = "EXTRA_ORDER_MOBILE";
    public final static int REQUEST_CODE = 100;
    private String ordercode = "", mobile = "";
    private RecyclerView recyclerView;
    private TextView tv_summary, tv_ok;
    private View head;
    private TextView tv_code, tv_name, tv_qty;
    private DropDownView tv_sort_type, tv_sort_opt, tv_sort_reason;
    public final static int TYPE_PAY = 1;
    public final static int TYPE_OPT = 2;
    public final static int TYPE_REASON = 3;
    private int defautId = -1;
    private int RefundPayTypeID = defautId, RefundReasonID = defautId, OptUserID = defautId;
    private RefundAadpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        vThis = this;
        findViewById(R.id.tvTLeft).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTitleCenter)).setText("退货");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adpater = new RefundAadpater(vThis);
        adpater.setEditChangQty(this);
        recyclerView.setAdapter(adpater);
        tv_summary = findKdbViewById(R.id.tv_summary);
        if (tv_summary != null)
            tv_summary.setText("退货数量：0件          金额：0.00");
        tv_ok = findKdbViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);

        head = LayoutInflater.from(vThis).inflate(R.layout.refund_head, null);
        adpater.addHeaderView(head);
        tv_code = (TextView) head.findViewById(R.id.tv_code);
        tv_name = (TextView) head.findViewById(R.id.tv_name);
        tv_qty = (TextView) head.findViewById(R.id.tv_qty);
        tv_sort_type = (DropDownView) head.findViewById(R.id.tv_sort_type);
        tv_sort_opt = (DropDownView) head.findViewById(R.id.tv_sort_opt);
        tv_sort_reason = (DropDownView) head.findViewById(R.id.tv_sort_reason);
        tv_sort_type.setNUSELETED_SHOW_NAME("请选择");
        tv_sort_opt.setNUSELETED_SHOW_NAME("请选择");
        tv_sort_reason.setNUSELETED_SHOW_NAME("请选择");
        tv_sort_type.setType(TYPE_PAY);
        tv_sort_opt.setType(TYPE_OPT);
        tv_sort_reason.setType(TYPE_REASON);
        tv_sort_type.setOnItemClickListener(this);
        tv_sort_opt.setOnItemClickListener(this);
        tv_sort_reason.setOnItemClickListener(this);
        if (getIntent() != null) {
            ordercode = getIntent().getExtras().getString(EXTRA_ORDER);
            mobile = getIntent().getStringExtra(EXTRA_ORDER_MOBILE);
        }
        getRefundOrder();
    }

    private void getRefundOrder() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).getRefundOrder(ordercode)
                .compose(RxUtil.<KDBResponse<RefundBean>>rxSchedulerHelper())
                .compose(RxUtil.<RefundBean>handleResult())
                .subscribeWith(new CommonSubscriber<RefundBean>(vThis, true, R.string.loading) {
                    @Override
                    public void onNext(RefundBean bean) {
                        super.onNext(bean);
                        if (bean != null) {
                            if (tv_qty != null)
                                tv_qty.setText("成交数量：" + bean.getOrderQty());
                            if (tv_name != null)
                                tv_name.setText("客户姓名：" + mobile);
                            if (tv_sort_type != null) {
                                tv_sort_type.setupDataList(bean.getReasonPayType());
                                tv_sort_type.setText(bean.getReasonPayType().get(0).getName());
                                RefundPayTypeID = bean.getReasonPayType().get(0).getID();
                            }
                            if (tv_sort_opt != null) {
                                tv_sort_opt.setupDataList(bean.getOptUsers());
                                tv_sort_opt.setText(bean.getOptUsers().get(0).getName());
                                OptUserID = bean.getOptUsers().get(0).getID();
                            }
                            if (tv_sort_reason != null) {
                                tv_sort_reason.setupDataList(bean.getReasons());
                                tv_sort_reason.setText(bean.getReasons().get(0).getName());
                                RefundReasonID = bean.getReasons().get(0).getID();
                            }
//                            List<RefundBean.ItemListBean> list = new ArrayList<RefundBean.ItemListBean>();
//
//                            for (int i = 0; i < 100; i++) {
//                                RefundBean.ItemListBean b = new RefundBean.ItemListBean();
//                                b.setCanRefundQty(5);
//                                b.setCover("upyun:nahuo-img-server://3636/item/153785408296-1235.jpg");
//                                b.setTID(i);
//                                list.add(b);
//                            }

                            if (adpater != null)
                                adpater.setNewData(bean.getItemList());

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
            case R.id.tv_ok:
                saveRefund();

                break;
        }
    }

    private void saveRefund() {
        if (RefundPayTypeID == defautId) {
            ViewHub.showLongToast(vThis, "请选择退款方式");
            return;
        }
        if (OptUserID == defautId) {
            ViewHub.showLongToast(vThis, "请选择经手人");
            return;
        }
        if (RefundReasonID == defautId) {
            ViewHub.showLongToast(vThis, "请选择退款原因");
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray();
            if (adpater != null) {
                List<RefundBean.ItemListBean> data = adpater.getData();
                if (ListUtils.isEmpty(data)) {
                    ViewHub.showLongToast(vThis, "商品列表不能为空");
                    return;
                } else {
                    for (RefundBean.ItemListBean bean : data) {
                        if (bean.getRefundQty() > 0) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("ItemID", bean.getItemID());
                            jsonObject.put("Color", bean.getColor());
                            jsonObject.put("Size", bean.getSize());
                            jsonObject.put("Qty", bean.getRefundQty());
                            jsonArray.put(jsonObject);
                        }
                    }
                }
            }
            if (jsonArray.length() <= 0) {
                ViewHub.showLongToast(vThis, "请填写至少一个退货数量");
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("OrderCode", ordercode);
            jsonObject.put("RefundPayTypeID", RefundPayTypeID);
            jsonObject.put("RefundReasonID", RefundReasonID);
            jsonObject.put("OptUserID", OptUserID);
            jsonObject.put("RefundItemList", jsonArray);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
            addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
            ).refundSave(body)
                    .compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                    .compose(RxUtil.<Object>handleResult())
                    .subscribeWith(new CommonSubscriber<Object>(vThis, true, "正在保存退货单") {
                        @Override
                        public void onNext(Object bean) {
                            super.onNext(bean);
                            setResult(RESULT_OK);
                            vThis.finish();
                        }
                    }));


        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    @Override
    public void onItemClick(SelectBean map, int pos, int realPos, int Type) {
        switch (Type) {
            case TYPE_PAY:
                if (map != null) {
                    RefundPayTypeID = map.getID();
                } else {
                    RefundPayTypeID = defautId;
                }

                break;
            case TYPE_OPT:
                if (map != null) {
                    OptUserID = map.getID();
                } else {
                    OptUserID = defautId;
                }

                break;
            case TYPE_REASON:
                if (map != null) {
                    RefundReasonID = map.getID();
                } else {
                    RefundReasonID = defautId;
                }
                break;
        }
    }

    @Override
    public void OnEditQty() {
        if (adpater != null) {
            List<RefundBean.ItemListBean> data = adpater.getData();
            if (ListUtils.isEmpty(data)) {
                if (tv_summary != null)
                    tv_summary.setText("退货数量：0件          金额：0.00");
            } else {
                int count = 0;
                double money = 0;
                for (RefundBean.ItemListBean bean : data) {
                    count += bean.getRefundQty();
                    money = money + (bean.getRefundQty() * Double.parseDouble(bean.getDiscountPrice()));
                }
                tv_summary.setText("退货数量：" + count + "件          金额：" + FunctionHelper.DoubleToString(money));
            }
        }
    }
}
