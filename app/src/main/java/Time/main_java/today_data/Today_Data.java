package Time.main_java.today_data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//programing by  Diao 2019/8/16
//今日数据的bean。
/*封装对数据进行处理*/
public class Today_Data implements Parcelable {
    private Calendar start_data,end_data;
    private  int alarm_type;
    private String word;
    private int Finish_Type;
    private String email;
    public Today_Data()
    {
        this.start_data=Calendar.getInstance();
        this.end_data=Calendar.getInstance();
        this.start_data.set(start_data.get(Calendar.YEAR),start_data.get(Calendar.MONTH),start_data.get(Calendar.DATE),start_data.get(Calendar.HOUR_OF_DAY),start_data.get(Calendar.MINUTE));
        this.end_data.set(end_data.get(Calendar.YEAR),end_data.get(Calendar.MONTH),end_data.get(Calendar.DATE),end_data.get(Calendar.HOUR_OF_DAY),end_data.get(Calendar.MINUTE));
        this.alarm_type=-1;
        this.word=null;
        this.email=null;
        this.Finish_Type=0;
        /**
         * 0:没有执行
         * 1：已完成
         * 2：不做
         * 3：延迟10分钟
         * 4：正在执行
         *
         *
         * */
    }

    protected Today_Data(Parcel in) {
        alarm_type = in.readInt();
        word = in.readString();
        Finish_Type = in.readInt();
        email = in.readString();
        start_data = To_Calendar(in.readLong());
        end_data = To_Calendar(in.readLong());
    }


    public void set_start_data(Calendar c1){
        this.start_data=c1;
    }
    public void set_end_data(Calendar c1)
    {
        this.end_data=c1;
    }
    public void set_word(String word){
        this.word=word;
    }
    public void setAlarm_type(int type){this.alarm_type=type;}
    public void set_Finish_Type(int type){this.Finish_Type=type;}
    public void set_Email(String email){this.email=email;}
    public String getWord(){
        return this.word;
    }
    public int getAlarm_type(){return alarm_type;}
    public Calendar get_star_data(){
        return  start_data;
    }
    public Calendar get_end_data(){
        return end_data;
    }
    public int get_Finish_Type(){return Finish_Type;}
    public String getEmail(){return email;}
    private long To_Format_String(Calendar t){
        Long h=t.getTimeInMillis();
        return h;
    }
    private Calendar To_Calendar(long t){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(t);
        return c;
    }

    public static final Creator<Today_Data> CREATOR = new Creator<Today_Data>() {
        @Override
        public Today_Data createFromParcel(Parcel in) {
            return new Today_Data(in);
        }

        @Override
        public Today_Data[] newArray(int size) {
            return new Today_Data[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alarm_type);
        dest.writeString(word);
        dest.writeInt(Finish_Type);
        dest.writeString(email);
        dest.writeLong(To_Format_String(start_data));
        dest.writeLong(To_Format_String(end_data));
    }

}
