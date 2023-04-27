
package com.example.postapi_demo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class MyviewProducts {

    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("productdata")
    @Expose
    private List<Productdatum> productdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Collection<? extends Productdatum> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<Productdatum> productdata) {
        this.productdata = productdata;
    }

    @Override
    public String toString() {
        return "MyviewProducts{" +
                "connection=" + connection +
                ", result=" + result +
                ", productdata=" + productdata +
                '}';
    }
}
