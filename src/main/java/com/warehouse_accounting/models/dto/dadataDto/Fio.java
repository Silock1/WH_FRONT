package com.warehouse_accounting.models.dto.dadataDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Fio {

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("patronymic")
    @Expose
    private String patronymic;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("sourse")
    @Expose
    private String sourse;

    @SerializedName("qc")
    @Expose
    private String qc;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }
}
