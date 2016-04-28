package com.AndroidBookProject2;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class ViewMain extends TabActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TabHost tabHost = getTabHost();//获取TabHost对象
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("推荐商品", getResources().getDrawable(R.drawable.popular))
				.setContent(new Intent(this, ViewTuiJian.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("彩妆专区", getResources().getDrawable(R.drawable.home))
				.setContent(new Intent(this, ViewJiaDian.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("护肤专区", getResources().getDrawable(R.drawable.mobile))
				.setContent(new Intent(this, ViewShouJi.class)));
		tabHost.addTab(tabHost.newTabSpec("tab4")
				.setIndicator("清洁专区", getResources().getDrawable(R.drawable.computer))
				.setContent(new Intent(this, ViewDianNao.class)));

		/*tabHost.addTab(tabHost.newTabSpec("tab5")
				.setIndicator("购物车", getResources().getDrawable(R.drawable.computer))
				.setContent(new Intent(this, CartListView.class)));
*/

		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,0,0,"我的购物车");
		menu.add(0,1,0,"我的订单");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()){
			case 0:
				intent = new Intent();
				intent.setClass(ViewMain.this, CartListView.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent();
				intent.setClass(ViewMain.this, BillListView.class);
				startActivity(intent);
				break;

		}


		return super.onOptionsItemSelected(item);
	}
}
