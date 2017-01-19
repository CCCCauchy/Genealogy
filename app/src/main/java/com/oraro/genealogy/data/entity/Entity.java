package com.oraro.genealogy.data.entity;

import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Administrator on 2016/10/19.
 */
public abstract class Entity extends BaseModel {
    private Long id;

    //get and set
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
