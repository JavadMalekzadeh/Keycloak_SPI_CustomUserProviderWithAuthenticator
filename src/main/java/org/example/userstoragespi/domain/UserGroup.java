package org.example.userstoragespi.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OF1USRGRP")
public class UserGroup implements Serializable {

    @Id
    @GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    @Column(name = "O1007ID")
    private Long id;

    @Column(name = "O1007COD")
    private String code;

    @Column(name = "O1007NAM")
    private String name;

//    @Column(name = "O1007TYP")
//    private UserGroupType type;

    @Column(name = "O1007CRDTSTRT")
    @Temporal(TemporalType.DATE)
    private Date validFromDate;

    @Column(name = "O1007CRDTEND")
    @Temporal(TemporalType.DATE)
    private Date validToDate;

//    @XmlTransient
//    @Embedded
//    private HistoryData historyData;
//
//    @XmlTransient
//    @ManyToMany(mappedBy = "userGroups", fetch = FetchType.LAZY)
//    private List<User> users;

    @Column(name = "O1007DSC")
    private String description;


//    public boolean isInValidMembershipDatePeriod() {
//
//        if(this.validFromDate==null || this.validToDate==null)
//            return true; //????
//
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 23);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.SECOND, 59);
//        cal.set(Calendar.MILLISECOND, 999);
//        Date today = cal.getTime();
//
//        return this.validFromDate.before(new Date()) || this.validFromDate.equals(new Date())
//                && (this.validToDate == null || this.validToDate.after(today));
//    }

    public UserGroup(){

    }
    public UserGroup(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public UserGroup(UserGroup userGroup) {
        this.code=userGroup.code;
        this.name=userGroup.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Date getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}