package com.itheima;


//账户类
public class Account {

    /**
     * 成员变量，私有
     */
    private String carId;//卡号
    private String userName;// 账户名
    private String passWorld;// 账户密码
    private double money;// 余额；
    private double quotaMoney;// 取现额度

    public Account() {
    }

    public Account(String carId, String userName, String passWorld, double money, double quotaMoney) {
        this.carId = carId;
        this.userName = userName;
        this.passWorld = passWorld;
        this.money = money;
        this.quotaMoney = quotaMoney;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWorld() {
        return passWorld;
    }

    public void setPassWorld(String passWorld) {
        this.passWorld = passWorld;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}
