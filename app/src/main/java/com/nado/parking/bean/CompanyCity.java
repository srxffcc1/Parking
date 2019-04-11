package com.nado.parking.bean;

import com.zaaach.citypicker.model.City;

public class CompanyCity extends City {

    public CompanyCity(String name, String province, String pinyin,String code) {

        super(name, province, pinyin, code);
    }
    public String city;
    public String company;
    public String letter;
    public String is_hot;
}
