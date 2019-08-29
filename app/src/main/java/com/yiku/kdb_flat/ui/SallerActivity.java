package com.yiku.kdb_flat.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nahuo.library.controls.LoadingDialog;
import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.model.bean.ProdectBean;
import com.yiku.kdb_flat.ui.adapter.SallerAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.SpManager;

import java.util.List;

public class SallerActivity extends BaseAppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private SallerAdapter sallerAdapter;
    public static int  RequestCode_01=100;
    public static String Extra_Bean="Extra_Bean";
    public static String Extra_List="Extra_List";
    private LoadingDialog mloadingDialog;
    private SallerActivity Vthis=this;
    private List<ProdectBean.SellerUsersBean> sList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saller);
        BWApplication.addActivity(this);
        findViewById(R.id.tvTLeft).setOnClickListener(this);
        ((TextView)findViewById(R.id.tvTitleCenter)).setText("选择销售员");
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sallerAdapter=new SallerAdapter(this);
        recyclerView.setAdapter(sallerAdapter);
        sallerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    ProdectBean.SellerUsersBean sellerUsersBean= (ProdectBean.SellerUsersBean) adapter.getData().get(position);
                    if (sellerUsersBean!=null){
                        SpManager.setSellerUsersId(Vthis, sellerUsersBean.getID());
                        SpManager.setSellerName(Vthis, sellerUsersBean.getName());
                        Intent intent=new Intent(SallerActivity.this, MainActivity.class);
                        intent.putExtra(Extra_Bean,sellerUsersBean);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        initData();
    }
    private void  initData(){
        if (getIntent()!=null) {
            sList = (List<ProdectBean.SellerUsersBean>) getIntent().getSerializableExtra(Vthis.Extra_List);
            int id= SpManager.getSellerUsersId(Vthis);
            for (ProdectBean.SellerUsersBean bean:sList) {
                if (id==bean.getID()){
                    bean.isCheck=true;
                }else {
                    bean.isCheck=false;
                }
            }
            sallerAdapter.setNewData(sList);
            sallerAdapter.notifyDataSetChanged();
        }
    }
    public class  Task   extends AsyncTask<String,Integer,Object>{

        @Override
        protected Object doInBackground(String... params) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mloadingDialog=new LoadingDialog(Vthis);
            mloadingDialog.start("加载数据....");
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (mloadingDialog.isShowing()) {
                mloadingDialog.stop();
            }
            if (result instanceof String && ((String) result).startsWith("error:")) {
                ViewHub.showLongToast(Vthis, ((String) result).replace("error:", ""));
            }
        }
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
