package com.definitions.Mo_Banking;


public class Account {

    private String acc_number;
    private String acc_name;
    private String acc_info;

    public Account() {};
    public Account(String acc_name, String acc_number, String acc_info) {
        this.acc_name   = acc_name;
        this.acc_number = acc_number;
        this.acc_info   = acc_info;
    }

    public String getAcc_number() {
        return acc_number;
    }

    public void setAcc_number(String acc_number) {
        this.acc_number = acc_number;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_info() {
        return acc_info;
    }

    public void setAcc_info(String acc_info) {
        this.acc_info = acc_info;
    }
}
