package com.ywq.tarena.groupon.util;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.ywq.tarena.groupon.bean.CitynameBean;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by tarena on 2017/6/22.
 */

public class DBUtil {

    DBHelper dbHelper;
    Dao<CitynameBean, String> dao;

    public DBUtil(Context context){
        dbHelper = new DBHelper(context);
        try {
            dao = dbHelper.getDao(CitynameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(CitynameBean citynameBean){
        try {
            dao.createIfNotExists(citynameBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(List<CitynameBean> list){
        for (CitynameBean bean:list){
            insert(bean);
        }
    }

    public void insertBatch(final List<CitynameBean> list) {
        //建立连接后，一次性将数据全部写入后，再断开连接
        try {
            dao.callBatchTasks(new Callable<Object>() {


                @Override
                public Object call() throws Exception {

                    for (CitynameBean bean : list) {
                        insert(bean);
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CitynameBean> query(){
        try {
            List<CitynameBean> citynameBeenList = dao.queryForAll();
            return citynameBeenList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库时出现异常");
        }
    }
}
