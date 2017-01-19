package com.oraro.genealogy.data.entity;

import com.oraro.genealogy.data.database.GenealogyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
@ModelContainer
@Table(database = GenealogyDatabase.class)
public class User extends Entity {
    @Column
    @PrimaryKey()
    private int userId;
    @Column
    private String genealogyName;
    @Column
    private String token;
    @Column
    private int role;
    @Column
    private int isFirstLogin;
    @Column
    private String userHeadImageUrl;
    @Column
    private Boolean hasLockSetup;
    @Column
    private Boolean isCurrentUser;
    private List<String> roleName;

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public Boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        isCurrentUser = currentUser;
    }

    public void setHasLockSetup(Boolean hasLockSetup) {
        this.hasLockSetup = hasLockSetup;
    }
    public Boolean isHasLockSetup(){
        return hasLockSetup;
    }


    public String getUserHeadImageUrl() {
        return userHeadImageUrl;
    }

    public void setUserHeadImageUrl(String userHeadImageUrl) {
        this.userHeadImageUrl = userHeadImageUrl;
    }

    public int getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGenealogyName() {
        return genealogyName;
    }

    public void setGenealogyName(String genealogyName) {
        this.genealogyName = genealogyName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
