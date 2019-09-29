package com.adarshya.thuliproject;

public class UserProfile {
    public String username;
    public String userphno;
    public String useraddress;
    public String userlocation;
    public String useremail;
    public float usercredit;

    public UserProfile(String username, String userphno, String useraddress, String userlocation, String useremail){
        this.username=username;
        this.userphno=userphno;
        this.useraddress=useraddress;
        this.userlocation = userlocation;
        this.useremail=useremail;
        this.usercredit=10;
    }
}
