package com.xiaomo.chcarappnew.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carOCR.activity.ScanActivity;
import com.carOCR.activity.ScanIllegalActivity;
import com.xiaomo.chcarappnew.R;
import com.xiaomo.chcarappnew.adapt.MyGridViewAdapt;
import com.xiaomo.chcarappnew.view.MyGridView;
import com.xiaomo.util.PermissionUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView mTextView;
    private TextView tvNickName;
    private TextView tvRealName;
    private ImageView mImageView;
    private  DrawerLayout drawer;

    private MyGridView gridview;

    private Intent intent = null;
    private SharedPreferences preference = null;

    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        sp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);

        Menu menu = navigationView.getMenu();
        preference = this.getSharedPreferences("NavigationView",MODE_PRIVATE);
        menu.findItem(R.id.action_delete_img).setChecked( preference.getBoolean("auto_delete_image",true));
        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        tvNickName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNickName);
        tvRealName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvRealName);
        mImageView.setOnClickListener(this);

        tvNickName.setText(sp.getString("name", "警官")+" "+sp.getString("jobTitle", "职称"));//姓名   职称  您好！
        tvRealName.setText(sp.getString("unit", "单位")+" "+sp.getString("depart", "部门"));

        //ButterKnife.bind(this);
        gridview= (MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridViewAdapt(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 3) {
                    intent = new Intent(MainActivity.this, StaticTabActivity.class);
                    startActivity(intent);
                }else if(position == 0){
                    intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                } else if(position == 1){
                    intent = new Intent(MainActivity.this, ScanIllegalActivity.class);
                    startActivity(intent);
                }

            }
        });

        PermissionUtils.requestMultiPermissions(this,mPermissionGrant);

    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(MainActivity.this, "获取声音权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(MainActivity.this, "获取用户权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(MainActivity.this, "读取手机状态权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(MainActivity.this, "获取打电话权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(MainActivity.this, "获取打开相机权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(MainActivity.this, "获取准确定位权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(MainActivity.this, "获取普通定位权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "获取读存储卡权限", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "获取写存储卡权限", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nothing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_advanced_set) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    //设置NavigationView菜单条目的点击监听
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //String string = null;
        Intent intent = null;
        switch (id){
            case R.id.action_delete_img:

                if(item.isChecked()){
                    item.setChecked(false);
                    preference.edit().putBoolean("auto_delete_image",false).apply();
                }else {
                    item.setChecked(true);
                    preference.edit().putBoolean("auto_delete_image",true).apply();
                }


                break;
            case R.id.action_network_set:
                intent = new Intent(MainActivity.this,NetworkSetActivity.class);
                startActivity(intent);
                break;
            case R.id.action_illegal_action:
                intent = new Intent(MainActivity.this,IllegalActionActivity.class);
                startActivity(intent);
                break;
            case R.id.action_commo_address:
                intent = new Intent(MainActivity.this,DefaultAreaActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sycn_blacklist:
                //TODO 后面需要增加联网同步的功能
                Toast.makeText(MainActivity.this,"黑名单已经同步",Toast.LENGTH_SHORT).show();
                break;
        }
        //if (!TextUtils.isEmpty(string))
           //mTextView.setText("你点击了"+string);
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ivAvatar){
           //Intent intent = new Intent(this,LoginActivity.class);
            //startActivity(intent);
        }
    }

    /*@OnClick(R.id.snackbar)
    void goToSnackbar(){
        startActivity(new Intent(this,SnackBarActivity.class));
    }*/

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

    }

}
