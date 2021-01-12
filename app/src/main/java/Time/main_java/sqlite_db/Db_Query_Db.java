package Time.main_java.sqlite_db;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import Time.main_java.MyApplication;

/**
 * Programing By Diao
 * 创建数据库
 * 数据库表的增删改查
 */

public class Db_Query_Db {
    private Db_Create_Table_Helper dbHelper;
    private  SQLiteDatabase db;
    private Cursor cursor=null;
    public Db_Query_Db(){
        dbHelper=new Db_Create_Table_Helper(MyApplication.getContext(),"Today_Store.db",null,1);
         db = dbHelper.getWritableDatabase();/**添加异常处理机制*/
    }
    public void insert_db(String sql,Object b[] ){
       if(!db.isOpen()){
           db=dbHelper.getWritableDatabase();
           db.execSQL(sql,b);
       }
       else  db.execSQL(sql,b);

    }
    public Cursor query_db(String sql,String args[]){
       cursor = db.rawQuery(sql,args);
       return cursor;
    }
    public void update_db(String sql,String args[]){
        if(!db.isOpen()){
            db=dbHelper.getWritableDatabase();
            db.execSQL(sql,args);
        }
        else db.execSQL(sql,args);
    }
    public void delet_db(String sql,String args[]){
        if(!db.isOpen()){
            db=dbHelper.getWritableDatabase();
            db.execSQL(sql,args);
        }
        else db.execSQL(sql,args);

    }
    public void Close_Db(){
        db.close();
    }
}
