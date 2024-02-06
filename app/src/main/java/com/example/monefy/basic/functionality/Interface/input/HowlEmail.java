package com.example.monefy.basic.functionality.Interface.input;

public enum HowlEmail {
    GMAIL("@gmail.com"),
    HOTMAIL("@hotmail.com"),
    MSN("@msn.com"),
    YAHOO("@yahoo.com"),
    AOL("@aol.com"),
    LIVE("@live.com"),
    INBOX("@inbox.com"),
    AIM("@aim.com"),
    MAIL("@mail.com"),
    WALLA("@walla.com"),
    NETZERO("@netzero.net"),
    TWCNY("@twcny.rr.com"),
    VERIZON("@verizon.net");

    private final String titleEmail;

    HowlEmail(String titleEmail){
        this.titleEmail = titleEmail;
    }

    public String getTitleEmail(){
        return  titleEmail;
    }

    public static HowlEmail[] getAllEmails() {
        return values();
    }
}
