package com.gic9;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class MemoDaoImpl {
	
	private String tag ="MemoDaoImpl";
	
	private Dao<Memo, String> memoDao;
	
	private Context context;
	
	private ConnectionSource connectionSource;
	
	public MemoDaoImpl(Context context){
		this.context = context;
		connectionSource =DBManager.getinstance(this.context).getConnectionSource();
		try {
			memoDao =DaoManager.createDao(connectionSource, Memo.class);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "Error in MemoDaoImpl");
			e.printStackTrace();
		}
	}
	
	public void createMemoTable(){
		DBManager.getinstance(context).createTable(Memo.class);
	}
	
	public List<Memo> getMemoList(){
		try {
			return memoDao.queryForAll();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "Error in createMemoTable");
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertMemo(Memo memo){
		try {
			memoDao.create(memo);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "Error in insertMemo");
			e.printStackTrace();
		}
	}
	
	public void removememo(Memo memo){
		try {
			memoDao.delete(memo);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "Error in deleteMemo");
			e.printStackTrace();
		}
	}

}
