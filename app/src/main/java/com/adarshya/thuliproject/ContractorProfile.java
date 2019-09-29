package com.adarshya.thuliproject;

public class ContractorProfile {
    public String compname;
    public String contname;
    public String contphno;
    public String contaddress;
    public String contlocation;
    public String contemail;
    public float contcredit;

    public ContractorProfile(String compname, String contname, String contphno, String contaddress, String contlocation, String contemail){
        this.compname=compname;
        this.contname=contname;
        this.contphno=contphno;
        this.contaddress=contaddress;
        this.contlocation=contlocation;
        this.contemail=contemail;
        this.contcredit = 10;
    }
}

