package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 *
 * @JessicaGreenberg
 * Student ID: #001462404
 */


public class AddProduct implements Initializable {
    @FXML
    Button btnCancelAddProduct;
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductStock;
    @FXML
    private TextField addProductSearch;

/**Parts Table***/
    @FXML
    private TableView addProductPartsTable;
    @FXML
    private TableColumn partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partStockColumn;
    @FXML
    private TableColumn partPriceColumn;

/***associated Parts Table***/
    @FXML
    private TableView associatedPartTable;
    @FXML
    private TableColumn associatedPartIdColumn;
    @FXML
    private TableColumn associatedPartNameColumn;
    @FXML
    private TableColumn associatedPartStockColumn;
    @FXML
    private TableColumn associatedPartPriceColumn;

    @FXML
    private TextField searchBar;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product nProduct = new Product(0,",",0,0,0,0);

    /******Navigation to Main Screen********/
    public void CancelAddProduct() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));

        Stage window = (Stage) btnCancelAddProduct.getScene().getWindow();
        window.setScene(new Scene(root, 900, 400));
    }

        /******* Generate ID for the Product ID number*/
        private void generateProductID() {
            boolean match;
            Random randomNum = new Random();
            Integer num = randomNum.nextInt(10000);


            if (Inventory.productListSize() == 1000) {
                addProductId.setText(num.toString());

            }
            if (Inventory.productListSize() == 10000) {
            } else {
                match = generateNum(num);

                if (!match) {
                    addProductId.setText(num.toString());
                } else {
                    generateProductID();
                }
            }

            addProductId.setText(num.toString());
        }

        private boolean generateNum(Integer num) {
            Part match = Inventory.lookupPartID(num);
            return match != null;
    }

/***Search Bar for listing parts in Table*/
    @FXML
    private void addProductSearchBtn(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = searchBar.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        addProductPartsTable.setItems(productsFound);

        if (productsFound.size() == 0) {
            JOptionPane.showMessageDialog(null, "Sorry, No Part by this Name is found");
        }
        addProductPartsTable.refresh();
    }
    @FXML
    private void addBtn(ActionEvent event)
    {
        Part selectedPart = (Part) addProductPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            nProduct.addAssociatedPart(selectedPart);
            associatedPartTable.setItems(nProduct.getAllAssociatedParts());
        }

        else {
            MainScreen.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }

    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        Part removePart = (Part) associatedPartTable.getSelectionModel().getSelectedItem();
        if (removePart == null) {MainScreen.confirmDialog("Select a part", "Please select a part to delete");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are You sure?");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                nProduct.deleteAssociatedPart(removePart);
            }
        }
    }

    @FXML
    private void saveBtn(ActionEvent event) throws IOException{
        nProduct.setId(Integer.parseInt(addProductId.getText()));
        nProduct.setName(addProductName.getText());
        nProduct.setPrice(Double.parseDouble(addProductPrice.getText()));
        nProduct.setStock(Integer.parseInt(addProductStock.getText()));
        nProduct.setMin(Integer.parseInt(addProductMin.getText()));
        nProduct.setMax(Integer.parseInt(addProductMax.getText()));


        String name = addProductName.getText();
        int stock = Integer.parseInt(addProductStock.getText());
        int min = Integer.parseInt(addProductMin.getText());
        int max = Integer.parseInt(addProductMax.getText());

        if (name.isEmpty()) {
            MainScreen.infoDialog("Input Error", "Please Input a name", "A product must have a name.");
            return;}

        if(min > max){
            JOptionPane.showMessageDialog(null, "min capacity cannot be a higher value than max");
            return;
        } if (stock > max) {
            JOptionPane.showMessageDialog(null, "You cannot report more in stock than your maximum capacity allots");

        } else{ Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save?");
                alert.setContentText("Do you want to Save this product");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    return;
                }
                if (result.isPresent() && result.get() == ButtonType.OK)

                    Inventory.addProduct(nProduct);

                /****Confirmation Box that User has Added Part****/
                JOptionPane.showMessageDialog(null, "You have Successfully added a Product");

                /****After saving the data, users are automatically redirected to the Main form****/
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }


        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateProductID();
    {
        /***Top TableView(Parts Table)***/
        addProductPartsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**Bottom TableView(associated Parts Table)***/

         associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
         associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
         associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
         associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    }
}



