package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddParts implements Initializable {


    @FXML
    public Button btnCancelAddPart;
    @FXML
    public Label MtoC;
    @FXML
    public RadioButton AddPartInHouseTgl;
    @FXML
    public RadioButton AddPartOutsourcedTgl;
    @FXML
    public ToggleGroup InHO;
    @FXML
    private Button btnSaveAddPart;
    @FXML
    private TextField addPartMachineId;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartInventoryLevel;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartID;

    /**
     *
     * @JessicaGreenberg
     * Student ID: #001462404
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartId();
    }

    ArrayList<Part> PartArrayList;


    /******* Generate a random ID for the Parts ID number*******/
    private void generatePartId() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);

        if (Inventory.partListSize() == 0) {
            addPartID.setText(num.toString());

        }
        if (Inventory.partListSize() == 2200)
        {
        } else {
            match = verifyIfTaken(num);

            if (match == false) {
                addPartID.setText(num.toString());
            } else {
                generatePartId();
            }
        }
    }

    private boolean verifyIfTaken(Integer num) {
        Part match = Inventory.lookupPartID(num);
        return match != null;
    }

    {
        PartArrayList = new ArrayList<Part>();
    }

    /** Set Option to Machine ID when InHouse is chosen*/
    @FXML
    public void AddPartInHouseTgl(ActionEvent event) {
        MtoC.setText("Machine ID");

    }

    // Set Option to Company Name when Outsourced is chosen//
    @FXML
    public void AddPartOutsourcedTgl(ActionEvent event) {
        MtoC.setText("Company Name");
    }

    /******Navigation to Main Screen********/
    public void CancelAddPart() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You sure?");
        alert.setContentText("Do you want to Cancel Adding a Part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));

        Stage window = (Stage) btnCancelAddPart.getScene().getWindow();
        window.setScene(new Scene(root, 900, 400));

    }}
    private boolean minValid(int min, int max)
    {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            JOptionPane.showMessageDialog(null, "Min must be less than the Max amount");
        }

        return isValid;
    }
    private boolean stockValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            JOptionPane.showMessageDialog(null, "Stock must be greater than the Min values and equal to or less than your max ");
        }

        return isValid;
    }
    @FXML
    private void btnSaveAddPartHandler(ActionEvent event) throws IOException
    { Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You sure?");
        alert.setContentText("Do you want to add this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
            try {
            int id = Integer. parseInt(addPartID.getText());
            String name = addPartName.getText();
            Double price = Double.parseDouble(addPartPrice.getText());
            int stock = Integer.parseInt(addPartInventoryLevel.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int machineId;
            String companyName;

            if (minValid(min, max) && stockValid(min, max, stock)) {

                if (AddPartInHouseTgl.isSelected()) {
                    try {
                        machineId = Integer.parseInt(addPartMachineId.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addParts(newInHousePart);
                    } catch (Exception e) {
                        MainScreen.infoDialog("Input Error","Could not add a Part","Please make sure all fields are filled out with proper values");

                    }
                }

                if (AddPartOutsourcedTgl.isSelected()) {
                    companyName = addPartMachineId.getText();
                    OutSourced newOutsourcedPart = new OutSourced(id, name, price, stock, min, max,companyName);
                    Inventory.addParts(newOutsourcedPart);
                }
                /****Confirmation Box that User has Added Part****/

                JOptionPane.showMessageDialog(null, "you've successfully added a part");

                /****After saving the data, users are automatically redirected to the Main form****/
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
         catch(Exception e) {
            System.out.print("Please enter valid values");
            System.out.print("Exception: " + e);
            JOptionPane.showMessageDialog(null, "All Text-fields must be appropriately filled out," +
                    " please check your resource and ensure accurate entries in the Name, Inventory, Price, Max, Min and either the Machine ID or Company Name field. Thank you ");
            return;
        }




    }

}







