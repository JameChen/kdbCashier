package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.ui.SaleDetailActivity;

/**
 * Created by jame on 2018/12/28.
 */

public class DetailRefundaDapter extends BaseQuickAdapter<SaleDetailBean.RefundListBean, BaseViewHolder> {
    private Context context;
    public final static int TYPE_REFUND = 1;
    public final static int TYPE_CHANGE = 2;
    public final static int TYPE_PRIMARY = 3;
    public final static int TYPE_DEFUAT= 0;
    private int Type = TYPE_REFUND;
    private boolean isBlueConnect;

    public void setBlueConnect(boolean blueConnect) {
        isBlueConnect = blueConnect;
    }

    private PrintLister printLister;

    public void setPrintLister(PrintLister printLister) {
        this.printLister = printLister;
    }

    public void setType(int type) {
        Type = type;
    }

    public DetailRefundaDapter(Context context) {
        super(R.layout.item_detail_refund, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SaleDetailBean.RefundListBean item) {
        if (item != null) {
            TextView item_detail_print=helper.getView(R.id.item_detail_print);
            if (isBlueConnect) {
                item_detail_print.setBackground(context.getResources().getDrawable(R.drawable.btn_d_red));
                item_detail_print.setEnabled(true);
            } else {
                item_detail_print.setEnabled(false);
                item_detail_print.setBackground(context.getResources().getDrawable(R.drawable.btn_d_gray));

            }
            if (Type == TYPE_REFUND) {
                helper.setGone(R.id.item_detail_print,true);
                helper.setGone(R.id.layout_refund, true);
                helper.setGone(R.id.sale_detail_txt07, false);
                helper.setGone(R.id.sale_detail_txt08, false);
                helper.setGone(R.id.layout_change_origin,false);
                helper.setGone(R.id.sale_detail_look,false);
                helper.setText(R.id.sale_detail_txt01, "退货单号：" + item.getCode());
                helper.setText(R.id.sale_detail_txt02, "经手人：" + item.getOptUserName());
                helper.setText(R.id.sale_detail_txt03, "退款方式：" + item.getRefundPayType());
                helper.setText(R.id.sale_detail_txt04, "退款原因：" + item.getReason());
                helper.setText(R.id.sale_detail_txt05, "退货时间：" + item.getCreateTimeX());
                helper.setText(R.id.sale_detail_txt06, "退货总数：共" + item.getTotalQty() + "件，" + item.getAmount() + "元");
            } else {
                helper.setGone(R.id.layout_change_origin,true);
                helper.setGone(R.id.layout_refund, false);
                helper.setGone(R.id.sale_detail_txt07, true);
                helper.setGone(R.id.sale_detail_txt08, true);
                if (Type==TYPE_CHANGE){
                    helper.setGone(R.id.item_detail_print,true);
                    helper.setGone(R.id.sale_detail_look,true);
                    helper.setText(R.id.sale_detail_txt08, "销售总数：共" + item.getTotalQty() + "件，" + item.getAmount() + "元");
                    helper.setText(R.id.sale_detail_txt07, "销售单号：" + item.getCode());
                }else {
                    helper.setGone(R.id.item_detail_print,false);
                    helper.setGone(R.id.sale_detail_look,false);
                    helper.setText(R.id.sale_detail_txt07, "换货单号：" + item.getCode());
                    helper.setText(R.id.sale_detail_txt08, "换货总数：共" + item.getTotalQty() + "件，" + item.getAmount() + "元");
                }
            }
            item_detail_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (printLister!=null)
                        printLister.printItem(item,Type);
                }
            });
            View sale_detail_look=helper.getView(R.id.sale_detail_look);
            sale_detail_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Type==TYPE_CHANGE){
                        Intent intent = new Intent(context, SaleDetailActivity.class);
                        intent.putExtra("orderCode", item.getCode());
                        context.startActivity(intent);
                    }
                }
            });
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linearLayoutManager3.setSmoothScrollbarEnabled(true);
            linearLayoutManager3.setAutoMeasureEnabled(true);
            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(linearLayoutManager3);
            RefundItemsAdapter adapter = new RefundItemsAdapter(context);
            adapter.setType(Type);
            adapter.setNewData(item.getItems());
            recyclerView.setAdapter(adapter);
        }

    }
    public interface PrintLister{
        void  printItem(SaleDetailBean.RefundListBean item,int pType);
    }
}
