package com.smarttop.blacklist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smarttop.blacklist.bean.BlackList;
import com.smarttop.library.db.AssetsDatabaseManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Stefan on 17/11/3.
 */

public class BlackListManager {

    private static final String TAG = "BlackListManager";
    private SQLiteDatabase db;

    public BlackListManager(Context context) {
        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(context);
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("blacklist.db");
    }

    /**
     * 按车牌号码查询所有黑名单记录（之后需要按照车牌类型和黑名单类型来进行区分）
     * @param hphm
     * @return
     */
    public List<BlackList> getBlackList(String hphm){
        List<BlackList> blackListList = new ArrayList<BlackList>();
        Cursor cursor = db.rawQuery("select _id ,hphm ,hpzl ,type from blacklist  where  hphm = ? ", new String[]{hphm});
        BlackList blackList = null;
        while (cursor.moveToNext()){
            blackList = new BlackList();
            blackList.hphm = cursor.getString(1);
            blackList.hpzl = cursor.getString(2);
            blackList.type = cursor.getString(3);
            blackListList.add(blackList);
        }
        return  blackListList;
    }
 }
