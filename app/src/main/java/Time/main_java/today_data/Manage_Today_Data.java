package Time.main_java.today_data;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Time.main_java.sqlite_db.Db_Query_Db;

public class Manage_Today_Data {
    Db_Query_Db query_db=new Db_Query_Db();
    String b[]=new String[6];

    public List<Today_Data> mToday_Date=new ArrayList<>();

    public void Add_To_Db(Today_Data t){
        long st,et;
        st=t.get_star_data().getTimeInMillis();
        et=t.get_end_data().getTimeInMillis();
        b[0]=String.valueOf(st);
        b[1]=String.valueOf(et);
        b[2]=t.getWord();
        b[3]=t.getEmail();
        b[4]=String.valueOf(t.getAlarm_type());
        b[5]=String.valueOf(t.get_Finish_Type());
        query_db.insert_db("insert into today_date(Start_Date,End_Date,Word,Email,Alarm_Type,Finish_Type)values(?,?,?,?,?,?)",b);
    }
    public List<Today_Data> Get_From_Db(){
        Cursor cursor= query_db.query_db("select * from today_date order by Start_Date Asc,End_Date Asc  ",null);
        if(cursor.moveToFirst()){
            do{
                Today_Data today_data=new Today_Data();
                Calendar c1=Calendar.getInstance();
                Calendar c2 =Calendar.getInstance();
                c1.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("Start_Date")));
                c2.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("End_Date")));
                 today_data.set_word(cursor.getString(cursor.getColumnIndex("Word")));
                 today_data.set_Email(cursor.getString(cursor.getColumnIndex("Email")));
                 today_data.setAlarm_type(cursor.getInt(cursor.getColumnIndex("Alarm_Type")));
                 today_data.set_Finish_Type(cursor.getInt(cursor.getColumnIndex("Finish_Type")));
                 today_data.set_start_data(c1);
                 today_data.set_end_data(c2);
                 mToday_Date.add(today_data);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
//
//        System.out.println("sda"+formatter.format(today_data.get_star_data().getTime()));

            }while (cursor.moveToNext());
        }
        cursor.close();
        query_db.Close_Db();
           return mToday_Date;

    }
        public void Delet_From_Db(Calendar s ,Calendar e){
        String b[]=new String[2];
        b[0]=String.valueOf(s.getTimeInMillis());
        b[1]=String.valueOf(e.getTimeInMillis());
        query_db.delet_db("delete from today_date where Start_Date=? and End_Date=? ",b);
    }
    public void Update_TO_Db(Calendar s,int Finish_Type,int update_type){
        if(update_type==0){
            String b[]=new String[2];
            b[0]=String.valueOf(Finish_Type);
            b[1]=String.valueOf(s.getTimeInMillis());
            query_db.update_db("update today_date set Finish_Type=? where Start_Date=?",b);
        }
        if(update_type==1){
            String b[]=new String[3];
            b[0]=String.valueOf(s.getTimeInMillis()+600000);
            b[1]=String.valueOf(Finish_Type);
            b[2]=String.valueOf(s.getTimeInMillis());
            query_db.update_db("update today_date set Start_Date=? , Finish_Type=?  where Start_Date=?",b);
        }

    }
}
