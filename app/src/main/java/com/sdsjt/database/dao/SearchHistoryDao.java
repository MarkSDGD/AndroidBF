package com.sdsjt.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sdsjt.database.DatabaseHelper;
import com.sdsjt.database.table.SearchHistoryTable;
import com.sdsjt.model.response.SearchHistoryBean;
import com.sdsjt.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;



public class SearchHistoryDao {

    private SQLiteDatabase db;

    public SearchHistoryDao() {
        this.db = DatabaseHelper.getDatabase();
    }

    public boolean add(String keyWord) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEYWORD, keyWord);
        values.put(SearchHistoryTable.TIME, TimeUtils.getCurrentTimeStamp());
        long result = db.insert(SearchHistoryTable.TABLENAME, null, values);
        return result != -1;
    }

    public List<SearchHistoryBean> queryAll() {
        Cursor cursor = db.query(SearchHistoryTable.TABLENAME, null, null, null, null, null, SearchHistoryTable.TIME + " desc");
        List<SearchHistoryBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setKeyWord(cursor.getString(SearchHistoryTable.ID_KEYWORD));
            bean.setTime(cursor.getString(SearchHistoryTable.ID_TIME));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public boolean queryisExist(String keyWord) {
        Cursor cursor = db.query(SearchHistoryTable.TABLENAME, null, SearchHistoryTable.KEYWORD + "=?", new String[]{keyWord}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean delete(String keyWord) {
        int id = db.delete(SearchHistoryTable.TABLENAME, SearchHistoryTable.KEYWORD + "=?", new String[]{keyWord});
        return id != -1;
    }

    public boolean update(String keyWord) {
        ContentValues values = new ContentValues();
        values.put(SearchHistoryTable.KEYWORD, keyWord);
        values.put(SearchHistoryTable.TIME, TimeUtils.getCurrentTimeStamp());
        int result = db.update(SearchHistoryTable.TABLENAME, values, SearchHistoryTable.KEYWORD + "=?", new String[]{keyWord});
        return result != -1;
    }

    public boolean deleteAll() {
        int id = db.delete(SearchHistoryTable.TABLENAME, null, null);
        return id != -1;
    }
}
