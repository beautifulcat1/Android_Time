package Time.main_java.Theone;

public class User_bean {
    private String Email,Password,Nackname,Shuoming,Phone,ImagNum,ImagAddr;
    public User_bean(){
        Email=new String();
        Password=new String();
        Nackname=new String();
        ImagNum=new String();
        ImagAddr=new String();
        Shuoming=new String();
        Phone=new String();
    }
    public void Set_Email(String E){
        Email=E;
    }
    public void Set_Password(String P){
        Password=P;
    }
    public void Set_Nackname(String N){
        Nackname=N;
    }
    public void Set_ImaNum(String IN){
        ImagNum=IN;
    }
    public void Set_ImaAddr(String IA){
        ImagAddr=IA;
    }

    public void SetShuoming(String shuoming) {
        Shuoming = shuoming;
    }

    public void SetPhone(String phone) {
        Phone = phone;
    }

    public String getImagAddr() {
        return ImagAddr;
    }

    public String getImagNum() {
        return ImagNum;
    }

    public String getNackname() {
        return Nackname;
    }

    public String getPassword() {
        return Password;
    }

    public String getemail() {
        return Email;
    }

    public String getShuoming() {
        return Shuoming;
    }

    public String getPhone() {
        return Phone;
    }
}
