package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyParts implements Initializable {
    public static ObservableList<Part> AllParts = FXCollections.observableArrayList();

    @FXML
    Button btnCancelModifyPart;
    @FXML
    private RadioButton inHouseTgl;
    @FXML
    private RadioButton outsourcedTgl;
    @FXML
    private Label modifyMachineId;
    @FXML
    private TextField modifyPartMachineId;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartStock;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartId;
    @FXML
    private RadioButton modifyPartOutsourcedTgl;
    @FXML
    private RadioButton ModifyinHouseTgl;

    /******Navigation to Main Screen********/
    public void CancelModifyPart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage window = (Stage) btnCancelModifyPart.getScene().getWindow();
        window.setScene(new Scene(root, 900, 400));
    }

    @FXML
    private void inHouseTgl(ActionEvent event) {
        modifyMachineId.setText("Machine Id");

    }

    @FXML
    private void outsourcedTgl(ActionEvent event) {
        modifyMachineId.setText("Company Name");
    }
    private Stage stage;
    private Object scene;



    /****Retrieve values from the table on the MainScreen****/
    public void sendPart (Part part)
    {
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartStock.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyPartMax.setText(String.valueOf(part.getMax()));

       if(part instanceof InHouse) {
           ModifyinHouseTgl.setSelected(true);
            this.modifyMachineId.setText("Machine Id");
           modifyPartMachineId.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else if (part instanceof OutSourced){
           modifyPartOutsourcedTgl.setSelected(true);
            this.modifyMachineId.setText("Company Name");
           modifyPartMachineId.setText(String.valueOf(((OutSourced) part).getCompanyName()));

    }}

    @FXML
    private void modifyPartSaveBtn(ActionEvent event) throws IOException
    {  try {
        int id = Integer.parseInt(modifyPartId.getText());
        String name = modifyPartName.getText();
        Double price = Double.parseDouble(modifyPartPrice.getText());
        int stock = Integer.parseInt(modifyPartStock.getText());
        int min = Integer.parseInt(modifyPartMin.getText());
        int max = Integer.parseInt(modifyPartMax.getText());
        int machineId;
        String companyName;

        if (minValid(min, max) && stockValid(min, max, stock)) {

            if (ModifyinHouseTgl.isSelected()) {
                try {
                    machineId = Integer.parseInt(modifyPartMachineId.getText());
                    InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(id,updatedPart);
                } catch (Exception e) {

                }
            }

            if (modifyPartOutsourcedTgl.isSelected()) {
                companyName = modifyPartMachineId.getText();
                OutSourced modifiedOutsourced = new OutSourced(id, name, price, stock, min, max,companyName);
                Inventory.updatePart(id,modifiedOutsourced);
            }
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
        }


/**RUNTIME ERROR Did not call the right Indexing
                    //Corrected by copying/pasting the call method of modify Part ID*/


            } catch (NumberFormatException e) {
                System.out.print("Please enter valid values");
                System.out.print("Exception: " + e);
                JOptionPane.showMessageDialog(null, "\" please check your resource and " +
                        "ensure accurate entries in the Name, Inventory, Price, Max, Min and either the Machine ID or " +
                        "Company Name field. Thank you \"");
            }
        }

    private boolean minValid(int min, int max)
    {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            JOptionPane.showMessageDialog(null, "Please Ensure Min entered is higher than 0 and no greater than the value of Max");
        }

        return isValid;
    }
    private boolean stockValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < max || stock > min) {
            isValid = false;
            JOptionPane.showMessageDialog(null, "Please Ensure the Inventory entered is greater than the Min and less than the Max  ");
        }

        return isValid;
    }



            @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
            {


    }
}




