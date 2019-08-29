package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.utils.GlideUtls;

import java.util.List;

public class SaleDetailItemAdapter extends BaseQuickAdapter<SaleDetailBean.ProductsBean, BaseViewHolder> {

    public List<SaleDetailBean.ProductsBean> models;
    private Context mContext;

    public SaleDetailItemAdapter(Context context) {
        super(R.layout.lvitem_sale_detail_product, null);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, SaleDetailBean.ProductsBean model) {
        if (model != null) {
            String imageUrl = model.getCover();
            imageUrl = ImageUrlExtends.getImageUrl(imageUrl, 8);
            ImageView imageView = helper.getView(R.id.img_order_detail_icon);
            GlideUtls.glidePic(mContext, imageUrl, imageView);
            helper.setText(R.id.txt_order_detail_summary, model.getRefundSummary());
            helper.setText(R.id.txt_order_detail_ku, "款号：" + model.getSku());
            helper.setText(R.id.txt_order_detail_name, model.getName());
            helper.setText(R.id.txt_order_detail_info, model.getColor() + "/" + model.getSize() + "/" + model.getQty() + "件");
            helper.setText(R.id.txt_order_detail_summary, model.getRefundSummary());
            helper.setText(R.id.txt_order_detail_money,model.getPriceTag());
//            if (model.isPointChange()) {
//                helper.setText(R.id.txt_order_detail_money, "原价：" + model.getOrgPrice() + ",折扣价：¥" + model.getPrice() + "元+" + model.getPoint() + "积分");
//            } else {
//                if (model.isIsOnSale() || model.isChanged()) {
//                    if (Double.parseDouble(model.getDiscountPrice()) == Double.parseDouble(model.getPrice())) {
//                        helper.setText(R.id.txt_order_detail_money, "原价：" + model.getOrgPrice() + ",特价：¥" + model.getPrice());
//                    } else {
//                        helper.setText(R.id.txt_order_detail_money, "原价：" + model.getOrgPrice() + ",折扣价：¥" + model.getDiscountPrice());
//                    }
//
//                } else {
//                    if (Double.parseDouble(model.getDiscountPrice()) == Double.parseDouble(model.getPrice())) {
//                        helper.setText(R.id.txt_order_detail_money, "价格：" + model.getPrice());
//                    } else {
//                        helper.setText(R.id.txt_order_detail_money, "价格：" + model.getPrice() + ",折扣价：¥" + model.getDiscountPrice());
//                    }
//
//                }
//            }


        }
    }


}
