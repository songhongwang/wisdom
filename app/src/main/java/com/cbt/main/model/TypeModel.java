package com.cbt.main.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by vigorous on 18/4/2.
 */

public class TypeModel implements IPickerViewData {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeModel(String id,String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
