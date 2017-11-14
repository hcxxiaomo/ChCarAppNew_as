package com.xiaomo.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.xiaomo.db.model.CarIllegalInfo;
import com.xiaomo.util.PageBean;

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
		String sql = "insert into t_illegal_info values ( null, ?, ? , ?, ? ,?, ? , ?, ? ,? ,?)";
		Object[] args = new Object[]{
				illegalInfo.carNumber
				,illegalInfo.type
				,illegalInfo.illegalId
				,illegalInfo.illegalInfo
				,illegalInfo.address
				,illegalInfo.img1
				,illegalInfo.img2
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
	
	public void findCarNumberRaidInfo (String legalType,String carNumber,String isReported,String startedDate,String endDate,PageBean pageBean){
		
	}
	
	public void deleteByCarNumber(){
		
	}
}
