

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scott
 */
public class Login {
    

 public void LoginScreen(){
     
 
 Stage windowL = new Stage();
 windowL.initModality(Modality.APPLICATION_MODAL);
 windowL.setOnCloseRequest(e -> Platform.exit());

 StackPane root = new StackPane();
 VBox vbox = new  VBox(10);
 vbox.setAlignment(Pos.CENTER);
 HBox hbox = new HBox(10);
 hbox.setAlignment(Pos.CENTER);
 VBox vbox2 = new  VBox(15);
 vbox.setAlignment(Pos.CENTER);
 VBox vbox3 = new  VBox(10);
 vbox.setAlignment(Pos.CENTER);
 
 Label title = new Label("Login");
 Label usernameL = new Label("Username:");
 Label passwordL = new Label("Password:");
 
 TextField username = new TextField();
 username.setPromptText("Enter Username");
 PasswordField password = new PasswordField();
 password.setPromptText("Enter Password");
 
 Button login = new Button("Login");
 login.setDefaultButton(true);
 login.setOnAction(e -> {
     
     if(username.getText().equals("Admin") 
             && password.getText().equals("password")){
       
        windowL.close();
        
     }
     
     else
             {
         Alert loginBox = new Alert(Alert.AlertType.WARNING);
         loginBox.setTitle("Login Failed");
         loginBox.setHeaderText(null);
         loginBox.setContentText("Username or Password is incorrect!"+"\n"
                 + "Please try again");

           loginBox.showAndWait();
             }
 });
 
 Image back = new Image("payroll.jpg");
 ImageView imageView = new ImageView(back);

 vbox3.getChildren().addAll(username,password);
 vbox2.getChildren().addAll(usernameL,passwordL);
 hbox.getChildren().addAll(vbox2,vbox3);
 vbox.getChildren().addAll(title,hbox,login);
 root.getChildren().addAll(imageView,vbox);
 Scene scene = new Scene(root);
 windowL.setScene(scene);
 scene.getStylesheets().add("Login.css");
 windowL.setTitle("Login");
 
 windowL.showAndWait();
 

    
}
}