package com.AndroidBookProject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewLogin extends Activity {
	private EditText username;//声明EditText类型变量
	private EditText password;//声明EditText类型变量
	private Button loginBut;//声明Button类型变量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewlogin);//加载布局资源文件viewlogin.xml

		username=(EditText) this.findViewById(R.id.username);//加载布局资源文件中的EditText组件
		password=(EditText) this.findViewById(R.id.password);//加载布局资源文件中的EditText组件
		loginBut=(Button) this.findViewById(R.id.loginBut);//加载布局资源文件中的Button组件

		loginBut.setOnClickListener(new OnClickListener(){//Button组件的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//验证用户名和密码是否正确
				User theuser=new ConnectWeb().userLogin(username.getText().toString(),password.getText().toString());
				if(theuser!=null){
					DataShare.user.setId(theuser.getId());//设置用户id
					DataShare.user.setUid(theuser.getUid());//设置用户昵称
					DataShare.user.setUserPwd(theuser.getUserPwd());//设置用户密码

					Toast.makeText(ViewLogin.this, "登录成功", Toast.LENGTH_LONG).show();
					//登录成功后进入购物车
					Intent intent = new Intent();
					intent.setClass(ViewLogin.this, ViewMain.class);
					startActivity(intent);
				}else{
					Toast.makeText(ViewLogin.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
				}
			}

		});
	}

}
