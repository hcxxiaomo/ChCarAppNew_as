package com.xiaomo.chcarappnew.activity;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.xiaomo.chcarappnew.R;
import com.xiaomo.chcarappnew.adapt.CarHistoryResultInfoAdapter;
import com.xiaomo.db.dao.CarNumberInfoDao;
import com.xiaomo.db.model.CarHistoryResultInfo;
import com.xiaomo.db.model.CarNumberInfo;
import com.xiaomo.db.model.PiePojo;
import com.xiaomo.util.MyDbHelper;
import com.xiaomo.util.PageBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class IllegalActionActivity extends Activity  implements  View.OnClickListener{

	private RadioButton rb_illegal_action_select_1;
	private RadioButton rb_illegal_action_select_2;
	private RadioButton rb_illegal_action_select_3;
	private RadioButton rb_illegal_action_select_4;
	private RadioButton rb_illegal_action_select_5;
	private RadioButton rb_illegal_action_select_6;
	private RadioButton rb_illegal_action_select_7;

    private RadioButton[] radioButtonIds ;

    private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_illegal_action);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        rb_illegal_action_select_1 = (RadioButton) findViewById(R.id.rb_illegal_action_select_1);
        rb_illegal_action_select_2 = (RadioButton) findViewById(R.id.rb_illegal_action_select_2);
        rb_illegal_action_select_3 = (RadioButton) findViewById(R.id.rb_illegal_action_select_3);
        rb_illegal_action_select_4 = (RadioButton) findViewById(R.id.rb_illegal_action_select_4);
        rb_illegal_action_select_5 = (RadioButton) findViewById(R.id.rb_illegal_action_select_5);
        rb_illegal_action_select_6 = (RadioButton) findViewById(R.id.rb_illegal_action_select_6);
        rb_illegal_action_select_7 = (RadioButton) findViewById(R.id.rb_illegal_action_select_7);

        rb_illegal_action_select_1.setOnClickListener(this);
        rb_illegal_action_select_2.setOnClickListener(this);
        rb_illegal_action_select_3.setOnClickListener(this);
        rb_illegal_action_select_4.setOnClickListener(this);
        rb_illegal_action_select_5.setOnClickListener(this);
        rb_illegal_action_select_6.setOnClickListener(this);
        rb_illegal_action_select_7.setOnClickListener(this);

        radioButtonIds = new RadioButton[]{rb_illegal_action_select_1,rb_illegal_action_select_2,rb_illegal_action_select_3
                ,rb_illegal_action_select_4,rb_illegal_action_select_5,rb_illegal_action_select_6,rb_illegal_action_select_7};


        sp = getSharedPreferences("illegal_action",MODE_PRIVATE);
        radioButtonIds[ sp.getInt("select",0)].setChecked(true);
        /*for (int i ,length = radioButtonIds.length ; i < length ;i ++){

        }*/
	}


    @Override
    public void onClick(View v) {

         for (int i = 0 ,length = radioButtonIds.length ; i < length ;i ++){
            if(radioButtonIds[i].equals(v)){
                ((RadioButton)v).setChecked(true);
                sp.edit().putInt("select",i).apply();
            }else {
                radioButtonIds[i].setChecked(false);
            }
        }

      /*  for (RadioButton rb  : radioButtonIds){
            rb.setChecked(false);
        }*/

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
