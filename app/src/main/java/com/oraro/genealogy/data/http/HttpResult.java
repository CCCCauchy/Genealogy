package com.oraro.genealogy.data.http;

public class HttpResult<T> {


    /**
     * type : 对result返回结果的详细说明
     * resultDescription : "success"
     * result : 1 或 0
     * data : 返回的对象
     */

    protected int type;
    protected String resultDescription;
    protected int result;
    protected T data;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "type=" + type +
                ", resultDescription=" + resultDescription +
                ", result=" + result +
                ", data=" + data +
                "}";
    }
}
