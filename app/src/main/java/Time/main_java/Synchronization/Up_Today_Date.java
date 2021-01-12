package Time.main_java.Synchronization;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class Up_Today_Date {
   private Socket clientsocket=null;
   private DataOutputStream os;
   private DataInputStream is;
   public boolean connectHost(){//建立服务器连接
        if(clientsocket==null){
            try{clientsocket=new Socket("47.102.47.139",8888);
                is=new DataInputStream(clientsocket.getInputStream());
                os=new DataOutputStream(clientsocket.getOutputStream());
            }catch(UnknownHostException e)
            {System.out.println("Trying to connect unknown Host"+e);
                return false;
            }catch(IOException e){
                System.out.println(e.toString());
                return false;
            }
        }
        return true;

    }
    public boolean sendData(String toServer){                            //自定义向服务器端发送数据方法

        try{ if(toServer.length()>0) {//判断语句是否为空
            os.writeUTF(toServer);
            os.flush();
        }
        }catch(Exception e){
            System.out.println("Exception in sendData:"+e);
            return false;
        }
        return true;
    }
    public String getData(){//服务器端得到数据
        String fromServer=null;
        try{fromServer=is.readUTF();//从服务器端接收信息，从其它用户下拉列表中加入或删除
            System.out.println(fromServer);
        }catch(IOException e){return null;
        } //try
        return fromServer;
    }//get
    //    Start_Date=dd[0];
//	End_Date=dd[1];
//	Alarm_Type=Integer.valueOf(dd[2]);
//	Word=dd[3];
//	Email=dd[4];
//	Finish_Type=Integer.valueOf(dd[5]);
}


