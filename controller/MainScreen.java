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
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen implements Initializable {
    /**Button Navigation for main screen**/
    @FXML
    Button btnAddPart;
    @FXML
    Button btnModifyPart;
    @FXML
    Button btnAddProduct;
    @FXML
    Button btnModifyProduct;
    @FXML
    Button btnExit;
    /********** Parts Table ************/
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part, Integer> PartIdColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> PartPriceColumn;
    @FXML
    private TextField searchPart;
    /********* Products Table ***********/
    @FXML
    private TableView<Product> ProductTableView;
    @FXML
    private TableColumn<Product, Integer> ProductIdColumn;
    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;
    @FXML
    private TextField productSearch;
    /**POSSIBLE FUTURE ENHANCEMENTS
    *Application could include a functional database as such through an SQL database
    *Application could implement a scanning function to automate adding parts to associated database circumventing autonomous user input
     *Application could integrate a backdoor functionality for users to be able to address physical count/ auditing capacities for business needs
     ERRORS and Issues
     *Various Errors I experienced throughout the program
     *included: Miscalling radio button ID's vs OnAction on both the add part and add product pages
     *Miscalling Index #'s in methods on the add Product Page
      *Breaking my program w/o exceptions within the form fields on to modify and add parts/products pages.
     *These Errors have been fixed and annotated in their correct placements throughout the program
     Navigation to Add Parts Screen**/
    public void AddPart() throws IOException
    {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/addParts.fxml")));

        Stage window = (Stage) btnAddPart.getScene().getWindow();
        window.setScene(new Scene(root, 900, 400));
    }

    /***********This event handler sends user to Add Products Screen*************/
    public void AddProduct() throws IOException
    {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/addProduct.fxml")));

        Stage window = (Stage) btnAddProduct.getScene().getWindow();
        window.setScene(new Scene(root, 900, 600));
    }

    /***********Navigation to Exit Application via exit button*************/
    public void exitButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit?");
        alert.setContentText("Are you sure you want to exit this ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }
        if (result.isPresent() && result.get() == ButtonType.OK) {
            {

                {
                    System.exit(0);
                }
            }
        }
    }
        //**************Ensures my Sample parts data doesn't copy itself every time initialized is called*************//
        private static boolean firstTime = true;

        private void addTestData()
        {
            if (!firstTime)
            {
                return;
            }
            firstTime = false;

//**************Sample Parts for Testing*************//
            InHouse a = new InHouse(1, "Brakes", 10.0, 15, 10, 1, 1);
            Inventory.addParts(a);

            InHouse b = new InHouse(2, "Wheel", 16.00, 11, 16, 1, 2);
            Inventory.addParts(b);

            InHouse c = new InHouse(3, "Seat", 10.00, 15, 10, 1, 3);
            Inventory.addParts(c);



        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
addTestData();

/****************Parts Table****************/
        PartTableView.setItems(Inventory.getAllParts());
        PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        

/***********Products Table***********/
        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /*******Implementation of the Parts Search Bar**
     * refreshes table view with results******/
    public void SearchPartBtn(ActionEvent event) {
        {
            ObservableList<Part> allParts = Inventory.getAllParts();
            ObservableList<Part> partsFound = FXCollections.observableArrayList();
            String searchString = searchPart.getText();

            for (Part part : allParts) {
                if (String.valueOf(part.getId()).contains(searchString) ||
                        part.getName().contains(searchString)) {
                    partsFound.add(part);
                }
            }

            PartTableView.setItems(partsFound);

            if (partsFound.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sorry, No Part by this Name is found");
            }
            PartTableView.refresh();
        }
    }


    /**Implementation of the Products Search Bar
     * refreshes table view with results**/
    public void SearchProductsBtn(ActionEvent event)
    {
        {
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            ObservableList<Product> productsFound = FXCollections.observableArrayList();
            String searchString = productSearch.getText();

            for (Product product : allProducts) {
                if (String.valueOf(product.getId()).contains(searchString) ||
                        product.getName().contains(searchString)) {
                    productsFound.add(product);
                }
            }

            ProductTableView.setItems(productsFound);

            if (productsFound.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sorry, No Part by this Name is found");
            }
            ProductTableView.refresh();
        }
    }



    /****Delete a Part From the Parts Table by selecting a Part and pressing the delete button in the Parts Table****/
    @FXML
    private void deletePart(ActionEvent event)
    {
        {
            if
            (PartTableView.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a part to delete");
                return;
            }
            if (confirmDialog("Delete Part?", "Would you like to delete this part?"))
            {
                Part removePart = PartTableView.getSelectionModel().getSelectedItem();
                Inventory.deletePart(removePart);
                Inventory.AllParts.remove(removePart);
            }
        }

    }


/****Delete a Product from the Product Table by selecting a product and pressing the delete button in the Products Table****/
@FXML
private void deleteProduct(ActionEvent event)
{
    if
    (ProductTableView.getSelectionModel().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please select a product to delete");
        return;
    }
    if (confirmDialog("Delete Product?", "Would you like to delete this product?"))

    {
        Product removeProduct = ProductTableView.getSelectionModel().getSelectedItem();
        if (removeProduct.getAllAssociatedParts().isEmpty()){
        Inventory.deleteProduct(removeProduct);
        Inventory.AllProducts.remove(removeProduct);
    }
        else {
            JOptionPane.showMessageDialog(null, "Products with associated parts cannot be deleted. In order to delete a product all associated parts must first be removed.");
        }
    }
}
/****Navigation to modify part screen**/
    public void ModifyPart(ActionEvent event) throws IOException {
        try {
            Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                JOptionPane.showMessageDialog(null, "Please select a part to modify");
                return;
            }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyParts.fxml"));
        loader.load();

        ModifyParts MPController = loader.getController();
        MPController.sendPart(PartTableView.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        }
        catch (IOException e)
        {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed", e);
        }
    }




    /***********Navigation to Modify Products Screen*************/

    public void ModifyProduct(ActionEvent event) throws IOException{
      try {
        Product selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(null, "Please select a product to modify");
            return;
        }
              FXMLLoader loader = new FXMLLoader();
              loader.setLocation(getClass().getResource("/view/modifyProduct.fxml"));
              loader.load();

              /***sentProduct Method will transfer contents from Main to modify screen*/
              ModifyProduct MPController = loader.getController();
              MPController.sendProduct(ProductTableView.getSelectionModel().getSelectedItem());

              Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
              Parent scene = loader.getRoot();
              stage.setScene(new Scene(scene));
              stage.show();
      }
      catch (IOException e)
      {
              Logger logger = Logger.getLogger(getClass().getName());
              logger.log(Level.SEVERE, "Failed", e);
      }
    }


/**method confirm dialog will give user a dialogue box to confirm choices throughout program*/
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Are You Sure?");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
/**info Dialogue will create an alert box for user throughout program*/
    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}








