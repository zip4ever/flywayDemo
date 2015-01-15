package com.realdolmen.flyway.demo.model;

import java.util.Date;

/**
 * Created by Kevin De Man on 25/06/2014.
 */
public class LegalHoliday {

    private long id;

    private Date date;

    private String descriptionNl;

    private String descriptionFr;

    private String descriptionDe;

    private String descriptionEn;

    public LegalHoliday() {

    }

    public LegalHoliday(String descriptionNl, Date date) {
        this.descriptionNl = descriptionNl;
        this.date = date;
    }

    public LegalHoliday(String descriptionNl, String descriptionFr, String descriptionDe, String descriptionEn, Date date){
        this.date = date;
        this.descriptionNl = descriptionNl;
        this.descriptionFr = descriptionFr;
        this.descriptionDe = descriptionDe;
        this.descriptionEn = descriptionEn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescriptionNl() {
        return descriptionNl;
    }

    public void setDescriptionNl(String descriptionNl) {
        this.descriptionNl = descriptionNl;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionDe() {
        return descriptionDe;
    }

    public void setDescriptionDe(String descriptionDe) {
        this.descriptionDe = descriptionDe;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
}
