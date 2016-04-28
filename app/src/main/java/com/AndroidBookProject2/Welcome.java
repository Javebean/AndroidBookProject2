package com.AndroidBookProject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 第一个欢迎窗体 
 * */

public class Welcome extends Activity{

	/**
	 * 重写Activity中的onCreate的方法。
	 * 该方法是在Activity创建时被系统调用，是一个Activity生命周期的开始。
	 * @param savedInstanceState：保存Activity的状态的。
	 *        Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Window win = getWindow();//返回当前Activity的Window对象,Window类中概括了Android窗口的基本属性和基本功能
		//隐藏状态栏
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		this.setContentView(R.layout.welcome);//设置布局资源
		ImageView iv=(ImageView)this.findViewById(R.id.wpic);//获取welcome.xml中id为wpic的ImageView组件
		iv.setImageResource(R.drawable.welcome);//设置ImageView上显示的资源
		welcome();//欢迎界面
	}


	/**
	 * 欢迎界面,2秒钟后切换
	 * @param
	 * @return
	 */
	public void welcome() {
		new Thread(new Runnable() {//创建线程
			public void run() {//实现Runnable的run方法，即线程体
				try {
					Thread.sleep(2000);//欢迎界面暂停2秒钟
					Message m = new Message();//创建Message对象
					logHandler.sendMessage(m);//将消息放到消息队列中
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();//启动线程
	}

	//执行接收到的消息，执行的顺序是按照队列进行，即先进先出
	Handler logHandler = new Handler() {
		public void handleMessage(Message msg) {
			welcome1();//显示logo界面
		}
	};
	/**
	 * 显示logo界面
	 * @param
	 * @return
	 */
	public void welcome1() {
		Intent it=new Intent();//实例化Intent
		//it.setClass(Welcome.this, ViewLogin.class);//设置Class
		it.setClass(Welcome.this, ViewLogin.class);//设置Class
		startActivity(it);//启动Activity
		Welcome.this.finish();//结束Welcome Activity
	}

	/**
	 * 键盘按键按下是触发该方法
	 * @param keyCode：被按下的键值即键盘码 
	 *        event：按键事件的对象
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4 ){//按下“返回”按键
			android.os.Process.killProcess(android.os.Process.myPid());//让程序完全退出应用
		}
		return super.onKeyDown(keyCode, event);
	}
}
