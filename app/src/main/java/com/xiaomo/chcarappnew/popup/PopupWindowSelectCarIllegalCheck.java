package com.xiaomo.chcarappnew.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;

import com.xiaomo.chcarappnew.R;
import com.xiaomo.chcarappnew.activity.Fragment1;
import com.xiaomo.chcarappnew.activity.Fragment2;
import com.xiaomo.chcarappnew.adapt.SpinnerAdapter;

import java.util.Calendar;

public class PopupWindowSelectCarIllegalCheck extends PopupWindow {

	//private Spinner sn_static_condition_action;
	private Spinner sn_static_condition_upload;
	private EditText et_static_condition_carnumber;
	private Button bt_static_condition_starttime;
	private Button bt_static_condition_endtime;
	private Button bt_static_condition_upload;
	private Button bt_static_condition_action;

	private int year;
	private int month;
	private int day;

	private  Button bt_choose_condition;

	private View contentView;

    private Activity mActivity;

	public PopupWindowSelectCarIllegalCheck(final Activity context, final Fragment2 fragment2){
        mActivity = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.activity_static_carillegal_condition, null);
		//int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(contentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w);//WindowManager.LayoutParams.MATCH_PARENT WindowManager.LayoutParams.WRAP_CONTENT
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		//this.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置popWindow的显示和消失动画
		this.setAnimationStyle(R.style.mypopwindow_anim_style);
        //sn_static_condition_action = (Spinner) contentView.findViewById(R.id.sn_static_condition_action_illegal);
        sn_static_condition_upload = (Spinner) contentView.findViewById(R.id.sn_static_condition_upload_illegal);
        et_static_condition_carnumber = (EditText) contentView.findViewById(R.id.et_static_condition_carnumber_illegal);
        bt_static_condition_starttime = (Button) contentView.findViewById(R.id.bt_static_condition_starttime_illegal);
        bt_static_condition_endtime = (Button) contentView.findViewById(R.id.bt_static_condition_endtime_illegal);
        bt_static_condition_action = (Button) contentView.findViewById(R.id.bt_static_condition_action_illegal);

        bt_choose_condition = (Button) contentView.findViewById(R.id.bt_choose_condition_illegal);

      /*  ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer); //修改actionbar左上角返回按钮的图标

       /* final String[] illeagl_item = {"高速公路逆向行驶_4602","占用应急车道行驶_4608","违反禁令标志提示_1344"
                ,"不按规定车道行驶_1355","违反禁止标线指示_1230","不按规定倒车_4601","违反警告标志指示_1090"};*/
        final String[] illeagl_item = {"违法停车_110"};
        final String[] illeagl_id = {"110"};
        final String[] illeagl_upload_item = {"全部"};
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance();
        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天


        /*SpinnerAdapter adapter = new SpinnerAdapter(mActivity,
                android.R.layout.simple_spinner_dropdown_item, illeagl_item);
        sn_static_condition_action.setAdapter(adapter);*/
        SpinnerAdapter adapter_upload = new SpinnerAdapter(mActivity,
                android.R.layout.simple_spinner_dropdown_item, illeagl_upload_item);
        sn_static_condition_upload.setAdapter(adapter_upload);

        bt_static_condition_starttime.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //创建DatePickerDialog对象
                DatePickerDialog dpd=new DatePickerDialog(mActivity,startedDatelistener,year,month,day);
                dpd.show();//显示DatePickerDialog组件
            }
        });
        bt_static_condition_endtime.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //创建DatePickerDialog对象
                DatePickerDialog dpd=new DatePickerDialog(mActivity,endDatelistener,year,month,day);
                dpd.show();//显示DatePickerDialog组件
            }
        });


        bt_static_condition_action.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("请选择查询的违法类型")
                        .setItems(illeagl_item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bt_static_condition_action.setText(illeagl_id[which]);
                                //illeagl_spinner_id.setTextColor(Color.BLUE);
                            }
                        })
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        });

        bt_choose_condition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Message msg = new Message();
                Bundle data = new Bundle();
                //data.putString("keysl", "xiaomo--MyPopupWindows");
                data.putString("car_number",et_static_condition_carnumber.getText().toString());
                data.putString("upload",sn_static_condition_upload.getSelectedItem().toString());
                data.putString("started_date",bt_static_condition_starttime.getText().toString());
                data.putString("end_date",bt_static_condition_endtime.getText().toString());
                data.putString("type",bt_static_condition_action.getText().toString());
                msg.setData(data);
                msg.what = 10010;
                fragment2.mHandler.sendMessage(msg);
                PopupWindowSelectCarIllegalCheck.this.dismiss();
                /*
                type = intent.getStringExtra("type");
   		car_number = intent.getStringExtra("car_number");
   		upload = intent.getStringExtra("upload");
   		started_date = intent.getStringExtra("started_date");
   		end_date = intent.getStringExtra("end_date");
                 */
               /* Intent intent = new Intent(mActivity,StaticActivity.class);
                //intent.putExtra("type",)
                intent.putExtra("car_number",et_static_condition_carnumber.getText().toString());
                intent.putExtra("upload",sn_static_condition_upload.getSelectedItem().toString());
                intent.putExtra("started_date",bt_static_condition_starttime.getText().toString());
                intent.putExtra("end_date",bt_static_condition_endtime.getText().toString());
                startActivity(intent);
                StaticConditionActivity.this.finish();*/


            }
        });


	}

    private DatePickerDialog.OnDateSetListener startedDatelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(year).append("-");
            if ((month+1) < 10) {
                sb.append("0").append((month+1)).append("-");
            }else{
                sb.append((month+1)).append("-");
            }
            if(day < 10){
                sb.append("0").append(day);
            }else{
                sb.append(day);
            }
            Log.i("-xiaomo-", sb.toString());
            bt_static_condition_starttime.setText(sb.toString());
            bt_static_condition_starttime.setTextColor(Color.BLUE);
        }
    };
    private DatePickerDialog.OnDateSetListener endDatelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(year).append("-");
            if ((month+1) < 10) {
                sb.append("0").append((month+1)).append("-");
            }else{
                sb.append((month+1)).append("-");
            }
            if(day < 10){
                sb.append("0").append(day);
            }else{
                sb.append(day);
            }
            Log.i("-xiaomo-", sb.toString());
            bt_static_condition_endtime.setText(sb.toString());
            bt_static_condition_endtime.setTextColor(Color.BLUE);
        }
    };



	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAsDropDown(parent, parent.getLayoutParams().width , 0);
		} else {
			this.dismiss();
		}
	}
}
