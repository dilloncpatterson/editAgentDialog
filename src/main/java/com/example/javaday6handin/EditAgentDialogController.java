/**
 * Sample Skeleton for 'editagentdialog.fxml' Controller Class
 */

package com.example.javaday6handin;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditAgentDialogController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtFirstName"
    private TextField tfAgtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtMiddleInitial"
    private TextField tfAgtMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtLastName"
    private TextField tfAgtLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtBusPhone"
    private TextField tfAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtEmail"
    private TextField tfAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtPosition"
    private TextField tfAgtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    private ObservableList<Agent> mainData;

    private int selectedIndex;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtFirstName != null : "fx:id=\"tfAgtFirstName\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtMiddleInitial != null : "fx:id=\"tfAgtMiddleInitial\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtLastName != null : "fx:id=\"tfAgtLastName\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtBusPhone != null : "fx:id=\"tfAgtBusPhone\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtEmail != null : "fx:id=\"tfAgtEmail\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgtPosition != null : "fx:id=\"tfAgtPosition\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'editagentdialog.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'editagentdialog.fxml'.";

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });
    }

    private void btnSaveClicked(MouseEvent mouseEvent) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "admin", "admin");
            PreparedStatement stmt = conn.prepareStatement("UPDATE `agents` SET `AgtFirstName`=?,`AgtMiddleInitial`=?,`AgtLastName`=?,`AgtBusPhone`=?,`AgtEmail`=?,`AgtPosition`=?,`AgencyId`=? WHERE `AgentId`=?");
            stmt.setString(1, tfAgtFirstName.getText());
            stmt.setString(2, tfAgtMiddleInitial.getText());
            stmt.setString(3, tfAgtLastName.getText());
            stmt.setString(4, tfAgtBusPhone.getText());
            stmt.setString(5, tfAgtEmail.getText());
            stmt.setString(6, tfAgtPosition.getText());
            stmt.setInt(7, Integer.parseInt(tfAgencyId.getText()));
            stmt.setInt(8, Integer.parseInt(tfAgentId.getText()));
            int numRows = stmt.executeUpdate();

            if (numRows == 0)
            {
                System.out.println("update failed");
            }

            //make agent object to match the db update and update the tableview with it
            mainData.set(selectedIndex, new Agent(Integer.parseInt(tfAgentId.getText()),tfAgtFirstName.getText(), tfAgtMiddleInitial.getText(),
                    tfAgtLastName.getText(), tfAgtBusPhone.getText(), tfAgtEmail.getText(), tfAgtPosition.getText(), Integer.parseInt(tfAgencyId.getText())));

            Node source = (Node) mouseEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        }

    public void setMainObservableList(ObservableList<Agent> data) {
        this.mainData = data;
    }

    public void setMainSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;

        Agent a = mainData.get(selectedIndex);
        tfAgentId.setText(a.getAgentId() + "");
        tfAgtFirstName.setText(a.getAgtFirstName());
        tfAgtMiddleInitial.setText(a.getAgtMiddleInitial());
        tfAgtLastName.setText(a.getAgtLastName());
        tfAgtBusPhone.setText(a.getAgtBusPhone());
        tfAgtEmail.setText(a.getAgtEmail());
        tfAgtPosition.setText(a.getAgtPosition());
        tfAgencyId.setText(a.getAgencyId() + "");
    }
}



