package com.gic9;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBManager {
	private Context context;
	private String tag="DBManager";
	private static DBManager instance;
	
	public  DBManager(Context context){
		this.context =context;
		
	}

	public static DBManager getinstance(Context context){
		
		if(instance==null){
			instance =new DBManager(context);
			
		}
		
		return instance;
	}
	
	public ConnectionSource getConnectionSource(){
		
		SQLiteDatabase sqliteDatabase =null;
		try {
			sqliteDatabase =context.openOrCreateDatabase("my_database.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
			
		} catch (Exception e) {
			Log.e(tag, "Error in ConnectionSource");
		}
		
		return new AndroidConnectionSource(sqliteDatabase);
	}
	
	public void createTable(Class<?> clazz){
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(), clazz);
		} catch (Exception e) {
			Log.e(tag, "Error in createTable");
			e.printStackTrace();
			
		}
	}
	
}
