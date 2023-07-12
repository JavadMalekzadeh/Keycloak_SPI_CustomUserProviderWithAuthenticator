package org.example.userstoragespi.domain;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "OC1USER")
public class User implements Serializable {
    @Id
    @GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    @Column(name = "O1002ID")
    private Long id;

    @Column(name = "O1002COD")
    @NotNull
    private String username;

    @Column(name = "O1002NAM")
    private String name;

    @Column(name = "O1002ENGNAM")
    private String englishName;

    @Column(name = "O1002PSWD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "OF1USR2GRP",
            joinColumns = @JoinColumn(name = "O1002ID", referencedColumnName = "O1002ID"),
            inverseJoinColumns = @JoinColumn(name = "O1007ID", referencedColumnName = "O1007ID"))
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserInBranch> userInBranches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public List<UserInBranch> getUserInBranches() {
        return userInBranches;
    }

    public void setUserInBranches(List<UserInBranch> userInBranches) {
        this.userInBranches = userInBranches;
    }
}
