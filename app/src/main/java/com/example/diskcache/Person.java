package com.example.diskcache;

import java.io.Serializable;

/**
 * Created by jack on 2018/3/10.
 */

public class Person implements Serializable{

    public Person(String name, String age){
        this.name = name;
        this.age = age;
    }

    public String name;

    public String age;

}
