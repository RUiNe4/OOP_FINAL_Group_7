package com.InvoiceManagement;

import java.util.Date;

public class Invoice {
  private int cardID;
  private double totalPrice;
  private Date date;
  private String cashier;

  public int getCardID() {
    return cardID;
  }

  public void setCardID(int cardID) {
    this.cardID = cardID;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getCashier() {
    return cashier;
  }

  public void setCashier(String cashier) {
    this.cashier = cashier;
  }

}
