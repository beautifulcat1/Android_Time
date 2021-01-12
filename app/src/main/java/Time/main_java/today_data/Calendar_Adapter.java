package Time.main_java.today_data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import Time.main_java.R;


public class Calendar_Adapter extends RecyclerView.Adapter<Calendar_Adapter.ViewHolder> {
    private List<Today_Data>mToday_Data1=new LinkedList<>();
    private List<Today_Data>mToday_Data2=new LinkedList<>();
    private List<Today_Data>temp;
    private Context mContext;
    private int flag=0;

    private Manage_Today_Data mange_today_data = new Manage_Today_Data();
   static   class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView bg;
        TextView Start_data,End_data;
        TextView context1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.calendarView2);
            bg=itemView.findViewById(R.id.bg2);
            Start_data=itemView.findViewById(R.id.start_data2);
            End_data=itemView.findViewById(R.id.End_data2);
            context1=itemView.findViewById(R.id.word2);

        }

    }
    public Calendar_Adapter(int i){
        temp=mange_today_data.Get_From_Db();
        for(int j=0;j<=temp.size()-1;j++){

            if(!temp.isEmpty()&&temp.get(j).get_star_data().get(Calendar.DATE)==i)
            {
                mToday_Data1.add(temp.get(j));

            }
        }

    }
    public void Set_Data(int month ,int day ){
        if(!mToday_Data2.isEmpty()){
            mToday_Data2.clear();
        }
        flag=1;
        for(int j=0;j<=temp.size()-1;j++){

            if(!temp.isEmpty()&&temp.get(j).get_star_data().get(Calendar.MONTH)==month&&temp.get(j).get_star_data().get(Calendar.DATE)==day)
            {
                mToday_Data2.add(temp.get(j));
            }
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null)
        {
            mContext=viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_layout2,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if(flag==0){
            Calendar Start_time,End_time;
            Today_Data data = mToday_Data1.get(i);
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
        }
        if(flag==1){
            Calendar Start_time,End_time;
            Today_Data data = mToday_Data2.get(i);
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
        }
    }




    @Override
    public int getItemCount() {
        if(flag==0){
            return mToday_Data1.size();
        }
        if (flag==1){
            return mToday_Data2.size();
        }
        return 0;
    }

}
