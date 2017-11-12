package com.xiaomo.chcarappnew.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView mTextView;
    private ImageView mImageView;
    private  DrawerLayout drawer;

    private MyGridView gridview;

    private Intent intent = null;
    private SharedPreferences preference = null;

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

        Menu menu = navigationView.getMenu();
        preference = this.getSharedPreferences("NavigationView",MODE_PRIVATE);
        menu.findItem(R.id.action_delete_img).setChecked( preference.getBoolean("auto_delete_image",true));
        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        mImageView.setOnClickListener(this);

        //ButterKnife.bind(this);
        gridview= (MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridViewAdapt(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 3) {
                    intent = new Intent(MainActivity.this, StaticActivity.class);
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



    }

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
}
