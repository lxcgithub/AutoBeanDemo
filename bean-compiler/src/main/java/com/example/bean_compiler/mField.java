package com.example.bean_compiler;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class mField {
    private String value;
    private String name;
    private TypeMirror type ;

    
    public mField(String name, TypeMirror type,String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }

}
