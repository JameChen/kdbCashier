package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.ProductModel;

/**
 * Created by jame on 2018/12/21.
 */

public class WareHoseManageDetailAdapter extends BaseQuickAdapter<ProductModel, BaseViewHolder> {
    Context context;

    public WareHoseManageDetailAdapter(Context context) {
        super(R.layout.item_ware_house_detail, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductModel item) {
        if (item != null) {
            helper.setText(R.id.item_detail_sizecolor_item_1, item.getColor());
            helper.setText(R.id.item_detail_sizecolor_item_2, item.getSize());
            helper.setText(R.id.item_detail_sizecolor_item_3,item.getQty()+"");
            helper.setText(R.id.item_detail_sizecolor_item_4, item.getInventoryQty()+"");
            helper.setText(R.id.item_detail_sizecolor_item_5, item.getProfitLossQty()+"");
            helper.setGone(R.id.item_detail_sizecolor_item_4,false);
            helper.setGone(R.id.item_detail_sizecolor_item_5,false);
        }
    }
}
