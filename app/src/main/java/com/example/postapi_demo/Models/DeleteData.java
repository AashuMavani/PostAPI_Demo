package com.example.postapi_demo.Models;

public class DeleteData
{
    int connection;
    int result;

    public int getConnection() {
        return connection;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DeleteData{" +
                "connection=" + connection +
                ", result=" + result +
                '}';
    }
}
