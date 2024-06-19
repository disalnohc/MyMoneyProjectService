package com.project.mymoney.MyMoneyProject.model;

public class RegularNextPay {
    private Long id;
    private String username;
    private String name;
    private Double price;
    private String start;
    private String end;
    private String cycle;
    private String type;
    private String currentPay;
    private String nextPay;
    private String pay;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNextPay() {
        return nextPay;
    }

    public void setNextPay(String nextPay) {
        this.nextPay = nextPay;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getCurrentPay() {
        return currentPay;
    }

    public void setCurrentPay(String currentPay) {
        this.currentPay = currentPay;
    }
}
