package com.little.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.little.data.bean.User;

import java.sql.SQLException;

/**
 * Created by tusm on 15/9/6.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "sqlite-test.db";

    //单例模式的DatabaseHelper
    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 2);
    }

    public static DatabaseHelper getInstance(Context context){
        if( databaseHelper == null ) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }


    //每张表对应一个dao
    private Dao<User,Integer> userDao;

    //在这里拿dao (临时方案)
    public Dao<User,Integer> getUserDao() throws SQLException{
        if( userDao == null ) {
            userDao = getDao(User.class);
        }
        return userDao;
    }







    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {

            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {

            TableUtils.dropTable(connectionSource, User.class, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void close() {
        super.close();

    }
}
