package com.ti.airmovil.mabe.Models;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class Test {
    private String name;
    private String url;

    public Test(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
