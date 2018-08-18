package com.example.bean_compiler;

import javax.lang.model.type.TypeMirror;

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
