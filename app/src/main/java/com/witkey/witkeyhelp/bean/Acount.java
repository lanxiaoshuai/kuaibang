package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import rx.observers.SerializedObserver;

public class Acount {
//    balance	        BigDecimal	可用余额(金币)
//    frozen	        BigDecimal	冻结金额(金币)
//    diamondsBalance	int	可用余额(钻石)
//    diamondsFrozen	int	冻结金额(钻石)
//    createUserId	    int	用户ID

    @SerializedName("balance")
    private double balance;
    @SerializedName("frozen")
    private double frozen;
    @SerializedName("diamondsBalance")
    private double diamondsBalance;
    @SerializedName("diamondsFrozen")
    private double diamondsFrozen;
    @SerializedName("createUserId")
    private int createUserId;
    @SerializedName("deposit")
    private double deposit;
    @SerializedName("reputationNum")
    private int reputationNum;


    public int getReputationNum() {
        return reputationNum;
    }

    public void setReputationNum(int reputationNum) {
        this.reputationNum = reputationNum;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "Acount{" +
                "balance=" + balance +
                ", frozen=" + frozen +
                ", diamondsBalance=" + diamondsBalance +
                ", diamondsFrozen=" + diamondsFrozen +
                ", createUserId=" + createUserId +
                '}';
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }

    public double getDiamondsBalance() {
        return diamondsBalance;
    }

    public void setDiamondsBalance(double diamondsBalance) {
        this.diamondsBalance = diamondsBalance;
    }

    public double getDiamondsFrozen() {
        return diamondsFrozen;
    }

    public void setDiamondsFrozen(double diamondsFrozen) {
        this.diamondsFrozen = diamondsFrozen;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public Acount() {
    }

    public Acount(double balance, double frozen, double diamondsBalance, double diamondsFrozen, int createUserId) {
        this.balance = balance;
        this.frozen = frozen;
        this.diamondsBalance = diamondsBalance;
        this.diamondsFrozen = diamondsFrozen;
        this.createUserId = createUserId;
    }
}
