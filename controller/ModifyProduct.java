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
import java.util.ResourceBundle;
/**
 *
 * @JessicaGreenberg
 * Student ID: #001462404
 */
public class ModifyProduct implements Initializable {
    @FXML
    Button btnCancelModifyProduct;
    @FXML
    private TextField modifyProductId;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductStock;
    @FXML
    private TableColumn<Object, Object> partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partStockColumn;
    @FXML
    private TableColumn partPriceColumn;
    @FXML
    private TextField ModifyProductSearchBar;

    @FXML
    private TableColumn AssociatedPartsIDCol;
    @FXML
    private TableColumn AssociatedPartsNameCol;
    @FXML
    private TableColumn AssociatedPartsStockCol;
    @FXML
    private TableColumn AssociatedPartsPriceCol;

    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableView<Part> modifyProductInventoryTableview;


    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product testProduct;

    /**Navigation to Main Screen*/
    public void CancelModifyProduct() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You sure?");
        alert.setContentText("Do you want to Cancel Modifying a Product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage window = (Stage) btnCancelModifyProduct.getScene().getWindow();
            window.setScene(new Scene(root, 900, 400));
        }
    }

    /**retrieves values from the table on the MainScreen*/
    public void sendProduct(Product product) {
        testProduct = product;
        modifyProductId.setText(String.valueOf(testProduct.getId()));
        modifyProductName.setText(String.valueOf(testProduct.getName()));
        modifyProductPrice.setText(String.valueOf(testProduct.getPrice()));
        modifyProductStock.setText(String.valueOf(testProduct.getStock()));
        modifyProductMin.setText(String.valueOf(testProduct.getMin()));
        modifyProductMax.setText(String.valueOf(testProduct.getMax()));
        associatedPartsTable.setItems(testProduct.getAllAssociatedParts());
    }

    /**Implementation of the Parts Search Bar*/
    @FXML
    private void ModifyProductSearchBtn(ActionEvent event) {
        {
            ObservableList<Part> allParts = Inventory.getAllParts();
            ObservableList<Part> partsFound = FXCollections.observableArrayList();
            String searchString = ModifyProductSearchBar.getText();

            for (Part part : allParts) {
                if (String.valueOf(part.getId()).contains(searchString) ||
                        part.getName().contains(searchString)) {
                    partsFound.add(part);
                }
            }

            modifyProductInventoryTableview.setItems(partsFound);

            if (partsFound.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sorry, No Part by this Name is found");

            }
    }
    }

    /**
     * Save Button Functionality
     */
    @FXML
    private void SaveBtn(ActionEvent event) throws IOException {
        //try// {

        testProduct.setId(Integer.parseInt(modifyProductId.getText()));
        testProduct.setName(modifyProductName.getText());
        testProduct.setPrice(Double.parseDouble(modifyProductPrice.getText()));
        testProduct.setStock(Integer.parseInt(modifyProductStock.getText()));
        testProduct.setMin(Integer.parseInt(modifyProductMin.getText()));
        testProduct.setMax(Integer.parseInt(modifyProductMax.getText()));


        String name = modifyProductName.getText();
        Double price = Double.parseDouble(modifyProductPrice.getText());
        int stock = Integer.parseInt(modifyProductStock.getText());
       int min = Integer.parseInt(modifyProductMin.getText());
        int max = Integer.parseInt(modifyProductMax.getText());
            if (name.isEmpty()) {
                MainScreen.infoDialog("Input Error", "Please Input a name", "A product must have a name.");
                return;}
            if(min > max){
                JOptionPane.showMessageDialog(null, "min capacity cannot be a higher value than max");
                return;
            } if (stock > max){
                JOptionPane.showMessageDialog(null, "You cannot report more in stock than your maximum capacity allots");
                return;
            } else {  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save?");
                alert.setContentText("Do you want to Save this product");
                Optional<ButtonType> result = alert.showAndWait();
 if(result.isPresent() && result.get() == ButtonType.CANCEL) {return;}
                if (result.isPresent() && result.get() == ButtonType.OK) {



        Inventory.updateProduct(Integer.parseInt(modifyProductId.getText()), testProduct);


    /****Confirmation Box that User has Added Part****/
            JOptionPane.showMessageDialog(null,"You have Successfully Modified a Product");

    /****After saving the data, users are automatically redirected to the Main form****/
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new

    Scene((Parent) scene));
            stage.show();

}
            }
    }






        /**removes a part associated with a product*/
    @FXML
    private void modifyProductRemovePart(ActionEvent event) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {MainScreen.confirmDialog("Select a part", "Please select a part to delete");}
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are You sure?");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                testProduct.deleteAssociatedPart(selectedPart);
            }
        }
    }
/** adds selected part in top table to associated parts table on bottom of screen*/
    @FXML
    private void addBtn(ActionEvent event)
    {
        Part selectedPart = modifyProductInventoryTableview.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        } else {
            testProduct.addAssociatedPart(selectedPart);
            associatedPartsTable.setItems(testProduct.getAllAssociatedParts());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Upper Table//
        modifyProductInventoryTableview.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Lower Table//
        AssociatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}






