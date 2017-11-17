package com.xiaomo.chcarappnew.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.szOCR.general.CGlobal;
import com.xiaomo.chcarappnew.R;
import com.xiaomo.db.dao.CarIllegalInfoDao;
import com.xiaomo.db.model.CarIllegalInfo;
import com.xiaomo.util.MyDbHelper;

public class PopupWindowCarIllegalResultInfo extends PopupWindow {

	private View contentView;
	private String telStr="";
	private Long serverCarId;

	private Button ok;
	//private Button show_car_owner_info_btn;
	private Button show_car_need_upload_btn_illegal;
	//private TextView show_car_owner_info_txt_illegal;

	private TextView history_result_time_illegal;
	private TextView history_result_car_number_illegal;
	//private TextView history_result_color_illegal;
	//private TextView history_result_maker_illegal;
	//private TextView history_result_type_illegal;
	//private TextView history_result_vin_illegal;
	//private TextView history_result_engine_illegal;
	private TextView history_result_str_illegal;
	//private TextView history_result_carType_illegal;
	private TextView show_car_had_upload_txt_illegal;
	private TextView show_car_record_has_illegal;

	private MyDbHelper myDbHelper;
	private CarIllegalInfoDao carIllegalInfoDao;

	public PopupWindowCarIllegalResultInfo(final Activity context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.page_car_illegal_result_info, null);
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
		//show_car_owner_info_txt_illegal =  (TextView) contentView.findViewById(R.id.show_car_owner_info_txt_illegal);
		history_result_time_illegal =  (TextView) contentView.findViewById(R.id.history_result_time_illegal);
		history_result_car_number_illegal =  (TextView) contentView.findViewById(R.id.history_result_car_number_illegal);
		//history_result_color_illegal =  (TextView) contentView.findViewById(R.id.history_result_color_illegal);
		//history_result_maker_illegal =  (TextView) contentView.findViewById(R.id.history_result_maker_illegal);
		//history_result_type_illegal =  (TextView) contentView.findViewById(R.id.history_result_type_illegal);
		//history_result_vin_illegal =  (TextView) contentView.findViewById(R.id.history_result_vin_illegal);
		//history_result_engine_illegal =  (TextView) contentView.findViewById(R.id.history_result_engine_illegal);
		history_result_str_illegal =  (TextView) contentView.findViewById(R.id.history_result_str_illegal);
		//history_result_carType_illegal =  (TextView) contentView.findViewById(R.id.history_result_carType_illegal);
		show_car_had_upload_txt_illegal =  (TextView) contentView.findViewById(R.id.show_car_had_upload_txt_illegal);
		//show_car_record_has_illegal =  (TextView) contentView.findViewById(R.id.show_car_record_has_illegal);

		ok = (Button) contentView.findViewById(R.id.compare_button_ok);
		//show_car_owner_info_btn_illegal = (Button) contentView.findViewById(R.id.show_car_owner_info_btn_illegal);
		show_car_need_upload_btn_illegal = (Button) contentView.findViewById(R.id.show_car_need_upload_btn_illegal);

		myDbHelper = new MyDbHelper(context, "db_car_number", 1);
		carIllegalInfoDao = new CarIllegalInfoDao(myDbHelper.getReadableDatabase());//得到dao

//	        final GlobalVaries globalStr = (GlobalVaries) context.getApplication();
//	        Log.i("-xiaomo-", globalStr.getCarString());
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupWindowCarIllegalResultInfo.this.dismiss();
			}
		});

		/*show_car_owner_info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show_car_owner_info_txt.setVisibility(View.VISIBLE);
			}
		});*/

		//从数据库中查询到数据
		final CarIllegalInfo ci =  carIllegalInfoDao.query(CGlobal.chriId);
		//将数据库查询到的信息解析成数据信息
		history_result_time_illegal.setText("识别时间："+ ci.createTime);
//			show_car_owner_info_txt.setText(response.toString());
		history_result_car_number_illegal.setText(ci.carNumber);
		//history_result_color.setText(ci.getCarColor());
		//history_result_maker.setText(ci.getMaker());
		//history_result_type.setText(ci.getType());
		//history_result_vin.setText(ci.getVin());
		//history_result_engine.setText(ci.getEngineNo());
		//history_result_carType.setText(ci.getCarType());
		serverCarId = ci.serverCarId;

		history_result_str_illegal.setTextColor(Color.RED);

		history_result_str_illegal.append(ci.illegalId.concat("-").concat(ci.illegalInfo));

		if (ci.isReported == 1) {
			show_car_had_upload_txt_illegal.setVisibility(View.VISIBLE);
			show_car_need_upload_btn_illegal.setVisibility(View.GONE);
		}else{
			show_car_need_upload_btn_illegal.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {//上报服务器为isRepored
					/*RequestParams params = new RequestParams();
					try {
						File file = new File(ci.img1);
						params.put("image", file);
					} catch (FileNotFoundException e) {
						Toast.makeText(context, "图片不存在，操作停止！", Toast.LENGTH_LONG).show();
						Log.e("-xiaomo-", "FileNotFoundException e",e);
						return ;
					}
					params.put("serverCarId", String.valueOf(serverCarId));
					RestClient.post("carplatenumber/carNumber/uploadCarIllegalImage", params, new JsonHttpResponseHandler(){


						public void onSuccess(int statusCode, Header[] headers,
											  JSONObject response) {
							Log.i("-xiaomo-", "uploadImage-success:"+response);
							if (response.optInt("upload") == 1) {//上传成功-- 数据库状态修改
								carIllegalInfoDao.updateIsReported(serverCarId);
								Toast.makeText(context, "图片上传平台存储成功！", Toast.LENGTH_LONG).show();
								show_car_need_upload_btn_illegal.setClickable(false);
							}
						}


						@Override
						public void onFailure(int statusCode, Header[] headers,
											  String responseString, Throwable throwable) {
							super.onFailure(statusCode, headers, responseString, throwable);
						}

					});*/
				}});
		}

		//图片缩略图
		ImageView image1=(ImageView)contentView.findViewById(R.id.car_info_pic_image_id_illegal_1);
		image1.setImageBitmap(BitmapFactory.decodeFile(ci.img1));
		image1.setOnClickListener(new OnClickListener() { // 点击放大
			public void onClick(View paramView) {
				LayoutInflater inflater = LayoutInflater.from(context);
				View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
				final AlertDialog dialog = new AlertDialog.Builder(context).create();
				ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
				img.setImageBitmap(BitmapFactory.decodeFile(ci.img1));
				//	        imageDownloader.download("图片地址"),img); // 这个是加载网络图片的，可以是自己的图片设置方法
				dialog.setView(imgEntryView); // 自定义dialog
				dialog.show();
				// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
				imgEntryView.setOnClickListener(new OnClickListener() {
					public void onClick(View paramView) {
						dialog.cancel();
					}
				});
			}
		});
	ImageView image2=(ImageView)contentView.findViewById(R.id.car_info_pic_image_id_illegal_1);
		image2.setImageBitmap(BitmapFactory.decodeFile(ci.img2));
		image2.setOnClickListener(new OnClickListener() { // 点击放大
			public void onClick(View paramView) {
				LayoutInflater inflater = LayoutInflater.from(context);
				View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
				final AlertDialog dialog = new AlertDialog.Builder(context).create();
				ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
				img.setImageBitmap(BitmapFactory.decodeFile(ci.img2));
				//	        imageDownloader.download("图片地址"),img); // 这个是加载网络图片的，可以是自己的图片设置方法
				dialog.setView(imgEntryView); // 自定义dialog
				dialog.show();
				// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
				imgEntryView.setOnClickListener(new OnClickListener() {
					public void onClick(View paramView) {
						dialog.cancel();
					}
				});
			}
		});


	}



	@Override
	public void dismiss() {
		super.dismiss();
		if (myDbHelper != null) {
			myDbHelper.close();
			Log.i("-xiaomo-", "PopupWindowCarCheckResultInfo--myDbHelper.close();");
		}
	}



	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAsDropDown(parent, parent.getLayoutParams().width , 0);
		} else {
			this.dismiss();
		}
	}
}
