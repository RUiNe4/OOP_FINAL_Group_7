package com.MainApplication.Controller;

import com.ProductManagement.Cart;
import com.ProductManagement.ManageProduct;
import com.UserManagement.LoginAuthentication;
import com.UserManagement.ManageEmployee;
import com.UserManagement.User;
import com.ProductManagement.Product;
import com.ProductManagement.TempProduct;
import com.itextpdf.text.Document;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController extends GridController implements Initializable {
  private static Cart cart;
  private static ArrayList<TempProduct> tempProducts = new ArrayList<>();
  private static boolean read;

  public static Cart getCart() {
    return cart;
  }
  @FXML
  private GridPane vegetableGrid;
  @FXML
  private GridPane fruitGrid;
  @FXML
  private GridPane drinkGrid;
  @FXML
  private GridPane foodGrid;
  @FXML
  private GridPane dairyGrid;

  @FXML
  private GridPane horizonGrid;
  private Product product = new Product();
  private TempProduct tempProduct = new TempProduct();
  @FXML
  private Label cartStatus;
  @FXML
  private Label userName;
  @FXML
  private TextField searchProduct;
  @FXML
  private Label totalPrice;
  private ManageEmployee manageEmployee = new ManageEmployee();
  private User user = manageEmployee.getUserByActive();
  private SceneController sceneController = new SceneController();
  private final static ArrayList<Cart> carts = new ArrayList<>();

  public static ArrayList<Cart> getCarts() {
    return carts;
  }

  public ProductController() throws Exception {
  }

  private void initTab( String productType) throws Exception {
    checkRead(productType, dairyGrid);
  }

  private void foodTab( String productType) throws Exception {
    checkRead(productType, foodGrid);
  }

  private void dairyTab( String productType) throws Exception {
    checkRead(productType, dairyGrid);
  }
  private void fruitTab( String productType) throws Exception {
    checkRead(productType, fruitGrid);
  }
  private void vegetableTab(String productType) throws Exception {
    checkRead(productType, vegetableGrid);
  }
  private void drinksTab(String productType) throws Exception {
    checkRead(productType, drinkGrid);
  }
  private void checkRead(String productType, GridPane gridTab) throws Exception {
    if (!read) {
      tempProduct.createTable();
      TempProduct tempProduct = new TempProduct();
      ArrayList<Product> products = product.readFromDB();
      //copy from product db to tempcart db
      for (int i = 0; i < products.size(); i++) {
        tempProduct = tempProduct.searchProduct(products.get(i).getPid());
        tempProduct.addTemp(tempProduct.getProductID(), tempProduct.getProductName(), tempProduct.getProductPrice(), tempProduct.getProductQty(), tempProduct.getProductType());
        tempProducts.add(tempProduct);
      }
      read = true;
    } else {
      tempProducts.clear();
      tempProducts = tempProduct.filterItem(productType);
      int column = 0;
      int row = 1;
      for (int i = 0; i < tempProducts.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader(ProductController.class.getResource("item-view.fxml"));
        HBox hBox = fxmlLoader.load();
        GridController gridController = fxmlLoader.getController();
        gridController.setData(tempProducts.get(i));
        if (column == 2) {
          column = 0;
          row++;
        }
        gridTab.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridTab.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridTab.setMaxWidth(Region.USE_COMPUTED_SIZE);

        gridTab.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridTab.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridTab.setMaxHeight(Region.USE_COMPUTED_SIZE);

        gridTab.add(hBox, column++, row);
        GridPane.setMargin(hBox, new Insets(10));
      }
    }
  }

  protected static ArrayList<Cart> setCartProduct(Cart product) {
    cart = product;
    carts.add(cart);
    return carts;
  }

  public static void removeFromCart(Cart cart) {
    carts.remove(cart);
  }

  public void cartItem() {
    int column = 1;
    if (carts.isEmpty()) {
      cartStatus.setText("Empty Cart");
    } else {
      cartStatus.setText("My Cart");
      for (int i = 0; i < carts.size(); i++) {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(ProductController.class.getResource("added-item-list.fxml"));
          AnchorPane anchorPane = fxmlLoader.load();
          CartController cartController = fxmlLoader.getController();
          cartController.setCart(carts.get(i));
          horizonGrid.add(anchorPane, column, i);
          GridPane.setMargin(anchorPane, new Insets(5));
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  public void clearCart(ActionEvent event) throws Exception {
    try {
      for (int i = 0; i < carts.size(); i++) {
        tempProduct = tempProduct.searchTemp(carts.get(i).getProductID());
        if (tempProduct == null) {
          tempProduct = new TempProduct();
          tempProduct.addTemp(carts.get(i).getProductID(), carts.get(i).getProductName(), carts.get(i).getProductPrice(), carts.get(i).getProductQty(), carts.get(i).getProductType());
        } else {
          tempProduct.updateTempCart(carts.get(i).getProductID(), tempProduct.getProductQty() + carts.get(i).getProductQty());
        }
      }
      carts.removeAll(carts);
      sceneController.switchSceneButton(event, "product-view.fxml");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void confirmItem(ActionEvent event) throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    float tmpTotal = 0;
    try {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("POS - SYSTEM");
      alert.setHeaderText("PRINT ORDER?");
      alert.setContentText("CONFIRM");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        for (int i = 0; i < carts.size(); i++) {
          product = new Product();
          product = product.searchProduct(carts.get(i).getProductID());
          tmpTotal += carts.get(i).getTotal();
          product.updateProduct(carts.get(i).getProductID(), (product.getPqty() - carts.get(i).getProductQty()));
          carts.get(i).setCartID(cart.generateID());
        }
        cart.setTotalPrice(tmpTotal);
        totalPrice.setText("Total Price = $" + tmpTotal);
        cart.saveToDb(carts);

        String filePath = "C:\\Users\\sunch\\Desktop\\OOP_Final_Project\\src\\main\\resources\\com\\MainApplication\\Controller\\invoice\\RCPT" + (cart.generateID() - 1) + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Paragraph paragraph = new Paragraph("CASHIER: " + user.getUserName() + "              INVOICE NUMBER:" + (cart.generateID() - 1) + "\n\n");
        Paragraph address = new Paragraph("\nAddress: Phnom Penh Cadt");
        Paragraph invoiceDate = new Paragraph("\nDate: " + formatter.format(date) + "\n");
        Paragraph indent = new Paragraph("\n");

        document.add(paragraph);
        document.add(address);
        document.add(invoiceDate);
        document.add(indent);

        PdfPTable table = new PdfPTable(4);
        PdfPCell c1 = new PdfPCell(new Phrase("Product Name"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Price"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Quantity"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total"));
        table.addCell(c1);

        table.setHeaderRows(1);

        for (int i = 0; i < carts.size(); i++) {
          table.addCell(carts.get(i).getProductName());
          table.addCell(String.valueOf(carts.get(i).getProductPrice()));
          table.addCell(String.valueOf(carts.get(i).getProductQty()));
          table.addCell(String.valueOf(carts.get(i).getTotal()));
        }

        document.add(table);

        Paragraph totalPrice = new Paragraph("Total Price: " + cart.getTotalPrice());
        document.add(totalPrice);

        document.close();

        Alert finishPDF = new Alert(Alert.AlertType.INFORMATION);
        finishPDF.setTitle("POS SYSTEM - Group 7");
        finishPDF.setHeaderText("FINISHED CREATING RECEIPT");
        finishPDF.setContentText("The Total Price is: " + cart.getTotalPrice());
        finishPDF.showAndWait();

        carts.clear();
        sceneController.switchSceneButton(event, "product-view.fxml");
      } else {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("POS - Group 7");
        alert1.setHeaderText("Back To menu");
        alert1.setContentText("Feel Free to add more Products");
        alert1.showAndWait();
      }

      sceneController.switchSceneButton(event, "product-view.fxml");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void addProduct(ActionEvent event) throws Exception {
    Cart tmp = new Cart();
    tmp = tmp.searchProduct(Integer.parseInt(searchProduct.getText()));
    if (tmp == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("POS System - Group 7");
      alert.setHeaderText("Not Found");
      alert.showAndWait();
    } else {
      tmp.setProductQty(1);
      tempProduct = tempProduct.searchProduct(Integer.parseInt(searchProduct.getText()));
      tempProduct.setProductQty(tempProduct.getProductQty() - 1);
      tempProduct.updateTempCart(
        Integer.parseInt(searchProduct.getText()),
        tempProduct.getProductQty()
      );
      setCartProduct(tmp);
    }
    sceneController.switchSceneButton(event, "product-view.fxml");
  }

  public void logoutButton(ActionEvent event) throws Exception {
    manageEmployee.editActive(user.getUserID(), 0);
    sceneController.switchSceneButton(event, "login-view.fxml");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      userName.setText(user.getUserName());

      cartItem();
      initTab("init");
      foodTab( "Food");
      dairyTab("Dairy");
      vegetableTab("Vegetables");
      drinksTab("Drinks");
      fruitTab("Fruits");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
