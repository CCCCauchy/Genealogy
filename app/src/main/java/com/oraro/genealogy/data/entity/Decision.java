package com.oraro.genealogy.data.entity;

import android.graphics.Bitmap;

import com.oraro.genealogy.data.database.GenealogyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19.
 */
@ModelContainer
@Table(database = GenealogyDatabase.class)
public class Decision extends Entity {
    @Column
    @PrimaryKey()
    private int formId;
    @Column
    private String title;
    @Column
    private String content;
    private List<String> optionText;
    private Map<String, String> optionPicture;
    private int type;
    private Date endTime;
    private int state;
    private int voteGroup;
    private Boolean isSecret;
    private Boolean canViewResult;
    private String action;

    public int getVoteGroup() {
        return voteGroup;
    }

    public void setVoteGroup(int voteGroup) {
        this.voteGroup = voteGroup;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptionText() {
        return optionText;
    }

    public void setOptionText(List<String> optionText) {
        this.optionText = optionText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getSecret() {
        return isSecret;
    }

    public void setSecret(Boolean secret) {
        isSecret = secret;
    }

    public Boolean getCanViewResult() {
        return canViewResult;
    }

    public void setCanViewResult(Boolean canViewResult) {
        this.canViewResult = canViewResult;
    }

    public Map<String, String> getOptionPicture() {
        return optionPicture;
    }

    public void setOptionPicture(Map<String, String> optionPicture) {
        this.optionPicture = optionPicture;
    }
}
