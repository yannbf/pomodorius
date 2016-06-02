package braga.com.br.pomodorius.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import braga.com.br.pomodorius.model.Task;


/**
 * Created by yann.braga on 24/05/2016.
 */
public class TaskDao extends GenericDao<Task> {
    private static final String TABLE_NAME = "tasks";

    public TaskDao(Context ctx){
        super(TABLE_NAME,ctx);
    }


    @Override
    public ContentValues getContentValues(Task obj) {
        ContentValues values = new ContentValues();

        if(obj.getId() !=null){
            values.put("_id", obj.getId());
        }

        values.put("title", obj.getTitle());
        values.put("description", obj.getDescription());
        values.put("pomodoro",obj.getPomodoro());
        values.put("short_break",obj.getShortBreak());
        values.put("long_break",obj.getLongBreak());

        return values;
    }

    @Override
    protected Task createObjectFromCursor(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        int pomodoros = cursor.getInt(cursor.getColumnIndex("pomodoro"));
        int shortBreak = cursor.getInt(cursor.getColumnIndex("short_break"));
        int longBreak = cursor.getInt(cursor.getColumnIndex("long_break"));

        Task task = new Task(id, title, description, pomodoros, shortBreak, longBreak);

        return task;
    }
}
