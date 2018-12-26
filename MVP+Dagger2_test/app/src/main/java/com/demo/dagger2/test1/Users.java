package com.demo.dagger2.test1;

import javax.inject.Inject;

/**
 * @author yzh-t105
 * @time 2018/12/20 12:23
 */
public class Users {
    private String str="Users实例是注解方式注入的";
    @Inject
    public Users(){

    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
