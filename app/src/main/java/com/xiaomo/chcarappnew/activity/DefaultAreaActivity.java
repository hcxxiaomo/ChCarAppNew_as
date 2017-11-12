package com.xiaomo.chcarappnew.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.db.manager.AddressDictManager;
import com.smarttop.library.utils.LogUtil;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.xiaomo.chcarappnew.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by smartTop on 2016/12/6.
 */

public class DefaultAreaActivity extends Activity implements View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {
    private TextView tv_selector_area;
    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private LinearLayout content;
    private AddressDictManager addressDictManager;

    private EditText et_address_desc;
    private Button bt_save_address;

    private ListView mListView; //首页的ListView
    private List<String> namesList; //用于装载数据的集合
    private int selectPosition = -1;//用于记录用户选择的变量
    private String selectBrand;

    private SharedPreferences sp;
    private  MyAdapter myAdapter;
    private String default_address;
    private  Set<String> setStr ;

    @SuppressLint("ResourceAsColor")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_area);
        tv_selector_area = (TextView) findViewById(R.id.tv_selector_area);
        content = (LinearLayout) findViewById(R.id.content);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        et_address_desc = (EditText) findViewById(R.id.et_address_desc);
        bt_save_address = (Button) findViewById(R.id.bt_save_address);

        mListView = (ListView)findViewById(R.id.lv_default_area);

        et_address_desc.clearFocus();

        tv_selector_area.setOnClickListener(this);
        bt_save_address.setOnClickListener(this);
        AddressSelector selector = new AddressSelector(this);
        //获取地址管理数据库
        addressDictManager = selector.getAddressDictManager();

        selector.setTextSize(14);//设置字体的大小
//        selector.setIndicatorBackgroundColor("#00ff00");
        selector.setIndicatorBackgroundColor((android.R.color.holo_orange_light));//设置指示器的颜色
//        selector.setBackgroundColor(android.R.color.holo_red_light);//设置字体的背景

        selector.setTextSelectedColor((android.R.color.holo_orange_light));//设置字体获得焦点的颜色

        selector.setTextUnSelectedColor((android.R.color.holo_blue_light));//设置字体没有获得焦点的颜色

//        //获取数据库管理
//        AddressDictManager addressDictManager = selector.getAddressDictManager();
//        AdressBean.ChangeRecordsBean changeRecordsBean = new AdressBean.ChangeRecordsBean();
//        changeRecordsBean.parentId = 0;
//        changeRecordsBean.name = "测试省";
//        changeRecordsBean.id = 35;
//        addressDictManager.inserddress(changeRecordsBean);//对数据库里增加一个数据
        selector.setOnAddressSelectedListener(new OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(Province province, City city, County county, Street street) {

            }
        });
        View view = selector.getView();
        content.addView(view);

        namesList = new ArrayList<String>();
        sp = getSharedPreferences("default_address",MODE_PRIVATE);
        setStr = new LinkedHashSet<>();
        setStr.add("广东深圳市福田区默认地址");
        setStr = sp.getStringSet("address",setStr);
        default_address = sp.getString("default_address","广东深圳市福田区默认地址");
        for (String addr: setStr ) {
            namesList.add(addr);
        }
        //int pos = 0;
        for(int i = 0 ,length = namesList.size(); i < length ; i ++ ){
            //Log.e("-xiaomo-","for start="+default_address + "<-->"+(namesList.get(i)));
            if (default_address.equals(namesList.get(i))){
                //Log.e("-xiaomo-","equals+i="+selectPosition);
                selectPosition = i;
               // Log.e("-xiaomo-","i="+selectPosition);
            }

        }
        //Log.e("-xiaomo-","for end");
        myAdapter = new MyAdapter(this,namesList);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中的参数
                selectPosition = position;
                myAdapter.notifyDataSetChanged();
                selectBrand = namesList.get(position);
                Toast.makeText(DefaultAreaActivity.this,"您已选择["+selectBrand+"]作为默认地址",Toast.LENGTH_SHORT).show();
                sp.edit().putString("default_address",tv_selector_area.getText().toString() + et_address_desc.getText()).apply();
            }
        });
      /*  mListView.post(new Runnable() {
            @Override
            public void run() {
        for(int i = 0 ,length = namesList.size(); i < length ; i ++ ){
            if (default_address.equals(namesList.get(i))){
                //pos = i;
                Log.e("-xiaomo-","i="+i);
                RadioButton lastCheckedOption = (RadioButton) mListView.getChildAt(i).findViewById(R.id.id_select);
                lastCheckedOption.setSelected(true);
                mListView.setItemChecked(i,true);
                break;
            }

        }  }
        });*/

    }


    @SuppressLint("ResourceAsColor")
	@Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_selector_area :
                if (dialog != null) {
                    dialog.show();
                } else {
                    dialog = new BottomDialog(this);
                    dialog.setOnAddressSelectedListener(this);
                    dialog.setDialogDismisListener(this);
                    dialog.setTextSize(14);//设置字体的大小
                    dialog.setIndicatorBackgroundColor((android.R.color.holo_orange_light));//设置指示器的颜色
                    dialog.setTextSelectedColor((android.R.color.holo_orange_light));//设置字体获得焦点的颜色
                    dialog.setTextUnSelectedColor((android.R.color.holo_blue_light));//设置字体没有获得焦点的颜色
                    dialog.show();
                }
                break;
            case R.id.bt_save_address:
                if (TextUtils.equals(tv_selector_area.getText(),"请单击此处选择地区")){
                    Toast.makeText(this,"请先选择地区",Toast.LENGTH_SHORT).show();
                    return;
                }
                namesList.add(tv_selector_area.getText().toString() + et_address_desc.getText());
                myAdapter.notifyDataSetChanged();
                setStr.add(tv_selector_area.getText().toString() + et_address_desc.getText());
                sp.edit().putStringSet("address",setStr).apply();

        }
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
       /* LogUtil.d("数据", "省份id=" + provinceCode);
        LogUtil.d("数据", "城市id=" + cityCode);
        LogUtil.d("数据", "乡镇id=" + countyCode);
        LogUtil.d("数据", "街道id=" + streetCode);*/
        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        tv_selector_area.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
//        getSelectedArea();
    }

    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 根据code 来显示选择过的地区
     */
    private void getSelectedArea(){
        String province = addressDictManager.getProvince(provinceCode);
        String city = addressDictManager.getCity(cityCode);
        String county = addressDictManager.getCounty(countyCode);
        String street = addressDictManager.getStreet(streetCode);
       /* LogUtil.d("数据", "省份=" + province);
        LogUtil.d("数据", "城市=" + city);
        LogUtil.d("数据", "乡镇=" + county);
        LogUtil.d("数据", "街道=" + street);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class MyAdapter extends BaseAdapter {
        Context context;
        List<String> brandsList;
        LayoutInflater mInflater;
        public MyAdapter(Context context,List<String> mList){
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.e("-xiaomo-","selectPosition="+selectPosition);
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.default_area_item,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView)convertView.findViewById(R.id.id_name);
                viewHolder.select = (RadioButton)convertView.findViewById(R.id.id_select);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.name.setText(brandsList.get(position));
            if(selectPosition == position){
                viewHolder.select.setChecked(true);
            }
            else{
                viewHolder.select.setChecked(false);
            }
            return convertView;
        }
    }
    public class ViewHolder{
        TextView name;
        RadioButton select;
    }

}
