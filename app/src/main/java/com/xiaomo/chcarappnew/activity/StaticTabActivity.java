package com.xiaomo.chcarappnew.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomo.chcarappnew.R;


public class StaticTabActivity  extends AppCompatActivity implements View.OnClickListener{


	private LinearLayout tab1Layout, tab2Layout;
	private TextView text1, text2;
	// 默认选中第一个tab
	private int index = 1;
	// fragment管理类
	private FragmentManager fragmentManager;
	// 三个fragment
	private Fragment tab1Fragment, tab2Fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_static_tab);
		fragmentManager = getSupportFragmentManager();
		init();

	}

	/**
	 * 初始化控件
	 */
	private void init() {
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);

		tab1Layout = (LinearLayout) findViewById(R.id.tab1_layout);
		tab2Layout = (LinearLayout) findViewById(R.id.tab2_layout);

		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		//
		setDefaultFragment();
	}

	/**
	 * 设置默认显示的fragment
	 */
	private void setDefaultFragment() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		tab1Fragment = new Fragment1();
		transaction.replace(R.id.content_layout, tab1Fragment);
		transaction.commit();
	}

	/**
	 *切换fragment
	 * @param newFragment
	 */
	private void replaceFragment(Fragment newFragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (!newFragment.isAdded()) {
			transaction.replace(R.id.content_layout, newFragment);
			transaction.commit();
		} else {
			transaction.show(newFragment);
		}
	}

	/**
	 * 改变现象卡的选中状态
	 */
	private void clearStatus() {
       /* if (index == 1) {
            tab1Layout.setBackgroundColor(Color.BLUE);
        } else if (index == 2) {
            tab2Layout.setBackgroundColor(Color.RED);
        }*/
	}

	@Override
	public void onClick(View view) {
		clearStatus();
		switch (view.getId()) {
			case R.id.text1:
				if (tab1Fragment == null) {
					tab1Fragment = new Fragment1();
				}
				replaceFragment(tab1Fragment);
				//tab1Layout.setBackgroundColor(Color.BLUE);
				index = 1;
				break;
			case R.id.text2:
				if (tab2Fragment == null) {
					tab2Fragment = new Fragment2();
				}
				replaceFragment(tab2Fragment);
				//tab2Layout.setBackgroundColor(Color.RED);
				index = 2;
				break;
		}

	}
	
	
	
}
