package com.yiku.kdb_flat.ui.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.FunctionHelper;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.model.ExchanegGoodBean;
import com.yiku.kdb_flat.utils.GlideUtls;
import com.yiku.kdb_flat.utils.ListUtils;

import java.util.List;

/**
 * Created by jame on 2018/12/29.
 */

public class ExchangeGoodsAdpater extends BaseQuickAdapter<ExchanegGoodBean.ItemListBean, BaseViewHolder>implements View.OnTouchListener, View.OnFocusChangeListener  {
    private Activity context;
    private OkChangQty okChangQty;

    public void setOkChangQty(OkChangQty okChangQty) {
        this.okChangQty = okChangQty;
    }

    public ExchangeGoodsAdpater(Activity context) {
        super(R.layout.item_exchang_goods, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ExchanegGoodBean.ItemListBean item) {
        if (item != null) {
            int position = helper.getAdapterPosition();
            helper.setIsRecyclable(false);
            helper.setText(R.id.tv_01, item.getOrderCode() + "\n" + item.getDisplayMobile() + "\n" + item.getCreateTimes());
            final ImageView imageView = helper.getView(R.id.iv_02);
            GlideUtls.glidePic(context, ImageUrlExtends.getImageUrl(item.getCover(), 14), imageView);
            helper.setText(R.id.tv_03, item.getCode());
            helper.setText(R.id.tv_04, item.getColor());
            helper.setText(R.id.tv_05, item.getSize());
            helper.setText(R.id.tv_06, item.getQty()+"");
            helper.setText(R.id.tv_07, FunctionHelper.DoubleToString(item.getDiscountPrice()));
            EditText editView = helper.getView(R.id.et_08);
            editView.setText(item.getExchangQty()+"");
            editView.setOnTouchListener(this);
            editView.setOnFocusChangeListener(this);
            editView.setTag(position);
            editView.setSelection(editView.length());
            if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) { // 保证每个时刻只有一个EditText能获取到焦点
                editView.requestFocus();
            } else {
                editView.clearFocus();
            }
            TextView tv_ok = helper.getView(R.id.tv_09);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (okChangQty!=null){
                        okChangQty.OnOkQty(item);
                    }
                }
            });
        }
    }
    public void reMoveItem(ExchanegGoodBean.ItemListBean item){
        List<ExchanegGoodBean.ItemListBean> data = getData();
        if (!ListUtils.isEmpty(data)) {
            for (int i=0;i<data.size();i++) {
                ExchanegGoodBean.ItemListBean bean=data.get(i);
                if (bean.getUid().equals(item.getUid())){
                    data.remove(i);
                }
            }
        }
        notifyDataSetChanged();
    }
    int selectedEditTextPosition=-1;
    private class MyTextWatcher implements TextWatcher {
        EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (selectedEditTextPosition != -1) {
                String text = s.toString();
                List<ExchanegGoodBean.ItemListBean> data = getData();
                if (!ListUtils.isEmpty(data)) {
                    ExchanegGoodBean.ItemListBean bean = data.get(selectedEditTextPosition);
                    if (TextUtils.isEmpty(text)) {
                        bean.setExchangQty(0);
                    } else {
                        if (Integer.parseInt(text) > bean.getQty()) {
                            bean.setExchangQty(bean.getQty());
                            this.editText.setText(String.valueOf(bean.getQty()));
                            this.editText.setSelection(String.valueOf(bean.getQty()).toString().length());
                            ViewHub.showShortToast(mContext, "可退货数" + bean.getQty() + "件");
                        } else {
                            bean.setExchangQty(Integer.parseInt(text));
                        }
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface OkChangQty {
        void OnOkQty(ExchanegGoodBean.ItemListBean item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText editText = (EditText) v;
            selectedEditTextPosition = (int) editText.getTag();
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (hasFocus) {
            editText.addTextChangedListener(new MyTextWatcher(editText));
        } else {
            editText.removeTextChangedListener(new MyTextWatcher(editText));
        }
    }
}
