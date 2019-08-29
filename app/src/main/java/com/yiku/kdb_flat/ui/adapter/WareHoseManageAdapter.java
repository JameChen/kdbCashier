package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.ShopItemListModel;
import com.yiku.kdb_flat.utils.GlideUtls;

/**
 * Created by jame on 2018/12/21.
 */

public class WareHoseManageAdapter extends BaseQuickAdapter<ShopItemListModel, BaseViewHolder> {
    Context context;

    public WareHoseManageAdapter(Context context) {
        super(R.layout.item_ware_house_manage, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopItemListModel item) {
        if (item != null) {
            helper.setGone(R.id.item_text_1,false);
           // helper.setText(R.id.item_text_1, Html.fromHtml("进货价：<font color=\"#FF0000\">¥</font>" + item.getAgentPrice() + "&nbsp;&nbsp;&nbsp;&nbsp;<br/>总进价：<font color=\"#FF0000\">¥</font>" + (new DecimalFormat("##0.00").format(item.getQty() * item.getOriPrice()))));
            helper.setText(R.id.item_text_2, Html.fromHtml("零售价：<font color=\"#FF0000\">¥</font>" + item.getRetailPrice()));
            helper.setText(R.id.item_text_3, Html.fromHtml("库存：" + item.getQty() + "件"));
            helper.setText(R.id.item_title, item.getCode());
            ImageView item_cover = helper.getView(R.id.item_cover);
            String url = ImageUrlExtends.getImageUrl(item.getCover(), 10);
            GlideUtls.glidePic(context, url, item_cover);
        }
    }
}
