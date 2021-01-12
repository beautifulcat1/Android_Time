package Time.main_java.today_data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import Time.main_java.R;
import Time.main_java.Synchronization.Up_Today_Date;
import Time.main_java.Theone.Manage_user;
import Time.main_java.Theone.User_bean;


/*programing by  Diao*/
/*adapter用于支持界面数据的显示*/
public class todayAdapter extends RecyclerView.Adapter <todayAdapter.ViewHolder>{
    private Context mContext;
    private Manage_Today_Data mange_today_data = new Manage_Today_Data();
    private int  Edit_Model=0;
    private List<Today_Data>mToday_Data,temp,temp2;
    long mtd;
    String ema;

    List<Today_Data> m=new LinkedList<>();
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView bg;
        TextView Start_data,End_data;
        TextView context1;
        ImageView delet_Button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            bg = (TextView)itemView.findViewById(R.id.bg);
            Start_data= (TextView)itemView.findViewById(R.id.start_data);
            End_data=(TextView)itemView.findViewById(R.id.End_data);
            context1 = (TextView)itemView.findViewById(R.id.word);
            delet_Button=itemView.findViewById(R.id.delet_button);
        }
    }

    public todayAdapter(){

        /**只显示今天的时间，若今天已过则不显示*/
        temp=get_today_data();
        if( !temp.isEmpty())
        {
            mToday_Data=temp;
        }
        else mToday_Data=new LinkedList<>();
    }



        public List<Today_Data> get_today_data(){

            List<Today_Data>temp3;
        temp3=mange_today_data.Get_From_Db();
        for(int i=0;i<=temp3.size()-1;i++){

            if(!temp3.isEmpty()&&temp3.get(i).get_star_data().get(Calendar.DATE)>=Calendar.getInstance().get(Calendar.DATE))
            {
                m.add(temp3.get(i));
            }
        }
            temp3.clear();
        return m;
    }




    public void add_data(Today_Data today_data){
        mToday_Data.clear();

        mange_today_data.Add_To_Db(today_data);
        temp2=get_today_data();
        if( !temp2.isEmpty())
        { mToday_Data=new LinkedList<>();
            mToday_Data=temp2;
        }
        notifyDataSetChanged();

    }
    public void delet_data(int i){
        Calendar c1= mToday_Data.get(i).get_star_data();
        Calendar c2 =mToday_Data.get(i).get_end_data();
        mange_today_data.Delet_From_Db(c1,c2);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null)
        {
            mContext=viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Calendar Start_time,End_time;
        Today_Data data = mToday_Data.get(i);
        viewHolder.context1.setText(data.getWord());
        Start_time=data.get_star_data();
        End_time=data.get_end_data();
        String Start =Start_time.get(Calendar.HOUR_OF_DAY)+":"+Start_time.get(Calendar.MINUTE);
        String End = End_time.get(Calendar.HOUR_OF_DAY)+":"+End_time.get(Calendar.MINUTE);
        viewHolder.Start_data.setText(Start);
        viewHolder.End_data.setText(End);
        if(data.getAlarm_type() ==-1){
            viewHolder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.moreng));
        }
        if(data.getAlarm_type()==0){
            viewHolder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.tangchuang));
        }
        if(data.getAlarm_type()==1){
            viewHolder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.shock));
        }
        if(data.getAlarm_type()==2){
            viewHolder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.alarm));
        }

        if(Edit_Model==0){
            viewHolder.delet_Button.setVisibility(View.INVISIBLE);
        }

        if(Edit_Model==1) {
            viewHolder.delet_Button.setVisibility(View.VISIBLE);
        }

        viewHolder.delet_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_bean user_bean =new User_bean();
                Manage_user manage_user=new Manage_user();
                user_bean=manage_user.Query_From_Db();
                ema=user_bean.getemail();
                mtd=mToday_Data.get(i).get_star_data().getTimeInMillis();
               new Thread(new Runnable() {
                   String b=new String();

                   @Override
                   public void run() {

                       b="$$$$$"+mtd+"==="+ema;
                       Up_Today_Date up_today_date =new Up_Today_Date();
                       if(up_today_date.connectHost()){
                           up_today_date.sendData(b);
                       }
                   }
               }).start();
                delet_data(i);
                mToday_Data.remove(i);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return   mToday_Data.size();
    }



    public void Edit_Model(int i){
        this.Edit_Model=i;
    }

}
