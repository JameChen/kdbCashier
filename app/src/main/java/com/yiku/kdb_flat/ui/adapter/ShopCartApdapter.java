package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.FunctionHelper;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.constant.Constant;
import com.yiku.kdb_flat.model.bean.ShopCartModel;
import com.yiku.kdb_flat.utils.GlideUtls;
import com.yiku.kdb_flat.utils.ListUtils;

/**
 * Created by jame on 2018/12/5.
 */

public class ShopCartApdapter extends BaseQuickAdapter<ShopCartModel, BaseViewHolder> {
    private Context context;
    public final static int TYPE_PLUS = 1;
    public final static int TYPE_REDUCE = 2;
    public final static int TYPE_DEL = 3;
    public final static int TYPE_SCAN = 3;
    private APDOnClick mApdOnClick;
    private boolean isSubmitOrder = false;

    public void setSubmitOrder(boolean submitOrder) {
        isSubmitOrder = submitOrder;
    }

    public void setApdOnClick(APDOnClick apdOnClick) {
        this.mApdOnClick = apdOnClick;
    }

    public ShopCartApdapter(Context context) {
        super(R.layout.item_shop_cart, null);
        this.context = context;
    }


    public void upateData(ShopCartModel bean, int type) {
        if (!ListUtils.isEmpty(getData())) {
            switch (type) {
                case ShopCartApdapter.TYPE_PLUS:
                case ShopCartApdapter.TYPE_REDUCE:
                    for (ShopCartModel model : getData()) {
                        if (model.getID() == bean.getID()) {
                            model.setQty(bean.getQty());
                            break;
                        }
                    }
                    break;
                case ShopCartApdapter.TYPE_DEL:
                    for (int i = 0; i < getData().size(); i++) {
                        ShopCartModel model = getData().get(i);
                        if (model.getID() == bean.getID()) {
                            getData().remove(i);
                            break;
                        }
                    }
                    break;
            }
            notifyDataSetChanged();
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCartModel item) {
        if (item != null) {
            ImageView iv = helper.getView(R.id.iv);
            String path = ImageUrlExtends.getImageUrl(item.getCover(), 13);
            GlideUtls.glidePic(context, path, iv);
            helper.setText(R.id.tv01, item.getCode());
            helper.setText(R.id.tv02, item.getColor());
            helper.setText(R.id.tv03, item.getSize());
            helper.setText(R.id.tv04, item.getQty() + "");
            CheckBox cb_integral=helper.getView(R.id.cb_integral);

            View iv_del = helper.getView(R.id.iv_del);
            TextView btn_minus = helper.getView(R.id.btn_minus);
            TextView btn_plus = helper.getView(R.id.btn_plus);
           // ImageView del = helper.getView(R.id.del);
            if (item.getTypeID() == Constant.SHOP_TYPEID.TypeID_Exchang_Goods) {
//                del.setImageResource(R.drawable.iv_del_no);
//                iv_del.setEnabled(false);
                btn_minus.setVisibility(View.INVISIBLE);
                btn_plus.setVisibility(View.INVISIBLE);
                helper.setGone(R.id.tv_chang,true);
//                if (item.isIsOnSale()) {
//                    helper.setText(R.id.tv05, Html.fromHtml("<font color=\"#161616\">" +
//                            FunctionHelper.DoubleFormat(-1*Double.parseDouble(item.getOrgRetailPrice())) + "</font><br/><font color=\"#FF0000\">特:" +
//                            FunctionHelper.DoubleFormat(-1*item.getPrice())+"<br/>"+ "</font>换货款")
//                    );
//                    helper.setText(R.id.tv06, Html.fromHtml("<font color=\"#161616\">" +
//                            FunctionHelper.DoubleFormat(-1*Double.parseDouble(item.getOrgRetailPrice()) * item.getQty()) + "</font><br/><font color=\"#FF0000\">特:" +
//                            FunctionHelper.DoubleFormat(-1*(item.getPrice() * item.getQty())) + "</font><br/>换货款")
//                    );
//                } else {
//                    helper.setText(R.id.tv05, FunctionHelper.DoubleFormat(item.getPrice()));
//                    helper.setText(R.id.tv06, FunctionHelper.DoubleFormat(item.getPrice() * item.getQty()));
//                }
//                helper.setText(R.id.tv05, Html.fromHtml("<font color=\"#161616\">" +
//                        FunctionHelper.DoubleFormat(-1*Double.parseDouble(item.getOrgRetailPrice())) + "</font><br/><font color=\"#FF0000\">换货款" +
//                         "</font>"));
//                helper.setText(R.id.tv06, Html.fromHtml("<font color=\"#161616\">" +
//                        FunctionHelper.DoubleFormat(-1*Double.parseDouble(item.getOrgRetailPrice()) * item.getQty()) + "</font><br/>"));
                helper.setText(R.id.tv05, FunctionHelper.DoubleFormat(-1 * item.getPrice()));
                helper.setText(R.id.tv06, FunctionHelper.DoubleFormat(-1 * (item.getPrice() * item.getQty())));
            } else {
                helper.setGone(R.id.tv_chang,false);
//                iv_del.setEnabled(true);
//                del.setImageResource(R.drawable.iv_del);
                btn_minus.setVisibility(View.VISIBLE);
                btn_plus.setVisibility(View.VISIBLE);
                if (item.isIsOnSale()) {
                    helper.setText(R.id.tv05, Html.fromHtml("<font color=\"#161616\">" +
                            FunctionHelper.DoubleFormat(Double.parseDouble(item.getOrgRetailPrice())) + "</font><br/><font color=\"#FF0000\">特:" +
                            FunctionHelper.DoubleFormat(item.getPrice()) + "</font>")
                    );
                    helper.setText(R.id.tv06, Html.fromHtml("<font color=\"#161616\">" +
                            FunctionHelper.DoubleFormat(Double.parseDouble(item.getOrgRetailPrice()) * item.getQty()) + "</font><br/><font color=\"#FF0000\">特:" +
                            FunctionHelper.DoubleFormat(item.getPrice() * item.getQty()) + "</font>")
                    );
                } else {
                    if (item.getTransPrice()>0&&item.getTransPrice()!=item.getPrice()){
                        helper.setText(R.id.tv05, FunctionHelper.DoubleFormat(item.getPrice())+"\n"
                        +"折:"+(int)item.getTransPrice());
                        helper.setText(R.id.tv06, FunctionHelper.DoubleFormat(item.getPrice() * item.getQty())+"\n"
                        +"折:"+(int)(item.getTransPrice()*item.getQty()));
                    }else {
                        helper.setText(R.id.tv05, FunctionHelper.DoubleFormat(item.getPrice()));
                        helper.setText(R.id.tv06, FunctionHelper.DoubleFormat(item.getPrice() * item.getQty()));
                    }
                }
            }
//            cb_integral.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
//            cb_integral.setText("无");
            cb_integral.setChecked(item.isCheck());
            if (!item.isHasPointChange()) {
                cb_integral.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                cb_integral.setText("无");
                cb_integral.setEnabled(false);
                cb_integral.setClickable(false);
                cb_integral.setGravity(Gravity.CENTER);
            }else {
                cb_integral.setGravity(Gravity.CENTER|Gravity.LEFT);
                cb_integral.setEnabled(true);
                cb_integral.setClickable(true);
                cb_integral.setButtonDrawable(ContextCompat.getDrawable(mContext,R.drawable.selector2_checkbox));
                cb_integral.setText(item.getPointPrice()+"元\n"+item.getPoint()+"积分");
            }
            cb_integral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setCheck(!item.isCheck());
                    if (mApdOnClick!=null)
                        mApdOnClick.OnChangePoints();
                }
            });
            iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopCartModel model = setModel(item, 3);
                    if (mApdOnClick != null)
                        mApdOnClick.OnAPDClick(TYPE_DEL, model);
                }
            });
            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopCartModel model = setModel(item, 1);
                    if (mApdOnClick != null)
                        mApdOnClick.OnAPDClick(TYPE_REDUCE, model);
                }
            });
            btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopCartModel model = setModel(item, 2);
                    if (mApdOnClick != null)
                        mApdOnClick.OnAPDClick(TYPE_PLUS, model);
                }
            });
        }
    }

    private ShopCartModel setModel(ShopCartModel item, int type) {
        ShopCartModel model = new ShopCartModel();
        model.setID(item.getID());
        int qty = item.getQty();
        if (type == 1) {
            qty = qty - 1;
            if (qty < 0)
                qty = 0;
            model.setQty(qty);

        } else if (type == 2) {
            qty = qty + 1;
            model.setQty(qty);
        } else if (type == 3) {
            model.setQty(item.getQty());
        }

        model.setItemID(item.getItemID());
        model.setColor(item.getColor());
        model.setSize(item.getSize());
        model.setName(item.getName());
        model.setCover(item.getCover());
        model.setPrice(item.getPrice());
        model.setSelect(item.isSelect());
        return model;
    }

    public interface APDOnClick {
        void OnAPDClick(int type, ShopCartModel item);
        void OnChangePoints();

    }
}
