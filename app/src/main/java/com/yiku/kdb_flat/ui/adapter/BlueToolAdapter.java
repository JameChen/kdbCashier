package com.yiku.kdb_flat.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gprinter.io.PortParameters;
import com.yiku.kdb_flat.R;

import java.util.List;

public class BlueToolAdapter extends BaseAdapter {
	public final static String DEBUG_TAG="MyAdapter";
	public static final String IMG = "img";
	public static final String TITEL = "titel";
	public static final String STATUS = "status";
	public static final String INFO = "info";
	public static final String BT_ENABLE = "btenable";
	public static final String ENABLE = "enable";
	public static final String DISABLE="disable";
	public static final int MESSAGE_CONNECT = 1;
	private Handler mHandler= null;

    public void setListItems(List<PortParameters> listItems) {
        this.listItems = listItems;
    }

    private List<PortParameters> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器
    public final class ListItemView{                //自定义控件集�?    
            public ImageView image;
            public TextView title;
            public TextView info;
            public Button button;
     }  
    ListItemView  listItemView = null;
    public BlueToolAdapter(Context context,
                           Handler handler) {
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文

        mHandler = handler; 
    }   
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
  //      ListItemView  listItemView = null;   
        if (arg1 == null) {   
            listItemView = new ListItemView();    
            arg1 = listContainer.inflate(R.layout.main_screen_list_item, null);
            listItemView.image = (ImageView)arg1.findViewById(R.id.ivOperationItem);
            listItemView.title = (TextView)arg1.findViewById(R.id.tvOperationItem);
            listItemView.info = (TextView)arg1.findViewById(R.id.tvInfo);
            listItemView.button= (Button)arg1.findViewById(R.id.btTestConnect);
           //设置控件集到convertView   
            arg1.setTag(listItemView);   
        }else {   
            listItemView = (ListItemView)arg1.getTag();   
        }  
        final int arg = arg0;   
//        listItemView.image.setBackgroundResource((Integer) listItems.get(
//                arg0).get(IMG));
        PortParameters portParameters=listItems.get(arg0);
        listItemView.title.setText(listItems.get(arg0).getUsbDeviceName());
        listItemView.info.setText("接口：蓝牙    "+"端口："+listItems.get(arg0)
                .getBluetoothAddr());
//        if (portParameters.getPortOpenState()){
//            listItemView.button.setText("断开");
//        }else {
//            listItemView.button.setText("连接");
//
//        }
        if (portParameters.getIpAddr().equals("连接中")){
            listItemView.button.setEnabled(false);
        }else {
            listItemView.button.setEnabled(true);
        }
        listItemView.button.setText(portParameters.getIpAddr());
//        String str = (String)listItems.get(arg0).get(BT_ENABLE);
//        if(str.equals(ENABLE))
//        {
//        	     listItemView.button.setEnabled(true);
//        }
//        else
//        {
//        	     listItemView.button.setEnabled(false);
//        }
        listItemView.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.d(DEBUG_TAG,"arg1 " + arg);
                Message message = new Message();
                message.what = MESSAGE_CONNECT;  
                message.arg1 = arg;  
                listItemView.button.getTag();
                mHandler.sendMessage(message);  
            }   
        });                  
       return arg1;  
   }  

}

