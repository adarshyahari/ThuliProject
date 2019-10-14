package com.adarshya.thuliproject;

public class Profile {
    public String username;
    public String userphno;
    public String useraddress;
    public String userlocation;
    public String useremail;
    public String type;
    public float usercredit;

    public Profile(String username, String userphno, String useraddress, String userlocation, String useremail, String type){
        this.username=username;
        this.userphno=userphno;
        this.useraddress=useraddress;
        this.userlocation = userlocation;
        this.useremail=useremail;
        this.usercredit=10;
        this.type=type;
    }
    public String getType(){
        return type;
    }
}

