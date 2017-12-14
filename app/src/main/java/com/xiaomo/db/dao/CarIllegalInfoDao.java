package com.xiaomo.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiaomo.db.model.CarIllegalInfo;
import com.xiaomo.db.model.PiePojo;
import com.xiaomo.util.PageBean;

import java.util.LinkedList;
import java.util.List;

public class CarIllegalInfoDao {

	/*

	    + "  _id integer  primary key,
+ "  car_number varchar(20) ,"
+ "  type varchar(50) ,"
+ "  illegal_id varchar(50) ,"
+ "  illegal_info varchar(50)
+ "  address varchar(500)   ,"
+ "  img1 varchar(50)  ,"
+ "   img2 varchar(50)  ,"
+ "   is_reported smallint  ,"
+ "  server_carid integer ,"
+ "  create_time varchar(50) "
	*/
	private SQLiteDatabase db = null;

	public CarIllegalInfoDao(SQLiteDatabase db) {
		super();
		this.db = db;
	}
	

	public void insert(CarIllegalInfo illegalInfo){
		String sql = "insert into t_illegal_info values ( null, ?, ? , ?, ? ,?, ? , ? , ?, ? ,? ,?)";
		Object[] args = new Object[]{
				illegalInfo.carNumber
				,illegalInfo.type
				,illegalInfo.illegalId
				,illegalInfo.illegalInfo
				,illegalInfo.address
				,illegalInfo.img1
				,illegalInfo.img2
				,illegalInfo.img3
                ,illegalInfo.isReported
                ,illegalInfo.serverCarId
                ,illegalInfo.createTime
        };
		db.execSQL(sql,args);
	}
	
	/*public void insertReplace(CarNumberRaidInfo staticInfo){
		String sql = "REPLACE INTO t_carnumber_raid (carNumber, illegalString, imgPath, isReported, createdTime) values ( ?, ? , ?, ? ,?)";
		Object[] args = new Object[]{
				staticInfo.carNumber
				,staticInfo.illegalString
				,staticInfo.imgPath
				,staticInfo.isReported
				,staticInfo.createdTime
		};
		db.execSQL(sql,args);
	}*/


	/*
				"CREATE TABLE t_illegal_info ("
					 + "  _id integer  primary key,"
					 + "  car_number varchar(20) ,"
					 + "  type varchar(50) ,"
					 + "  illegal_id varchar(50) ,"
					 + "  illegal_info varchar(50)  ,"
					 + "  address varchar(500)   ,"
					 + "  img1 varchar(50)  ,"
					 + "   img2 varchar(50)  ,"
					 + "   is_reported smallint  ,"
					 + "  server_carid integer ,"
					 + "  create_time varchar(50) "
					 + ") ";
	 */
	public List<CarIllegalInfo> findCarNumberRaidInfo (String legalType,String carNumber,String isReported,String startedDate,String endDate,PageBean pageBean){

		List<CarIllegalInfo> listCarIllegalInfo = new LinkedList<CarIllegalInfo>();
		StringBuilder sb = new StringBuilder();
		sb.append("select _id,car_number,type,illegal_id,illegal_info,address,img1,img2,img3,is_reported,")
				.append("server_carid,create_time  from t_illegal_info  ");
//		String[] illeagl_item = {"全部","逾期未年审","报废车","黄标车","布控车","违法未处理" };
//		String[] illeagl_upload_item = {"全部","未上报","已上报" };
		StringBuilder sbInner = new StringBuilder();
		if (legalType != null && legalType.trim().length() > 1){
            sbInner.append("and illegal_id = " + legalType);
        }

		if (carNumber != null && carNumber.trim().length() >= 1) {
            sbInner.append("and car_number like '%").append(carNumber.trim()).append("%' ");
		}

		if ("未上报".equals(isReported)) {
			sbInner.append("and is_reported = 0 ");
		}else if("已上报".equals(isReported)){
			sbInner.append("and is_reported = 1 ");
		}

		if (startedDate != null && !"开始时间".equals(startedDate)) {
			sbInner.append("and create_time >= '").append(startedDate).append("00:00:00' ");
		}
		if (endDate != null && !"结束时间".equals(endDate)) {
			sbInner.append("and create_time <= '").append(endDate).append("23:59:59' ");
		}

		if (sbInner.length() > 0) {
			sbInner.replace(0, 3, "where");
		}
		sb.append(sbInner).append("  order by _id desc limit ? , ?");
        String[] args = new String[]{
                String.valueOf(pageBean.getStart()),
                String.valueOf(pageBean.getPageSize())
        };
        Log.i("-xiaomo-",sb.toString() + " ||pageBean.getStart()="+pageBean.getStart()+"\tpageBean.getPageSize()="+pageBean.getPageSize());
        Cursor result =  db.rawQuery(sb.toString(), args);
        CarIllegalInfo carIllegalInfo = null;
        //sb.append("select _id,car_number,type,illegal_id,illegal_info,address,img1,img2,is_reported,").append("server_carid,create_time  from t_illegal_info  ");
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            carIllegalInfo = new CarIllegalInfo();
            carIllegalInfo._id = result.getLong(0);
            carIllegalInfo.carNumber = result.getString(1);
            carIllegalInfo.type = result.getString(2);
            carIllegalInfo.illegalId = result.getString(3);
            carIllegalInfo.illegalInfo = result.getString(4);
            carIllegalInfo.address = result.getString(5);
            carIllegalInfo.img1 = result.getString(6);
            carIllegalInfo.img2 = result.getString(7);
            carIllegalInfo.img3 = result.getString(8);
            carIllegalInfo.isReported = result.getInt(9);
            carIllegalInfo.serverCarId = result.getLong(10);
            carIllegalInfo.createTime = result.getString(11);
            listCarIllegalInfo.add(carIllegalInfo);
        }
        Log.i("-xiaomo-", "-----out----"+listCarIllegalInfo.size());
        return listCarIllegalInfo;
    }

    public int getCount(String legalType,String carNumber,String isReported,String startedDate,String endDate){
        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select count(1)  from t_illegal_info  ");
//		String[] illeagl_item = {"全部","逾期未年审","报废车","黄标车","布控车","违法未处理" };
//		String[] illeagl_upload_item = {"全部","未上报","已上报" };
        StringBuilder sbInner = new StringBuilder();
        if (legalType != null && legalType.trim().length() > 1){
            sbInner.append("and illegal_id = " + legalType);
        }

        if (carNumber != null && carNumber.trim().length() > 1) {
            sbInner.append("and car_number like '%").append(carNumber).append("%' ");
        }

        if ("未上报".equals(isReported)) {
            sbInner.append("and is_reported = 0 ");
        }else if("已上报".equals(isReported)){
            sbInner.append("and is_reported = 1 ");
        }

        if (startedDate != null && !"开始时间".equals(startedDate)) {
            sbInner.append("and create_time >= '").append(startedDate).append("00:00:00' ");
        }
        if (endDate != null && !"结束时间".equals(endDate)) {
            sbInner.append("and create_time <= '").append(endDate).append("23:59:59' ");
        }

        if (sbInner.length() > 0) {
            sbInner.replace(0, 3, "where");
        }
        sb.append(sbInner);
        Cursor result =  db.rawQuery(sb.toString(), null);
        result.moveToNext();
        count = result.getInt(0);
        return count;
    }

    public CarIllegalInfo query(Long _id){
        String sql = "select _id,car_number,type,illegal_id,illegal_info,address,img1,img2,img3,is_reported,server_carid,create_time  from t_illegal_info   where _id = ?";
        String[] args = new String[]{
                String.valueOf(_id)
        };
        Cursor result =  db.rawQuery(sql, args);
        CarIllegalInfo carIllegalInfo = new CarIllegalInfo();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            carIllegalInfo._id = result.getLong(0);
            carIllegalInfo.carNumber = result.getString(1);
            carIllegalInfo.type = result.getString(2);
            carIllegalInfo.illegalId = result.getString(3);
            carIllegalInfo.illegalInfo = result.getString(4);
            carIllegalInfo.address = result.getString(5);
            carIllegalInfo.img1 = result.getString(6);
            carIllegalInfo.img2 = result.getString(7);
            carIllegalInfo.img3 = result.getString(8);
            carIllegalInfo.isReported = result.getInt(9);
            carIllegalInfo.serverCarId = result.getLong(10);
            carIllegalInfo.createTime = result.getString(11);
        }
        return  carIllegalInfo;
    }

    public void updateIsReported(Long _id){
        String sql = "update  t_illegal_info  set is_reported = 1 where  server_carid = ?";
        String[] args = new String[]{
                String.valueOf(_id)
        };
//		try {
        db.execSQL(sql,args);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
    }

    public LinkedList<PiePojo> getPieInfo(){
        LinkedList<PiePojo> list = new LinkedList<PiePojo>();
        String sql = "select substr(create_time,1,10), count(1) from t_illegal_info  group by substr(create_time,1,10) order by substr(create_time,1,10) desc limit 0 ,15 ";
        Cursor result =  db.rawQuery(sql, null);
        PiePojo pie = null;
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            pie = new PiePojo();
            pie.date = result.getString(0);
            pie.totalSum = result.getInt(1);
            list.add(pie);
        }
        return list;
    }

	/*public void deleteByCarNumber(){
		
	}*/
}
