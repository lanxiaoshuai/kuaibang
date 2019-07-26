package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

public class Acount {
//    balance	        BigDecimal	可用余额(金币)
//    frozen	        BigDecimal	冻结金额(金币)
//    diamondsBalance	int	可用余额(钻石)
//    diamondsFrozen	int	冻结金额(钻石)
//    createUserId	    int	用户ID

    @SerializedName("balance")
    private Double balance;
    @SerializedName("frozen")
    private Double frozen;
    @SerializedName("diamondsBalance")
    private int diamondsBalance;
    @SerializedName("diamondsFrozen")
    private int diamondsFrozen;
    @SerializedName("createUserId")
    private int createUserId;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFrozen() {
        return frozen;
    }

    public void setFrozen(Double frozen) {
        this.frozen = frozen;
    }

    public int getDiamondsBalance() {
        return diamondsBalance;
    }

    public void setDiamondsBalance(int diamondsBalance) {
        this.diamondsBalance = diamondsBalance;
    }

    public int getDiamondsFrozen() {
        return diamondsFrozen;
    }

    public void setDiamondsFrozen(int diamondsFrozen) {
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

    public Acount(Double balance, Double frozen, int diamondsBalance, int diamondsFrozen, int createUserId) {
        this.balance = balance;
        this.frozen = frozen;
        this.diamondsBalance = diamondsBalance;
        this.diamondsFrozen = diamondsFrozen;
        this.createUserId = createUserId;
    }
}
