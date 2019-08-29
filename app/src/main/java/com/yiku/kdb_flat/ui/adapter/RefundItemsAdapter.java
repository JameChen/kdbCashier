package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.ui.SaleDetailActivity;
import com.yiku.kdb_flat.utils.GlideUtls;

import static com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter.TYPE_PRIMARY;
import static com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter.TYPE_REFUND;

/**
 * Created by jame on 2018/12/28.
 */

public class RefundItemsAdapter extends BaseQuickAdapter<SaleDetailBean.RefundListBean.ItemsBean,BaseViewHolder> {
    private Context context;
    private int Type=TYPE_REFUND;

    public void setType(int type) {
        Type = type;
    }
    public RefundItemsAdapter(Context context) {
        super(R.layout.lvitem_sale_detail_product, null);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SaleDetailBean.RefundListBean.ItemsBean item) {
        if (item!=null){
            ImageView imageView=helper.getView(R.id.img_order_detail_icon);
            GlideUtls.glidePic(context, ImageUrlExtends.getImageUrl(item.getCover(), 8),imageView);
            helper.setText(R.id.txt_order_detail_name,item.getTitle());
            helper.setText(R.id.txt_order_detail_info,item.getColor()+"/"+item.getSize()+"/"+item.getQty()+"件");
            helper.setText(R.id.txt_order_detail_money,"¥"+item.getPrice());
            helper.setText(R.id.txt_order_detail_ku,"款号："+item.getCode());
            helper.setText(R.id.txt_order_detail_summary,"");
            View sale_detail_look=helper.getView(R.id.sale_detail_look);
            if (Type==TYPE_PRIMARY){
                helper.setText(R.id.sale_detail_txt06,"原订单号："+item.getOrgOrderCode());
                helper.setGone(R.id.layout_change,true);
                helper.setGone(R.id.sale_detail_look,true);
            }else {
                helper.setGone(R.id.layout_change,false);
                helper.setGone(R.id.sale_detail_look,false);

            }
            sale_detail_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Type==TYPE_PRIMARY){
                        Intent intent = new Intent(context, SaleDetailActivity.class);
                        intent.putExtra("orderCode", item.getOrgOrderCode());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
