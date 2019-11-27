
package com.example.androidweatherchallenge.model.forecast;

import com.google.gson.annotations.Expose;

public class Sys {

    @Expose
    private String pod;

    public Sys() {
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
