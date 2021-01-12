package Time.main_java.sqlite_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 创建表
 */
public class Db_Create_Table_Helper extends SQLiteOpenHelper {
    public static final String CREATE_today = "create table today_date("
            +"Start_Date real,"
            +"End_Date real,"
            +"Alarm_Type integer,"
            +"Word text,"
            +"Email text,"
            +"Finish_Type integer,"
            +"primary key(start_date,end_date)"+")";
    public static final String CREATE_user = "create table user_list("
            +"Email text,"
            +"Password text,"
            +"Nackname text,"
            +"Shuoming text,"
            +"Phone text,"
            +"ImagNum integer,"
            +"ImagAddr text"
            +")";
    private Context mcontext;
    public Db_Create_Table_Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_today);
        db.execSQL(CREATE_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
