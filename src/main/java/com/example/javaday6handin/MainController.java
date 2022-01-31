/**
 * Sample Skeleton for 'main-view.fxml' Controller Class
 */

package com.example.javaday6handin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentId"
    private TableColumn<Agent, Integer> colAgentId; // Value injected by FXMLLoader
                                                                              ///////////////************
    private ObservableList<Agent> data = FXCollections.observableArrayList();

    @FXML // fx:id="colAgtFirstName"
    private TableColumn<Agent, String> colAgtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtMiddleInitial"
    private TableColumn<Agent, String> colAgtMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtLastName"
    private TableColumn<Agent, String> colAgtLastName; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtBusPhone"
    private TableColumn<Agent, String> colAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtEmail"
    private TableColumn<Agent, String> colAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtPosition"
    private TableColumn<Agent, String> colAgtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyId"
    private TableColumn<Agent, Integer> colAgencyId; // Value injected by FXMLLoader




    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtFirstName != null : "fx:id=\"colAgtFirstName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtMiddleInitial != null : "fx:id=\"colAgtMiddleInitial\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtLastName != null : "fx:id=\"colAgtLastName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtBusPhone != null : "fx:id=\"colAgtBusPhone\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtEmail != null : "fx:id=\"colAgtEmail\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgtPosition != null : "fx:id=\"colAgtPosition\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'main-view.fxml'.";

        getAgents();

        tvAgents.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    onOpenDialog(tvAgents.getSelectionModel().getSelectedIndex());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    private void onOpenDialog(int selectedIndex) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editagentdialog.fxml"));
        Parent parent = fxmlLoader.load();
        EditAgentDialogController dialogController = fxmlLoader.<EditAgentDialogController>getController();
        dialogController.setMainObservableList(data);
        dialogController.setMainSelectedIndex(selectedIndex);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

             }
        });
    }

    private void getAgents() {
        String username = "";
        String password = "";
        String url = "";
        try {
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties p = new Properties();
            p.load(fis);
            username = (String) p.get("user");
            password = (String) p.get("password");
            url = (String) p.get("URL");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Class.forName("org.mariadb.jdbc.Driver");    //no longer necessary
        //Class.forName("come.mysql.cj.jdbc.Driver");    //no longer necessary
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "admin", "admin");
            //Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs =stmt.executeQuery("select * from agents");
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnCount());
            while (rs.next())
            {
                data.add(new Agent(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8)));
                colAgentId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agentId"));
                colAgtFirstName.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtFirstName")));
                colAgtMiddleInitial.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtMiddleInitial")));
                colAgtLastName.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtLastName")));
                colAgtBusPhone.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtBusPhone")));
                colAgtEmail.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtEmail")));
                colAgtPosition.setCellValueFactory((new PropertyValueFactory<Agent, String>("agtPosition")));
                colAgencyId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agencyId"));
                tvAgents.setItems(data);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
