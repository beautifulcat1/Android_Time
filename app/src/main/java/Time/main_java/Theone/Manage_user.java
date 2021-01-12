package Time.main_java.Theone;

import android.database.Cursor;

import Time.main_java.sqlite_db.Db_Query_Db;

public class Manage_user {
    private Db_Query_Db db_query_db =new Db_Query_Db();
    private User_bean user_bean=new User_bean();
    public void Insert_To_Db(String state,String args[]){
//                +"Email text,"
//                +"Password text,"
//                +"Nackname text,"
//                +"Shuoming text,"
//                +"Phone text,"
//                +"ImagNum integer,"
//                +"ImagAddr text"
        db_query_db.insert_db("insert into user_list(Email,Password,Nackname,Shuoming,Phone,ImagNum,ImagAddr)values(?,?,?,?,?,?,?)",args);
        db_query_db.insert_db(state,args);
    }
    public User_bean Query_From_Db(){
        Cursor cursor= db_query_db.query_db("select * from user_list",null);
        if(cursor.moveToFirst()) {
            do {
                user_bean.Set_Email(cursor.getString(cursor.getColumnIndex("Email")));
                user_bean.Set_Password(cursor.getString(cursor.getColumnIndex("Password")));
                user_bean.Set_Nackname(cursor.getString(cursor.getColumnIndex("Nackname")));
                user_bean.SetShuoming(cursor.getString(cursor.getColumnIndex("Shuoming")));
                user_bean.SetPhone(cursor.getString(cursor.getColumnIndex("Phone")));
                user_bean.Set_ImaNum(cursor.getString(cursor.getColumnIndex("ImagNum")));
                user_bean.Set_ImaAddr(cursor.getString(cursor.getColumnIndex("ImagAddr")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return user_bean;
    }
    public void Update_to_Db(String s1,String values[]){
        db_query_db.update_db(s1,values);
    }
}
