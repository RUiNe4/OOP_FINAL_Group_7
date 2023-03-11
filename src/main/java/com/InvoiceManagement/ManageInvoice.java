package com.InvoiceManagement;

import com.DatabaseFunction.DBConnection;
import com.UserManagement.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ManageInvoice extends DBConnection {
  DBConnection con = new DBConnection();
  private final Statement statement;
  private final User user = new User();
  Connection connection;
  public ManageInvoice() throws Exception {
    connection = con.getConnection("jdbc:mysql://localhost:3306/possys", "root", "");
    this.statement = connection.createStatement();
  }

  ArrayList<Invoice> invoices = new ArrayList<>();

  public ArrayList<Invoice> readFromDB (){
    try {
      String qry = "select * from invoice";
      ResultSet rs = statement.executeQuery(qry);
      while (rs.next()){
        Invoice invoice = new Invoice();
        invoice.setCardID(rs.getInt("cartID"));
        invoice.setTotalPrice(rs.getDouble("totalPrice"));
        invoice.setDate(rs.getDate("date"));
        invoice.setCashier(rs.getString("cashier"));
        invoices.add(invoice);
      }
    }catch (Exception e){
      System.out.println(e.getMessage());
    }
    return invoices;
  }

}
