package Time.main_java.Alarm;

import java.util.Calendar;

import Time.main_java.Synchronization.Up_Today_Date;
import Time.main_java.Theone.Manage_user;
import Time.main_java.Theone.User_bean;
import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;

public class test2 {
    public test2(String Email) {
        Calendar temp1, temp2;
        temp1 = Calendar.getInstance();
        temp2 = Calendar.getInstance();
        long Start_Date, End_Date;
        String Word;
        int Alarm_Type, Finish_Type;
        String[] hh;
        int jj;
        String b = "";
//        Manage_Today_Data manage_today_data = new Manage_Today_Data();
        Up_Today_Date up_today_date = new Up_Today_Date();


        if (up_today_date.connectHost()) {
            up_today_date.sendData("!!!!!" + Email);
            b = up_today_date.getData();
        } else System.out.println("不能连接服务器");
        hh = b.split("%%");
        jj = hh.length / 6;


        for (int i = 0; i < jj; i++) {
            Today_Data today_data = new Today_Data();
            int xia = (6 * (i + 1) - 6);
            System.out.println(hh[xia]);
            Start_Date = Long.valueOf(hh[xia]);
            End_Date = Long.valueOf(hh[xia + 1]);
            Word = (hh[xia + 2]);
            Alarm_Type=Integer.valueOf(hh[xia+4]);
            Finish_Type = Integer.valueOf(hh[xia + 5]);

            temp1.setTimeInMillis(Start_Date);
            temp2.setTimeInMillis(End_Date);
            today_data.set_start_data(temp1);
            today_data.set_end_data(temp2);
            today_data.setAlarm_type(Alarm_Type);
            today_data.set_word(Word);
            today_data.set_Finish_Type(Finish_Type);
            today_data.set_Email(Email);
//            manage_today_data.Add_To_Db(today_data);
            System.out.println(today_data.get_star_data());

        }
    }
    public static void main(String []args){
        test2 t =new test2("781878580@qq.com");
    }
}
