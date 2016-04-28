package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BillListView extends Activity {
	private LinearLayout myListLayout;//声明LinearLayout类型变量
	private ListView tripListView;//声明ListView类型变量
	private List<BillEntity> billList = new ArrayList<BillEntity>();//声明List类型变量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewbilllist);//加载布局资源文件
		myListLayout = (LinearLayout) this.findViewById(R.id.billList);//加载布局资源文件中的LinearLayout组件
		tripListView = new ListView(this);// 创建ListView
		//创建布局参数
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//将ListView添加到LinearLayout上
		getBillList();//获取订单列表

	}

	// 读取订单列表数据
	private void getBillList() {
		final ProgressDialog myDialog = ProgressDialog.show(BillListView.this,
				"请稍等...", "数据检索中...", true);
		new Thread() {
			public void run() {
				try {
					//从服务器端读取用户订单列表
					billList = new ConnectWeb().getBillList(DataShare.user
							.getUid());

					Log.i("从服务器端读取用户订单列表:"+DataShare.user
							.getUid(),billList.toString());
					Message m = new Message();
					listHandler.sendMessage(m);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	Handler listHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (billList.size() == 0) {
				return;
			}
			showBillList();//填充订单列表适配器
		}
	};

	// 填充订单列表适配器
	public void showBillList() {
		//声明SimpleAdapter适配器
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.billlistrow, new String[] { "billNum", "billState",
				"billTime" }, new int[] { R.id.billNum, R.id.billState,
				R.id.billTime });
		tripListView.setAdapter(adapter);//为tripListView添加适配器
	}

	public List<Map<String, Object>> getTripList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < billList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			BillEntity theBill = billList.get(i);
			String stateStr = "";
			if (theBill.getState().equals("waiting")) {
				stateStr = "处理中";
			}
			map.put("billNum", "订单编号：" + theBill.getId());
			map.put("billTime", "下单时间：" + theBill.getCtime());
			map.put("billState", "订单状态：" + stateStr);

			list.add(map);
		}
		return list;
	}

}
