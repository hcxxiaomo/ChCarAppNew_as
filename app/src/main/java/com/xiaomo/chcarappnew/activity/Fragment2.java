package com.xiaomo.chcarappnew.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.szOCR.general.CGlobal;
import com.xiaomo.chcarappnew.R;
import com.xiaomo.chcarappnew.adapt.CarIllegalHistoryResultInfoAdapter;
import com.xiaomo.chcarappnew.popup.PopupWindowCarIllegalResultInfo;
import com.xiaomo.chcarappnew.popup.PopupWindowSelectCarIllegalCheck;
import com.xiaomo.chcarappnew.popup.PopupWindowSelectCarNumberCheck;
import com.xiaomo.db.dao.CarIllegalInfoDao;
import com.xiaomo.db.model.CarIllegalInfo;
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

public class Fragment2 extends Fragment {
    private static final String TAG = "Activity";

    private LineChartView lineChart;
    private MyDbHelper myDbHelper;
    private CarIllegalInfoDao carIllegalInfoDao;
    private ListView static_listview;
    private List<CarIllegalInfo> nCarBean = new ArrayList<CarIllegalInfo>();
    private CarIllegalHistoryResultInfoAdapter nChAdaper;

    //分页相关数据
    private int currentPage = 1;//当前页
    private int pageSize = 15;//每页数据量
    private int lastItem = 0 ;//保存最后一项
    private int allCount = 0 ;//总数据量
    private int totalPage = 0 ;//总页数
    //增加一个分页加载时候对应的ProcessBar
    private LinearLayout linearLayout;//进度条组件的布局容器
    private ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    private ProgressBar progressBar ;//进度条组件

    private String type = null;
    private String upload = null;
    private String started_date = null;
    private String end_date = null;
    private String car_number = null;

    private Button check_car_type;

    //    String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};//X轴的标注
//    int[] score= {50,42,90,33,10,74,22,18,79,20};//图表的数据点
//    int[] score_new= {30,40,90,30,10,70,20,10,70,20};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    //private List<PointValue> mPointValues_new = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    private LinkedList<PiePojo> listPiePojo ;

    private Activity mActivity;
    private Button button_fragment1;
    private View newsLayout;

    public Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        newsLayout =  inflater.inflate(R.layout.fragment_2, container, false);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 10010:
                        Bundle bd = msg.getData();
                        /*
                         data.putString("car_number",et_static_condition_carnumber.getText().toString());
                data.putString("upload",sn_static_condition_upload.getSelectedItem().toString());
                data.putString("started_date",bt_static_condition_starttime.getText().toString());
                data.putString("end_date",bt_static_condition_endtime.getText().toString());
                         */
                        car_number = bd.getString("car_number");
                        upload = bd.getString("upload");
                        started_date = bd.getString("started_date");
                        end_date = bd.getString("end_date");
                        type = bd.getString("type");
                        Log.e("-xiaomo-",car_number+upload+started_date+end_date+type);
                        //得到查询条件之后再进行查询
                        getDataFromSelect();
                        break;
                }
            }
        };

        lineChart = (LineChartView) newsLayout.findViewById(R.id.chart);
        static_listview = (ListView) newsLayout.findViewById(R.id.static_listview);

        myDbHelper = new MyDbHelper(mActivity, "db_car_number", 1);
        carIllegalInfoDao = new CarIllegalInfoDao(myDbHelper.getReadableDatabase());//得到dao

        //初始化一个有processBar的LinearLayout
        linearLayout = new LinearLayout(mActivity);
        progressBar = new ProgressBar(mActivity);
        linearLayout.addView(progressBar, params);
        linearLayout.setGravity(Gravity.CENTER);

        check_car_type = (Button) newsLayout.findViewById(R.id.check_car_type);
        check_car_type.setOnClickListener(new Fragment2.myOnClickListener());

        listPiePojo = carIllegalInfoDao.getPieInfo();

        //如果是来自上一个页面的话，需要得到传输过来的数据
       /* Intent intent = getIntent();
        type = intent.getStringExtra("type");
        car_number = intent.getStringExtra("car_number");
        upload = intent.getStringExtra("upload");
        started_date = intent.getStringExtra("started_date");
        end_date = intent.getStringExtra("end_date");*/
        getDataFromSelect();


        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
        return newsLayout;
    }

    private void getDataFromSelect() {
        //list数据查询功能
        nCarBean = carIllegalInfoDao.findCarNumberRaidInfo(
                type,
                car_number,
                upload,
                started_date,
                end_date,
                new PageBean(currentPage, pageSize));
        allCount = carIllegalInfoDao.getCount(
                type,
                car_number,
                upload,
                started_date,
                end_date
        );
        nChAdaper = new CarIllegalHistoryResultInfoAdapter(nCarBean, mActivity);
        static_listview.setAdapter(nChAdaper);
        static_listview.setVisibility(View.VISIBLE);
        static_listview.setOnItemClickListener(new MyOnItemClickListener());
        if (allCount > pageSize) {
            static_listview.addFooterView(linearLayout);//要在listView.setAdapter(adapter);之前添加数据信息
            static_listview.setOnScrollListener(new MyOnScrollListener());
        }

        totalPage = (allCount-1) / pageSize +1;
    }


    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables(){
        if (listPiePojo == null) {
            return;
        }
        for (int i = 0; i < listPiePojo.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(listPiePojo.get(i).date));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        if (listPiePojo == null) {
            return;
        }
        for (int i = 0; i < listPiePojo.size(); i++) {
            mPointValues.add(new PointValue(i, listPiePojo.get(i).totalSum));
            //mPointValues_new.add(new PointValue(i, listPiePojo.get(i).blackSum));
        }

    }

    private void initLineChart(){
        if (listPiePojo == null) {
            return;
        }
        Line line = new Line(mPointValues).setColor(Color.parseColor("#69a8de"));  //折线的颜色（橙色）
        //Line line_new = new Line(mPointValues_new).setColor(Color.parseColor("#e9c107"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);

        //line_new.setShape(ValueShape.CIRCLE).setCubic(false).setFilled(false).setHasLabels(true).setHasLines(true).setHasPoints(true);
        //lines.add(line_new);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis().setHasLines(true);  //Y轴
        axisY.setName("所有数据");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.BLACK);  //设置字体颜色
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 2;
        v.right= 9;
        lineChart.setCurrentViewport(v);
    }

    private void appendData(){
        carIllegalInfoDao = new CarIllegalInfoDao(myDbHelper.getReadableDatabase());//得到dao
        List<CarIllegalInfo> listCarInfos = carIllegalInfoDao.findCarNumberRaidInfo(
                type,
                car_number,
                upload,
                started_date,
                end_date,
                new PageBean(currentPage, pageSize));

        this.nCarBean.addAll(listCarInfos);
        nChAdaper.notifyDataSetChanged();//通知数据改变
    }


    public class myOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (R.id.check_car_type == v.getId()){
                //弹出一个弹窗，选择查询条件，然后传回来
                PopupWindowSelectCarIllegalCheck pscc = new PopupWindowSelectCarIllegalCheck(Fragment2.this.mActivity,Fragment2.this);
                pscc.showAtLocation(Fragment2.this.mActivity.findViewById(R.id.check_car_type), Gravity.TOP | Gravity.START, 0, 0);
            }
        }
    }


    public class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            //TODO 需要增加跳转到对应的id中的数据
//				globalStr.setCarString(nCarBean.get(position).toString());

            CGlobal.chriId = nCarBean.get(position)._id;

            //要替换成 illegal 对应的 PopupWindow
            PopupWindowCarIllegalResultInfo bpw = new PopupWindowCarIllegalResultInfo(Fragment2.this.mActivity);
            bpw.showAtLocation(Fragment2.this.mActivity.findViewById(R.id.check_car_type), Gravity.TOP | Gravity.START, 0, 0);
           /* Intent intent = new Intent(StaticActivity.this,CarCheckResultActivity.class);
            startActivity(intent);*/

        }

    }

    public class MyOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (lastItem == nChAdaper.getCount() //划到当前listView的最底端
                    && currentPage < totalPage //当前页小于总页数 ，不是最后一页
                    && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE //已经停止划动，不再划动
                    ) {
                currentPage ++;
                Fragment2.this.appendData();//内部类调用外部类的方法
            }else if (currentPage == totalPage) {//如果是最后一页了，就不需要再显示这个加载信息了
                static_listview.removeFooterView(linearLayout);
            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            lastItem = firstVisibleItem + visibleItemCount -1 ;//多增加了一个ProgressBar
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myDbHelper != null) {
            myDbHelper.close();
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
