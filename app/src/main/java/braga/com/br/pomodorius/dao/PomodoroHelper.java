package braga.com.br.pomodorius.dao;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yann.braga on 24/05/2016.
 */
public class PomodoroHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pomodoro.db";
    private static final int DATABASE_VERSION = 3;
    private Context mContext;

    public PomodoroHelper(Context ctx){
        super(ctx,DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream is = assetManager.open("db/create_database.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = br.readLine()) != null){
                db.execSQL(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream is = assetManager.open("db/update_database.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = br.readLine()) != null){
                db.execSQL(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
