package Time.main_java.today_data;

/**
 * jie
 */
public class Today_Datas_Strc{
    Today_Data_Node head=null;
    class Today_Data_Node{
        Today_Data_Node next=null;
        Today_Data data;
        public Today_Data_Node(Today_Data data){
            this.data=data;
        }
    }

    public void Add_Today_Data(Today_Data data){
        Today_Data_Node new_today_data_node = new Today_Data_Node(data);
        if(head==null){
            head=new_today_data_node;
            return;
        }
        Today_Data_Node temp=head;
        while (temp.next!=null)
        {
            temp=temp.next;

        }
        temp.next=new_today_data_node;
    }

    public void disp(){
        Today_Data_Node tmp = head;
        while (tmp!=null)
        {
            System.out.println(tmp.data.getWord());
            tmp=tmp.next;
        }
    }
}
