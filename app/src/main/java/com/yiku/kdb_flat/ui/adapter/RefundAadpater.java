package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nahuo.library.helper.ImageUrlExtends;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.model.bean.RefundBean;
import com.yiku.kdb_flat.utils.GlideUtls;
import com.yiku.kdb_flat.utils.ListUtils;

import java.util.List;

/**
 * Created by jame on 2018/12/27.
 */

public class RefundAadpater extends BaseQuickAdapter<RefundBean.ItemListBean, BaseViewHolder> implements View.OnTouchListener, View.OnFocusChangeListener {
    private Context context;
    private EditChangQty editChangQty;

    public void setEditChangQty(EditChangQty editChangQty) {
        this.editChangQty = editChangQty;
    }

    public RefundAadpater(Context context) {
        super(R.layout.item_refund, null);
        this.context = context;
    }

    public class SubBean {
        int Id = -1;
        int pos = -1;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
    }

    private int selectedEditTextPosition = -2;
    int currentId = -2;

    @Override
    protected void convert(BaseViewHolder helper, RefundBean.ItemListBean item) {
        if (item != null) {
            helper.setIsRecyclable(false);
            int position = helper.getAdapterPosition() - 1;
            ImageView iv_icon = helper.getView(R.id.iv_icon);
            GlideUtls.glidePic(context, ImageUrlExtends.getImageUrl(item.getCover(), 14), iv_icon);
            helper.setText(R.id.tv_content, item.getTitle() + "\n款号：" + item.getCode() +"\n"+item.getColor() + "/" + item.getSize() + "/" + item.getQty() + "件"+ "\n销售数量：" +
                    item.getQty() + "*" + item.getDiscountPrice() + "\n可退数量：" + item.getCanRefundQty());
            EditText editView = helper.getView(R.id.et_qty);
            editView.setOnTouchListener(this);
            editView.setOnFocusChangeListener(this);
            SubBean subBean = new SubBean();
            subBean.setId(item.getTID());
            subBean.setPos(position);
            editView.setTag(subBean);
//            if (TextUtils.isEmpty(qty)) {
//                editView.setText("0");
//            } else {
//                editView.setText(qty);
//            }
           editView.setText(item.getRefundQty()+"");
            editView.setSelection(editView.length());

            if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) { // 保证每个时刻只有一个EditText能获取到焦点
                editView.requestFocus();
            } else {
                editView.clearFocus();
            }

        }
    }

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
                List<RefundBean.ItemListBean> data = getData();
                if (!ListUtils.isEmpty(data)) {
                    RefundBean.ItemListBean bean = data.get(selectedEditTextPosition);
                    if (TextUtils.isEmpty(text)) {
                        bean.setRefundQty(0);
                    } else {
                        if (Integer.parseInt(text) > bean.getCanRefundQty()) {
                            bean.setRefundQty(bean.getCanRefundQty());
                            this.editText.setText(String.valueOf(bean.getCanRefundQty()));
                            this.editText.setSelection(String.valueOf(bean.getCanRefundQty()).toString().length());
                            ViewHub.showShortToast(mContext, "可退货数" + bean.getCanRefundQty() + "件");
                        } else {
                            bean.setRefundQty(Integer.parseInt(text));
                        }
                    }
                }
                if (editChangQty != null)
                    editChangQty.OnEditQty();
//                   ExtendPropertyListBean bean = pareList.get(selectedEditTextPosition);
//                   bean.setValue(text);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface EditChangQty {
        void OnEditQty();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText editText = (EditText) v;
            SubBean bean = (SubBean) editText.getTag();
            if (bean != null) {
                selectedEditTextPosition = bean.getPos();
                currentId = bean.getId();
            }
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
