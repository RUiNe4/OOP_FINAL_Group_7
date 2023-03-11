package com.MainApplication.Controller;

import com.ProductManagement.Cart;
import com.ProductManagement.Product;
import com.ProductManagement.TempProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CartController {
  private SceneController sceneController = new SceneController();
  @FXML
  private Label productName;
  @FXML
  private Label productPrice;
  @FXML
  private Label productQty;
  @FXML
  private Label productID;
  @FXML
  private Label subTotalPrice;
  @FXML
  private Label totalPrice;
  private Cart cart;
  private static Cart cartProduct;
  private Product product = new Product();
  private TempProduct tempProduct = new TempProduct();
  public CartController() throws Exception {
  }
  public void setCart(Cart cart) {
    this.cart = cart;
//    float tmpTotal = 0;
    float subTotal = cart.getProductPrice()*cart.getProductQty();
    subTotalPrice.setText(String.valueOf(subTotal));
    productName.setText(cart.getProductName());
    productPrice.setText("$" + (cart.getProductPrice()));
    productQty.setText(String.valueOf(cart.getProductQty()));
    productID.setText("#" + cart.getProductID());
//    tmpTotal += subTotal;
//    totalPrice.setText("Total Price = " + tmpTotal);
  }
  public void removeButton(ActionEvent event) throws Exception {
    try {
      product = new Product();
      product = product.searchProduct(cart.getProductID());
      tempProduct = new TempProduct();
      tempProduct = tempProduct.searchTemp(cart.getProductID());
      if(tempProduct == null){
        tempProduct = new TempProduct();
        tempProduct.addTemp(product.getPid(), product.getPname(), product.getPprice(), product.getPqty(), product.getPtype());
      } else {
        tempProduct.updateTempCart(cart.getProductID(), product.getPqty());
        cart.setProductQty(0);
      }
      ProductController.removeFromCart(cart);
      sceneController.switchSceneButton(event, "product-view.fxml");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void increase(MouseEvent event) throws Exception {
    tempProduct = tempProduct.searchTemp(cart.getProductID());
    if(tempProduct == null){
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("POS SYSTEM - GROUP 7");
      alert.setHeaderText("NO MORE STOCK");
      alert.showAndWait();
      sceneController.switchSceneButton(event, "product-view.fxml");
    }
    if(tempProduct.getProductQty() == 0){
//      tempProduct.deleteItem(cart.getProductID());
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("OUT OF STOCK");
      alert.setHeaderText("IMPORTANT");
      alert.setContentText("Product IS OUT OF STOCK");
      alert.showAndWait();
    } else {
      cart.setProductQty(cart.getProductQty()+1);
      cart.setTotal(cart.getProductQty()*cart.getProductPrice());
      tempProduct.setProductQty(tempProduct.getProductQty()-1);
      productQty.setText(String.valueOf(cart.getProductQty()));
    }
    tempProduct.updateTempCart(cart.getProductID(), tempProduct.getProductQty());
    sceneController.switchSceneButton(event, "product-view.fxml");
  }

  public void decrease(MouseEvent event) throws Exception {
    product = new Product();
    product = product.searchProduct(cart.getProductID());
    tempProduct = tempProduct.searchTemp(cart.getProductID());
    if(cart.getProductQty() == 1){
      ProductController.removeFromCart(cart);
      tempProduct.setProductQty(tempProduct.getProductQty()+1);
    } else {
      cart.setProductQty(cart.getProductQty()-1);
      cart.setTotal(cart.getTotal() - cart.getProductPrice());
      productQty.setText(String.valueOf(cart.getProductQty()+1));
      tempProduct.setProductQty(tempProduct.getProductQty()+1);
    }

    tempProduct.updateTempCart(cart.getProductID(), tempProduct.getProductQty());
    sceneController.switchSceneButton(event, "product-view.fxml");
  }
}

