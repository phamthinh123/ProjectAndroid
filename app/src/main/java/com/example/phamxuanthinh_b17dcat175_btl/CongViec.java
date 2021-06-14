package com.example.phamxuanthinh_b17dcat175_btl;

import java.io.Serializable;

public class CongViec implements Serializable {
private String id;
private String ten,ngay,gio,mota;

    public CongViec() {
    }

    public CongViec(String id, String ten, String ngay, String gio, String mota) {
        this.id = id;
        this.ten = ten;
        this.ngay = ngay;
        this.gio=gio;
        this.mota = mota;
    }

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getNgay() {
        return ngay;
    }

    public String getGio() {
        return gio;
    }

    public String getMota() {
        return mota;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
