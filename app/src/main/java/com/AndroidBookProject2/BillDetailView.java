package com.AndroidBookProject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class BillDetailView extends Activity{
	private Spinner payMoneyWaySpinner;//声明Spinner类型变量
	private Spinner sendTimeSpinner;//声明Spinner类型变量
	private TextView payMoneyTextView;//声明TextView类型变量
	private EditText personAddr;//声明EditText类型变量
	private Button submitBill;//声明Button类型变量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewbilldetail);//加载布局资源文件viewbilldetail.xml
		
		//付款方式的Spinner
		payMoneyWaySpinner=(Spinner) this.findViewById(R.id.payMoneyWay);//获取资源文件中的Spinner组件
		//创建ArrayAdapter适配器对象，数据来源于数组pay_MoneyWayArr
		ArrayAdapter<CharSequence> payMoneyadapter = ArrayAdapter.createFromResource(
				this, R.array.pay_MoneyWayArr, android.R.layout.simple_spinner_item);
		payMoneyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		payMoneyWaySpinner.setAdapter(payMoneyadapter);//为payMoneyWaySpinner添加适配器
		
		//送货时间的Spinner
		sendTimeSpinner=(Spinner) this.findViewById(R.id.sendTime);//获取资源文件中的Spinner组件
		//创建ArrayAdapter适配器对象，数据来源于数组pay_MoneyWayArr
		ArrayAdapter<CharSequence> sendTimeadapter = ArrayAdapter.createFromResource(
				this, R.array.send_TimeArr, android.R.layout.simple_spinner_item);
		sendTimeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sendTimeSpinner.setAdapter(sendTimeadapter);//为sendTimeSpinner添加适配器

		//获取应付金额TextView
		payMoneyTextView=(TextView) this.findViewById(R.id.payMoney);//获取资源文件中的TextView组件
		payMoneyTextView.setText(CartListView.selectGoodsPrice+"元");//设置payMoneyTextView组件显示文字
		
		//获取地址信息
		personAddr=(EditText) this.findViewById(R.id.personAddr);//获取资源文件中的EditText组件
		
		//提交订单
		submitBill=(Button) this.findViewById(R.id.submitBill);//获取资源文件中的Button组件
		submitBill.setOnClickListener(new OnClickListener(){//监听Button的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//gids保存订单编号字符串，订单编号字符串格式为编号1，编号2
				//gnums保存购买次数字符串，购买次数字符串格式为次数1，次数2
				//TODO @javebean

				String gids="",gnums="";
				String db_ids = "";
				Map<String,String> goodsList2 = CartListView.selectGoods;
				for(Map.Entry<String,String> g :goodsList2.entrySet()){
					gids=gids+g.getKey()+"，";
					gnums = gnums+g.getValue()+",";
				}

				List<String> db_id = CartListView.selectGoodsId;
				for(String s : db_id){
					db_ids = db_ids+s+",";
				}

			/*	List<Goods> goodsList = new ConnectWeb().getCartByUsername(DataShare.user.getUid());
				for(int i=0;i<goodsList.size();i++){
					gids=gids+goodsList.get(i).getGid()+",";
					gnums=gnums+goodsList.get(i).getBuyCount() + ",";
				}*/
				if(gids.length()>0){
					gids=gids.substring(0, gids.length()-1);
				}
				if(gnums.length()>0){
					gnums=gnums.substring(0, gnums.length()-1);
				}

				if(db_ids.length()>0){
					db_ids=db_ids.substring(0, db_ids.length()-1);
				}
				Log.i("要删除的数据库购物车id",db_ids);
				//添加订单
				new ConnectWeb().addBill(db_ids,DataShare.user.getUid(), gids, gnums, ShopUtils.changeToUnicode(sendTimeSpinner.getSelectedItem().toString()), ShopUtils.changeToUnicode(payMoneyWaySpinner.getSelectedItem().toString()),ShopUtils.changeToUnicode(personAddr.getText().toString()) );
				Toast.makeText(BillDetailView.this, "订单提交完毕", Toast.LENGTH_LONG).show();
			}

		});
	}
	// 菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mnudt = menu.add(0, 0, 0, "订单中心");
		mnudt.setIcon(R.drawable.bill);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单响应事件
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {		
		case 0:
			Intent it1 = new Intent();
			it1.setClass(BillDetailView.this, BillListView.class);
			startActivity(it1);
			break;
		}
		return true;
	}
	

}
