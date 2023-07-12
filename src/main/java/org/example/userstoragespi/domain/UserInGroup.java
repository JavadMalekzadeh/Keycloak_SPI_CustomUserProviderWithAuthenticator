package org.example.userstoragespi.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OF1USR2GRP")
public class UserInGroup implements Serializable {

    @Id
    @GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    @Column(name = "O1019ID")
    private Long id;

    @Column(name = "O1002ID")
    private Long userId;

    @Column(name = "O1007ID")
    private Long groupId;

    @Column(name = "O1019LSTUPDBRN")
    private String lastUpdaterBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O1007ID", insertable = false, updatable = false)
    private UserGroup userGroup;

    @Column(name = "O1019CRDTSTRT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userInGroupStartDate;

    @Column(name = "O1019CRDTEND")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userInGroupEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLastUpdaterBranch() {
        return lastUpdaterBranch;
    }

    public void setLastUpdaterBranch(String lastUpdaterBranch) {
        this.lastUpdaterBranch = lastUpdaterBranch;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Date getUserInGroupStartDate() {
        return userInGroupStartDate;
    }

    public void setUserInGroupStartDate(Date userInGroupStartDate) {
        this.userInGroupStartDate = userInGroupStartDate;
    }

    public Date getUserInGroupEndDate() {
        return userInGroupEndDate;
    }

    public void setUserInGroupEndDate(Date userInGroupEndDate) {
        this.userInGroupEndDate = userInGroupEndDate;
    }
}

