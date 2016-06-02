package braga.com.br.pomodorius.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import braga.com.br.pomodorius.model.IModel;

/**
 * Created by yann.braga on 24/05/2016.
 */
public abstract class GenericDao<T extends IModel> implements IDatabase<T> {
    private String tableName;
    private PomodoroHelper dbHelper;
    private SQLiteDatabase db;

    public GenericDao(String tableName, Context context) {
        this.tableName = tableName;
        dbHelper = new PomodoroHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @Override
    public void insert(T obj) {
        ContentValues contentValues = getContentValues(obj);
        db.insert(tableName, null, contentValues);
    }

    @Override
    public void update(T obj) {
        ContentValues contentValues = getContentValues(obj);
        db.update(tableName, contentValues, "_id = ?", new String[]{String.valueOf(obj.getId())});
    }

    @Override
    public void delete(T obj) {
        db.delete(tableName, "_id = ?", new String[]{String.valueOf(obj.getId())});
    }

    @Override
    public T find(Integer id) {

        T result = null;
        Cursor cursor = db.query(tableName, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            result = createObjectFromCursor(cursor);
        }
        return result;
    }

    @Override
    public List<T> findAll() {

        List<T> list = null;
        Cursor cursor = db.query(tableName, null, null, null, null, null, "_id ASC");

        if(cursor != null && cursor.getCount() > 0){
            list = new ArrayList<>();
            cursor.moveToFirst();

            do {
                T obj = createObjectFromCursor(cursor);
                list.add(obj);
            } while(cursor.moveToNext());
        }
        return list;
    }
    public abstract ContentValues getContentValues(T obj);

    protected abstract T createObjectFromCursor(Cursor cursor);
}
