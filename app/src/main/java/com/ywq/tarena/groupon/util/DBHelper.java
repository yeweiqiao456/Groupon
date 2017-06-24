package com.ywq.tarena.groupon.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ywq.tarena.groupon.bean.CitynameBean;

import java.sql.SQLException;

/**
 *
 *
 * Created by tarena on 2017/6/22.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public DBHelper(Context context) {
        super(context, "city.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        //在第一次创建city.db数据库时，该方法会被调用
        //创建存储数据的数据表
        try {
            TableUtils.createTableIfNotExists(connectionSource, CitynameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        //当数据库的结构发生变化时，该方法会被调用
        try {
            TableUtils.dropTable(connectionSource,CitynameBean.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
