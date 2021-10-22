
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUI extends Application
{
    
    public static void main (String[] args) {
     
        launch(args);
       
    }
    
    //ArrayList to fill with employee Objects
    List<Employee> employees = new ArrayList<>();
    
    //ArrayList to fill with poition Objects
    List<Position> position = new ArrayList<>();
    
    
    DecimalFormat df,df2;
    
    //Variables....
   
    //Stage
    Stage window,window2,window3,windowP,windowPE;
 
    //Scene
    Scene scene, scene2,sceneP,scenePE;
 
    //Layout1
    BorderPane mainLayout = new BorderPane();
    VBox tableView = new VBox();
    HBox addRemove = new HBox();
    VBox rightC = new VBox(20);
    GridPane updateE = new GridPane();
    
    //layout2
    BorderPane secondLayout = new BorderPane();
    HBox top = new HBox(30);
    GridPane center = new GridPane();
    
    //layoutP
    VBox root = new VBox(10);
    HBox layoutP = new HBox();
    VBox layoutP2 = new VBox(10);
    VBox layoutP3 = new VBox(10);
    
    //layoutPE
    VBox rootPE = new VBox(10);
    HBox layoutPE = new HBox();
    HBox buttons = new HBox(20);
    VBox layoutPE2 = new VBox(10);
    VBox layoutPE3 = new VBox(10);
    
    //Menus
    Menu file,manageP,payroll;
    MenuItem login,exit,regP,updateP,payments;
    MenuBar menu;
    
    //Table
    TableView<Employee> table;   
    
    //Labels
    Label name,surname,niNo,positionHeld,salary,positionBoxL,edit;
            
    Label nameR, positionR, niNoR,taxCode,lAnual,lMonthly,lWeekly,gross,niD,taxD
            ,totalD,net;
    
    Label pos, salaryP,title;
    
    Label posPE, salaryPE, titlePE;
    
    //Textfields
    TextField txtName,txtSurname,txtniNo,txtPositionHeld,txtSalary,
            txtName2,txtSurname2,txtniNo2,txtPositionHeld2,txtSalary2;
    
    TextField aGross,mGross,wGross,aNiD,mNiD,wNiD,aTax,mTax,wTax,aTd,mTd,wTd,
            aNet,mNet,wNet,txtTaxcode;
    
    TextField txtPos, txtSalaryP;
    
    TextField txtPosPE, txtSalaryPE;
    
    //Combo box 
    ComboBox<String> positionBox,positionBox2,posBoxPE;;
    
    //Buttons
    Button registerBtn,deleteBtn,updateBtn,editBtn,calculateBtn,backBtn,
            registerP,updatePE, deletePE,runPayroll,backEmp;
    
    

    @Override
    public void start(Stage primaryStage) throws Exception
           
    {
      
        // calling the LoginScreen method from the Login calss
       Login login = new Login();
       login.LoginScreen();
       
        /*Calling the method I created to populate the ArrayLists
       from a file. */    
       populateEmp(); 
       populatePos();
       
       //----------- BUTTONS ACTIONS -----------
       
       
       registerBtn = new Button("Register Employee");
       registerBtn.setStyle
        (" -fx-background-color: linear-gradient(#80ff80,#006600)");
       registerBtn.setOnAction(e -> 
               //calling method 
               registerBtnClicked());
               
       deleteBtn = new Button("Delete Employee");
       deleteBtn.setStyle
        (" -fx-background-color: linear-gradient(#ffb3b3,#cc0000)");
       deleteBtn.setOnAction(e ->  DeleteEmployeeFromFile());   
        
        
       updateBtn = new Button("Update Details");
       updateBtn.setStyle
        (" -fx-background-color: linear-gradient(#66a3ff,#0000b3)");
       updateBtn.setOnAction(e ->{
             //Calling Update Method 
              update();
       });
       
       editBtn = new Button("Edit");
       editBtn.setStyle
        (" -fx-background-color: linear-gradient(#66a3ff,#0000b3)");
       editBtn.setOnAction(e ->{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit");
        alert.setHeaderText("Edit National Insuance Number");
        alert.setContentText("National Insurnace numbers do not change and "
                + "should only be edited if it was entered incorrectly at "
                + "registration. Are you sure you want to edit?");
        
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){   
                txtniNo2.setDisable(false);      
       }
       });
       
       
       calculateBtn = new Button("Calculate Salary");
       calculateBtn.setStyle
        (" -fx-background-color: linear-gradient(#ff80ff,#660066)");
       calculateBtn.setOnAction(e -> ReportButton());
       
       backBtn = new Button("Back to Employees");
       backBtn.setOnAction(e -> window2.close());
       
       // Running method to set the layouts for the mainScreen
       MainPage();
       
       // Stages 
       window = primaryStage;
       window.setTitle("Manage Employee");
       
       window2 = new Stage();
       window2.setTitle("Employee Report");
       //stopping other activity until the user has delt with this window
       window2.initModality(Modality.APPLICATION_MODAL);
       
       window3 = new Stage();
       window3.setTitle("Payroll");
       //stopping other activity until the user has delt with this window
       window3.initModality(Modality.APPLICATION_MODAL);
        
       windowP = new Stage();
       windowP.setTitle("Create Position");
       windowP.initModality(Modality.APPLICATION_MODAL);
       
       windowPE = new Stage();
       windowPE.setTitle("Edit Position ");
       windowPE.initModality(Modality.APPLICATION_MODAL);
       
       //setting scene
       scene = new Scene(mainLayout);
       scene.getStylesheets().add("Style.css");
       scene2 = new Scene(secondLayout);
       scene2.getStylesheets().add("Report.css");
       sceneP = new Scene(root,400,250);
       sceneP.getStylesheets().add("Position.css");
       scenePE = new Scene(rootPE,400,250);
       scenePE.getStylesheets().add("Position.css");
       
       //adding the scene to the stage and showing it 
       window.setScene(scene);
       window.show();
       
       
    }
   
    // --------------------- Main page method ---------------------------- 
     public void MainPage (){
        
      //MENU BAR.....
     
       //file menu 
       file = new Menu("File");
       
       //adding Menu items to the the file menu
       login = new MenuItem("Login...");
       login.setAccelerator(new KeyCodeCombination(KeyCode.L, 
               KeyCombination.CONTROL_DOWN));
       login.setOnAction(e ->  {
         Login login = new Login();
         login.LoginScreen();
       });
       file.getItems().add(login);
       

       
       //Line to seperate the login and exit menu items. 
       file.getItems().add(new SeparatorMenuItem());
       
       exit = new MenuItem("Exit");
       exit.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE,
               KeyCombination.CONTROL_DOWN));
       exit.setOnAction(e -> window.close());
       file.getItems().add(exit);
        
       
       //Manage Position Menu
       manageP = new Menu("Position");
       
       //adding Menu items to the Position Menu
       regP = new MenuItem("Register Position...");
       regP.setAccelerator(new KeyCodeCombination(KeyCode.R,
               KeyCombination.CONTROL_DOWN));
       regP.setOnAction(e -> regPosition());

       manageP.getItems().add(regP);
       //Line to seperate the register and edit menu items. 
       manageP.getItems().add(new SeparatorMenuItem());
        
       updateP = new MenuItem("Edit Position...");
       updateP.setAccelerator(new KeyCodeCombination(KeyCode.E,
               KeyCombination.CONTROL_DOWN));
       updateP.setOnAction(e ->  editPosition());
       manageP.getItems().add(updateP);
       
       //payroll menu
       payroll = new Menu("Payroll");
       
       //Line to seperate the reports and payments menu items. 
       payroll.getItems().add(new SeparatorMenuItem());
       
       payments = new MenuItem("Payslips...");
       payments.setAccelerator(new KeyCodeCombination(KeyCode.P,
               KeyCombination.CONTROL_DOWN));
       payments.setOnAction(e -> {
           
         Payroll pay1 = new Payroll();
         pay1.payroll();
               
       });
       payroll.getItems().add(payments);
       
       // MenuBar   
       menu = new MenuBar(); 
       // Adding Menu's to the menu bar. 
       menu.getMenus().addAll(file,manageP,payroll);   
        
     
       //LABEL
       name = new Label("Name: ");
       surname = new Label("Surname: ");
       niNo = new Label("NI Number: ");
       positionHeld = new Label("Position: ");
       salary = new Label ("Salary: ");
       positionBoxL = new Label ("Positions: ");
       edit = new Label ("Edit Employee details");
       edit.setFont(Font.font("Ariel",FontWeight.BOLD,20));
       edit.setAlignment(Pos.CENTER);
       
       
       //TEXTFIELDS 
       txtName = new TextField();
       txtName.setPromptText("Enter Name");
       //Using a listener to make sure only letters are entered 
       txtName.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtSurname = new TextField();
       txtSurname.setPromptText("Enter Surname");
       txtSurname.textProperty().addListener(
               (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtSurname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtniNo = new TextField();
       txtniNo.setPromptText("Enter NI Number");
  
       txtPositionHeld = new TextField();
       txtPositionHeld.setPromptText("Enter Position");
       txtPositionHeld.textProperty().addListener(
        (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtPositionHeld.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtSalary = new TextField();
       txtSalary.setPromptText("Enter Salary");
       //Using a listener to make sure only numbers are entered 
       txtSalary.textProperty().addListener((observable, oldValue, newValue) ->{
        if (!newValue.matches("\\d*")) {
            txtSalary.setText(newValue.replaceAll("[^\\d.]", ""));
        }
    });
       
       txtName2 = new TextField();
       txtName2.setPromptText("Enter Name");
       txtName2.setDisable(true);
       txtName2.textProperty().addListener((observable, oldValue, newValue) ->{
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtName2.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtSurname2 = new TextField();
       txtSurname2.setPromptText("Enter Surname");
       txtSurname2.setDisable(true);
       txtSurname2.textProperty().addListener(
               (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtSurname2.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtniNo2 = new TextField();
       txtniNo2.setPromptText("Enter NI Number");
       txtniNo2.setDisable(true);
       
       txtPositionHeld2 = new TextField();
       txtPositionHeld2.setPromptText("Enter Position");
       txtPositionHeld2.setDisable(true);
       txtPositionHeld2.textProperty().addListener(
               (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtPositionHeld2.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
       
       txtSalary2 = new TextField();
       txtSalary2.setPromptText("Enter Salary");
       txtSalary2.setDisable(true);
       txtSalary2.textProperty().addListener(
               (observable, oldValue, newValue) ->{
        if (!newValue.matches("\\d*")) {
            txtSalary2.setText(newValue.replaceAll("[^\\d.]", ""));
        }
    });
    
     
     /* Making the columns for the table and tell them where to get the
     information to fill them
    */   

       TableColumn<Employee,String> surnameColumn = new TableColumn<>("Surname");
       surnameColumn.setMinWidth(200);
       surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
       surnameColumn.setSortable(false);
     
       TableColumn<Employee,String> nameColumn = new TableColumn<>("Name");
       nameColumn.setMinWidth(200);
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       nameColumn.setSortable(false);
     
       TableColumn<Employee,String> niColumn = new TableColumn<>("NI Number");
       niColumn.setMinWidth(200);
       niColumn.setCellValueFactory(new PropertyValueFactory<>("niNo"));
       niColumn.setSortable(false);
     
       TableColumn<Employee,String> posColumn = new TableColumn<>("Position");
       posColumn.setMinWidth(200);
       posColumn.setCellValueFactory(new PropertyValueFactory<>("Position"));
       posColumn.setSortable(false);
     
       TableColumn<Employee,Double> salColumn = new TableColumn<>("Salary");
       salColumn.setMinWidth(200);
       salColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
       salColumn.setSortable(false);
     
       //ComboBoxs
       positionBox = new ComboBox<>();
       positionBox2 = new ComboBox<>();
       
       //Populating the comboBox with the position array   
       String [] positionArray = new String[position.size()];
      
       for (int i =0; i<position.size(); i++)
       positionArray[i] = position.get(i).getPosHeld();
       
       positionBox.getItems().addAll(positionArray);
       positionBox2.getItems().addAll(positionArray);
       
       //fill the textfields when a selection is made from the ComboBox
       positionBox.setOnAction(e -> {
          
       int selectedIndex = positionBox.getSelectionModel().getSelectedIndex();
       
       txtPositionHeld.setText(position.get(selectedIndex).getPosHeld());
       txtSalary.setText(position.get(selectedIndex).getSalary()+"");
       });
       
       //fill the textfields when a selection is made from the ComboBox
       positionBox2.setOnAction(e -> {
          
       int selectedIndex = positionBox2.getSelectionModel().getSelectedIndex();
       
       txtPositionHeld2.setText(position.get(selectedIndex).getPosHeld());
       txtSalary2.setText(position.get(selectedIndex).getSalary()+"");
       });
       
       //Table 
       table = new TableView<>();
       // adding List to the table
       table.getItems().addAll(employees);
     
       // Adding colums to  the table
       table.getColumns().addAll(surnameColumn,nameColumn,niColumn,posColumn,
             salColumn);
     
       //fill the textfields when a selection is made from the ComboBox
       table.setOnMouseClicked(e -> {
      
       txtName2.setDisable(false);
       txtSurname2.setDisable(false);
       txtPositionHeld2.setDisable(false);
       txtSalary2.setDisable(false);
       
       try{    
       // getting selected Object from the list in the table
       int selectedIndex = table.getSelectionModel().getSelectedIndex();
       
       //setting Textfield with the selected Objects corrospoding values
       txtName2.setText(employees.get(selectedIndex).getName());
       txtSurname2.setText(employees.get(selectedIndex).getSurname());
       txtniNo2.setText(employees.get(selectedIndex).getNiNo());
       txtPositionHeld2.setText(employees.get(selectedIndex).getPosition());
       
       //Coverting double to String to then put in the TextField
       double sal = employees.get(selectedIndex).getSalary();
       String salpass =String.valueOf(sal);
       txtSalary2.setText(salpass);
       
     }
       catch(Exception f){
         
     }
       });
     
      // setting padding and spacing for VBox(addRemove)
      addRemove.setPadding(new Insets(10,10,10,10));
      addRemove.setSpacing(10);
     
      addRemove.getChildren().addAll(txtName,txtSurname,
               txtniNo,positionBox,txtPositionHeld,txtSalary,registerBtn
             ,deleteBtn);
     
      //adding table to the layout
      tableView.getChildren().addAll(table,addRemove);
     
       //------------ MainPage Right ----------
      //Gridpane layout to go in the borderPane layout
      updateE.setPadding(new Insets(10,10,10,10));
      updateE.setVgap(8);
      updateE.setHgap(10);
     
       
       //positioning the Labels, textfields and buttons on the gridpane
       GridPane.setConstraints(name,0,3);
       GridPane.setConstraints(txtName2,1,3);
       GridPane.setConstraints(surname,0,4);
       GridPane.setConstraints(txtSurname2,1,4);
       GridPane.setConstraints(niNo,0,5);
       GridPane.setConstraints(txtniNo2,1,5);
       GridPane.setConstraints(editBtn,2,5);
       GridPane.setConstraints(positionBoxL,0,7);
       GridPane.setConstraints(positionBox2,1,7);  
       GridPane.setConstraints(positionHeld,0,8);
       GridPane.setConstraints(txtPositionHeld2,1,8);
       GridPane.setConstraints(salary,0,9);
       GridPane.setConstraints(txtSalary2,1,9);
       GridPane.setConstraints(updateBtn,1,10);
       
       
       
       //adding all labels, textfields and button to GridPane
       updateE.getChildren().addAll(name,txtName2,surname
               , txtSurname2,niNo,editBtn,txtniNo2,positionBoxL,positionBox2,
               positionHeld,txtPositionHeld2,salary,
               txtSalary2,updateBtn);  
      
       
       //setting menu 
       mainLayout.setTop(menu);
       
       //adding the VBox to the menuPane
       mainLayout.setCenter(tableView);
      
       //adding the GridPane to the menuPane
       mainLayout.setRight(rightC);
       rightC.setAlignment(Pos.TOP_CENTER);
       rightC.getChildren().addAll(edit,updateE,calculateBtn);
      
    }
     
     
    //---------------------- reportPage Method ------------------------
     
     public void ReportPage(){
         
        top.getChildren().clear();
        center.getChildren().clear();
        secondLayout.getChildren().clear();

        //Labels
        nameR = new Label("");
        nameR.getStyleClass().add("label-title");
        positionR = new Label("");
        niNoR = new Label("");
        niNoR.getStyleClass().add("label-title");
        taxCode = new Label("");
        taxCode.getStyleClass().add("label-title");
        lAnual = new Label("Anually");
        lMonthly = new Label("Monthly");
        lWeekly = new Label("Weekly");
        gross = new Label("Gross pay: ");
        niD = new Label("NI Deduction: ");
        taxD = new Label("Tax Deduction: ");
        totalD = new Label("Total Deductions: ");
        net = new Label("Net Pay: ");

       //TextFields
        aGross = new TextField();
        aGross.setStyle("-fx-text-fill:black");
        mGross = new TextField();
        mGross.setStyle("-fx-text-fill:black");
        wGross = new TextField();
        wGross.setStyle("-fx-text-fill:black");
        aNiD = new TextField();
        mNiD = new TextField();
        wNiD = new TextField();
        aTax = new TextField();
        mTax = new TextField();
        wTax = new TextField();
        aTd = new TextField();
        mTd = new TextField();
        wTd = new TextField();
        aNet = new TextField();
        aNet.setStyle("-fx-text-fill:black");
        mNet = new TextField();
        mNet.setStyle("-fx-text-fill:black");
        wNet = new TextField();
        wNet.setStyle("-fx-text-fill:black");
        
        // disabling textFields
        aGross.setDisable(true);
        mGross.setDisable(true);
        wGross.setDisable(true);
        aNiD.setDisable(true);
        mNiD.setDisable(true);
        wNiD.setDisable(true);
        aTax.setDisable(true);
        mTax.setDisable(true);
        wTax.setDisable(true);
        aTd.setDisable(true);
        mTd.setDisable(true);
        wTd.setDisable(true);
        aNet.setDisable(true);
        mNet.setDisable(true);
        wNet.setDisable(true);
      
       top.setAlignment(Pos.CENTER);
       top.getChildren().addAll(nameR,niNoR,taxCode);
      
       //Gridpane to go in the center of borderPane 
       center.setPadding(new Insets(10,10,10,10));
       center.setVgap(8);
       center.setHgap(10);
     
       //positioning the Labels, textfields and buttons on the gridpane
       GridPane.setConstraints(lWeekly,1,0);
       GridPane.setConstraints(lMonthly,2,0);
       GridPane.setConstraints(lAnual,3,0);
       GridPane.setConstraints(gross,0,1);
       GridPane.setConstraints(niD,0,2);
       GridPane.setConstraints(taxD,0,3);
       GridPane.setConstraints(totalD,0,4);
       GridPane.setConstraints(net,0,5);
       GridPane.setConstraints(wGross,1,1);  
       GridPane.setConstraints(wNiD,1,2);
       GridPane.setConstraints(wTax,1,3);
       GridPane.setConstraints(wTd,1,4);
       GridPane.setConstraints(wNet,1,5);
       GridPane.setConstraints(mGross,2,1);  
       GridPane.setConstraints(mNiD,2,2);
       GridPane.setConstraints(mTax,2,3);
       GridPane.setConstraints(mTd,2,4);
       GridPane.setConstraints(mNet,2,5);
       GridPane.setConstraints(aGross,3,1);  
       GridPane.setConstraints(aNiD,3,2);
       GridPane.setConstraints(aTax,3,3);
       GridPane.setConstraints(aTd,3,4);
       GridPane.setConstraints(aNet,3,5);
       GridPane.setConstraints(backBtn,2,6);
       
       
       //adding all labels, textfields and button to GridPane
       center.getChildren().addAll(lWeekly,lMonthly,lAnual,gross,niD,taxD,
               totalD,net,wGross,wNiD,wTax,wTd,wNet,mGross,mNiD,mTax,mTd,mNet,
               aGross,aNiD,aTax,aTd,aNet,backBtn);  
      
        secondLayout.setTop(top);  
        secondLayout.setCenter(center);
     
        window2.setScene(scene2);
    
}
     // Method to run calculate pay when report buttin is pressed. 
     public void ReportButton(){
         
       //checking an employee has been selected
        if (txtniNo2.getText().isEmpty()){
           
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Report");
        alert.setHeaderText("No Employee selected!");
        alert.setContentText("Selecte an employee to calculate pay for!");
        alert.showAndWait();
      
        }   
        else {
          
          ReportPage();
          
          //setting the formate for the value sent accross to salary
          df = new DecimalFormat("#"); 
          df2 = new DecimalFormat("#,###.00"); 
          
          int selectedIndex = table.getSelectionModel().getSelectedIndex();
       
          //setting Lables Textfield with the selection made from the table 
          nameR.setText(employees.get(selectedIndex).getName()+" "+
                  employees.get(selectedIndex).getSurname());
          
          positionR.setText(employees.get(selectedIndex).getPosition());
          
          niNoR.setText(employees.get(selectedIndex).getNiNo());
          
          //Coverting double to String to then put in the TextField
          double sal = employees.get(selectedIndex).getSalary();
          String salpass = String.valueOf(df2.format(sal));
          aGross.setText("£"+salpass);   
          
          // Calculating the correct tax code and displaying it
          double taxcode;
          double ni;
          double tax;
          //taking salary pass in and taking away 100k the divided by 2
          double taxcal = (sal - 100000) / 2;
          //taking the taxcal off of 11850 to the the higherTax code
          double higherTax = 11850 - taxcal;
          
          /* if salary passed in from array is less than or equla too 100k
          then set taxCode as 1185L
          */
             if (sal<= 100000){
                 
              taxcode = 1185;
              taxCode.setText(df.format(taxcode)+"L");
          }
             
             else{
                 /* if the highTax is more than 0 display amount followed by
                 the letter T
               */
                if (higherTax > 0){
                    
                   /*putting double in textfiled with a format followed by 
                    letter T
                    */ 
                   
                   taxCode.setText(df.format(higherTax)+"T");
                }
                // else display 0T
                else {
                    higherTax = 0;
                    taxCode.setText(df.format(higherTax)+"T");
                }
          }
          //Coverting double to String to then put in the TextField
          double salM = sal/12;
          String salMpass = String.valueOf(df2.format(salM));
          mGross.setText("£"+salMpass);
             
          //Coverting double to String to then put in the TextField
          double salW =sal/52;
          String salWpass = String.valueOf(df2.format(salW));
          wGross.setText("£"+salWpass);   
          
          //calculating National Insurance
          if (sal>8423 && sal<46385){
            
           ni = (sal - 8424) *0.12; 
          }
          else if (sal>46384){
              
           double lowerni = (37960) *0.12; 
           double higherni =(sal - 46384) *0.02;
           
           ni = lowerni + higherni;
          }
          else
          {
           ni = 0;
          }
          //Coverting double to String to then put in the TextField for NI
          String niApass = String.valueOf(df2.format(ni));
          aNiD.setText("£"+niApass);
          
          double niM = ni/12;
          String niMpass = String.valueOf(df2.format(niM));
          mNiD.setText("£"+niMpass);
          
          double niW = ni/52;
          String niWpass = String.valueOf(df2.format(niW));
          wNiD.setText("£"+niWpass);
          
          //calculating Tax
          if (sal>11850 && sal<46351){
            
           tax = (sal - 11850) *0.20; 
          }
          else if (sal>46350 && sal<100001){
              
           double lowertax = (34500) * 0.20; 
           double highertax =(sal - 46350) *0.40;
           tax = lowertax + highertax;
          }
           else if (sal>100000 && sal<123701){
              
           double lowertax = (34500) * 0.20; 
           double highertax =(sal -34500-higherTax) *0.40;
           tax = lowertax + highertax;
          }
          
          else if(sal>123700 && sal<150001){
              
           double lowertax = (34500) *0.20; 
           double highertax =(sal - 34500) *0.40;
           tax = lowertax + highertax;
          }
          
          else if (sal>150000){
              
           double lowertax = (34500) *0.20; 
           double highertax = (sal - 34500) *0.40;
           double toptax = (sal - 150000) * 0.05;
           tax = lowertax + highertax + toptax;
              
          }
          else
          {
           tax = 0;
          }
          
          //Coverting double to String to then put in the TextField for Tax
          String taxApass = String.valueOf(df2.format(tax));
          aTax.setText("£"+taxApass);
          
          double taxM = tax/12;
          String taxMpass = String.valueOf(df2.format(taxM));
          mTax.setText("£"+taxMpass);
          
          double taxW = tax/52;
          String taxWpass = String.valueOf(df2.format(taxW));
          wTax.setText("£"+taxWpass);
          
          /*Coverting double to String to then put in the TextField for
          total deductions*/
          
          double aTD = tax +ni;
          String aTDpass = String.valueOf(df2.format(aTD));
          aTd.setText("£"+aTDpass);
          
          double mTD = taxM +niM;
          String mTDpass = String.valueOf(df2.format(mTD));
          mTd.setText("£"+mTDpass);
          
          double wTD = taxW +niW;
          String wTDpass = String.valueOf(df2.format(wTD));
          wTd.setText("£"+wTDpass);
          
          // Final Pay 
          double aPay = sal -aTD;
          String aPaypass = String.valueOf(df2.format(aPay));
          aNet.setText("£"+aPaypass);
          
          double mPay = salM -mTD;
          String mPaypass = String.valueOf(df2.format(mPay));
          mNet.setText("£"+mPaypass);
          
          double wPay = salW -wTD;
          String wPaypass = String.valueOf(df2.format(wPay));
          wNet.setText("£"+wPaypass);
          
            window2.show();
          }
     }
     
     // ------------------------Register Position-----------------------------
       public void regPosition(){
           
       root.getChildren().clear();
       layoutP.getChildren().clear();
       layoutP2.getChildren().clear();
       layoutP3.getChildren().clear();
       
       pos = new Label("Position:");
       salaryP = new Label("Salary:");
       title = new Label("Create Position");
       title.setStyle("-fx-font-size:18pt;");
       title.setAlignment(Pos.CENTER);
       
       txtPos = new TextField();
       txtPos.setPromptText("Enter Position");
       txtPos.setMinWidth(255);
       txtPos.textProperty().addListener((observable, oldValue, newValue) ->{
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtPos.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
 
       txtSalaryP = new TextField();
       txtSalaryP.setPromptText("Enter Salary");
       txtSalaryP.setMinWidth(255);
       txtSalaryP.textProperty().addListener(
               (observable, oldValue, newValue) ->{
        if (!newValue.matches("\\d*")) {
            txtSalaryP.setText(newValue.replaceAll("[^\\d.]", ""));
        }
    });
       
       registerP = new Button("Create Position");
       registerP.setStyle
        (" -fx-background-color: linear-gradient(#80ff80,#006600)");
       registerP.setOnAction(e -> addPosition());
       
       root.getChildren().addAll(title,layoutP,registerP);
       root.setAlignment(Pos.CENTER);
       
       layoutP.getChildren().addAll(layoutP2,layoutP3);
       
       layoutP2.getChildren().addAll(pos,salaryP);
       layoutP2.setPadding(new Insets(10));
       layoutP2.setAlignment(Pos.CENTER);
       layoutP3.getChildren().addAll(txtPos,txtSalaryP);
       layoutP3.setPadding(new Insets(10));
       layoutP3.setAlignment(Pos.CENTER);
       
       windowP.setScene(sceneP);
       windowP.showAndWait();
    }
     
       //------------------EDIT Position-------------------------------------
      public void editPosition (){
    
          
       rootPE.getChildren().clear();
       layoutPE.getChildren().clear();
       layoutPE2.getChildren().clear();
       layoutPE3.getChildren().clear();  
       buttons.getChildren().clear();
          
       posBoxPE = new ComboBox<>();
     
       //Populating the comboBox with the position array   
       String [] positionArray = new String[position.size()];
      
       for (int i =0; i<position.size(); i++)
       positionArray[i] = position.get(i).getPosHeld();
       
       posBoxPE.getItems().addAll(positionArray);
       
       
       //fill the textfields when a selection is made from the ComboBox
       posBoxPE.setOnAction(e -> {
           
       int selectedIndex = posBoxPE.getSelectionModel().getSelectedIndex();
       
       txtPosPE.setText(position.get(selectedIndex).getPosHeld());
       txtSalaryPE.setText(position.get(selectedIndex).getSalary()+"");
       });
       
       titlePE = new Label("Edit Position");
       titlePE.setStyle("-fx-font-size:14pt;");
       titlePE.setAlignment(Pos.CENTER);
       posPE = new Label("Position:");
       salaryPE = new Label("Salary:");
  
       txtPosPE = new TextField();
       txtPosPE.setPromptText("Edit Position");
       txtPosPE.setMinWidth(255);
       txtPosPE.textProperty().addListener((observable, oldValue, newValue) ->{
        if (!newValue.matches("\\sa-zA-Z*")) {
            txtPosPE.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
 
       txtSalaryPE = new TextField();
       txtSalaryPE.setPromptText("Edit Salary");
       txtSalaryPE.setMinWidth(255);
       txtSalaryPE.textProperty().addListener(
               (observable, oldValue, newValue) ->{
        if (!newValue.matches("\\d*")) {
            txtSalaryPE.setText(newValue.replaceAll("[^\\d.]", ""));
        }
    });
       
       updatePE = new Button("Update Details");
       updatePE.setStyle
        (" -fx-background-color: linear-gradient(#66a3ff,#0000b3)");
       updatePE.setOnAction(e -> Update2());
       
       deletePE = new Button("Delete Position");
       deletePE.setStyle
        (" -fx-background-color: linear-gradient(#ffb3b3,#cc0000)");
       deletePE.setOnAction(e -> Delete());
       
    
       rootPE.getChildren().addAll(titlePE,posBoxPE,layoutPE,buttons);
       rootPE.setAlignment(Pos.CENTER);
       
       layoutPE.getChildren().addAll(layoutPE2,layoutPE3);
       
       layoutPE2.getChildren().addAll(posPE,salaryPE);
       layoutPE2.setPadding(new Insets(10));
       layoutPE2.setAlignment(Pos.CENTER);
       
       layoutPE3.getChildren().addAll(txtPosPE,txtSalaryPE);
       layoutPE3.setPadding(new Insets(10));
       layoutPE3.setAlignment(Pos.CENTER);
       
       buttons.getChildren().addAll(updatePE,deletePE);
       buttons.setAlignment(Pos.CENTER);
      
       windowPE.setScene(scenePE);
       windowPE.showAndWait();
}
    
    // method for the action when the registerBtn is clicked. 
     public void registerBtnClicked(){
          /* This varibale is taking its value from the txtniNo.
           I am then going to use this to check what the user has entered
           matches the National Inusrance number format used by the UK goverment
           */
           String nitest = txtniNo.getText();
           
        //Checking all fields have been filled before running the save method.
        if (txtName.getText().isEmpty() || txtSurname.getText().isEmpty()||
            txtniNo.getText().isEmpty())
        {
        //Alert to let the user know all fields have not been filled. 
            new Alert(Alert.AlertType.WARNING, "Please enter all fields")
                 .showAndWait();
            
            /* this nested If statement is checking the NI number entered 
            matches the correct format*/
            if (!nitest.matches("^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)"
                    + "(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])"
                    + "(?:\\s*\\d\\s*){6}([A-D]|\\s)$"))
            {
                
              //Alert to let the user know NI number format is wrong. 
            new Alert(Alert.AlertType.WARNING, "NI number format is incorrect")
                 .showAndWait();
            }
        }
        else
        {
            /* this nested If statement is checking the NI number entered 
            matches the correct format*/
            if (!nitest.matches("^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)"
                    + "(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])"
                    + "(?:\\s*\\d\\s*){6}([A-D]|\\s)$"))
            {
              //Alert to let the user know NI number format is wrong.  
            new Alert(Alert.AlertType.WARNING, "NI number format is incorrect")
                 .showAndWait();
            }
            else 
            {
                
     /* here I am taking the information entered by the user in the text fields
     * and adding them to the List. 
       Also I am taking a position from the positionBox. 
     */
          String name = txtName.getText().trim();
          String surname = txtSurname.getText().trim();
          String niNo = txtniNo.getText().trim();
          String pos = txtPositionHeld.getText().trim();
          double sal = Double.parseDouble(txtSalary.getText());
          
         Employee employee = new Employee(name, surname, niNo, pos,sal);
         employees.add(employee);
         
           //Calling method to save new employee to file.
            saveEmployeeData();
          
            //REFRESHING the page
                Refresh();
            }     
        }
     }
     
      //------------------ Update Method -------------------
    
    public void update()
    {
        /* This varibale is taking its value from the txtniNo.
           I am then going to use this to check what the user has entered
           matches the National Inusrance number format used by the UK goverment
           */
           String nitest = txtniNo2.getText();
       
        //Checking all fields have been filled before running the save method.
        if (txtName2.getText().isEmpty() || txtSurname2.getText().isEmpty()||
            txtniNo2.getText().isEmpty() || txtPositionHeld2.getText().isEmpty() 
                   || txtSalary2.getText().isEmpty())
                    
        {
        //Alert to let the user know all fields have not been filled. 
            new Alert(Alert.AlertType.WARNING, "Please enter all fields")
                 .showAndWait();
            
             /* this nested If statement is checking the NI number entered 
            matches the correct format*/
            if (!nitest.matches("^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)"
                    + "(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])"
                    + "(?:\\s*\\d\\s*){6}([A-D]|\\s)$"))
            {
                
              //Alert to let the user know NI number format is wrong. 
            new Alert(Alert.AlertType.WARNING, "NI number format is incorrect")
                 .showAndWait();
            }
        }
        else
        {
            /* this nested If statement is checking the NI number entered 
            matches the correct format*/
            if (!nitest.matches("^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)"
                    + "(?:[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z])"
                    + "(?:\\s*\\d\\s*){6}([A-D]|\\s)$"))
            {
              //Alert to let the user know NI number format is wrong.  
            new Alert(Alert.AlertType.WARNING, "NI number format is incorrect")
                 .showAndWait();
        }
        else 
        {
         
        /* Checking the selected index from the array and then updating it with 
            what the user has entered in the text fields.
            */
           int selectedIndex = table.getSelectionModel().getSelectedIndex();
           
          employees.get(selectedIndex).setName(txtName2.getText().trim());
          employees.get(selectedIndex).setSurname(txtSurname2.getText().trim());
          employees.get(selectedIndex).setNiNo(txtniNo2.getText().trim());
          employees.get(selectedIndex).setPosition(txtPositionHeld2.getText()
                  .trim());
          employees.get(selectedIndex).setSalary(Double.parseDouble
        (txtSalary2.getText().trim()));
          
          saveEmployeeData();
          
            //REFRESHING page
            Refresh();
           
        }
        }
           
    }
    
      // method to update the array
    public void Update2(){
        //Checking all fields have been filled before running the save method.
           if (txtPosPE.getText().isEmpty() 
                    || txtSalaryPE.getText().isEmpty())
        {
        //Alert to let the user know all fields have not been filled. 
            new Alert(Alert.AlertType.WARNING, "Please enter all fields")
                 .showAndWait();
        }
        else 
        {
         
        /* Checking the selected index from the array and then updating it with 
            what the user has entered in the text fields.
            */
           int selectedIndex = posBoxPE.getSelectionModel().getSelectedIndex();
           
           position.get(selectedIndex).setPosHeld(txtPosPE.getText()
                   .trim());
           position.get(selectedIndex).setSalary
        (Double.parseDouble(txtSalaryPE.getText().trim()));
           
           
           //Calling method to save new employee to file.
           savePosition2Data();
           
        //closing window then refreshing the main page 
        windowPE.close();
        Refresh();   
           
        }
    }
    
 /** Here I have created methods to save object information to a 
     * saved files after the user has entered details of an 
     * employee
     */
    public void  saveEmployeeData()      
    {
        try
        {
            FileOutputStream file = new FileOutputStream("Employee.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            //for loop to increase ArrayList with each Objectthe user saves
            for (int i = 0; i<employees.size(); i++)
            {
                outputFile.writeObject(employees.get(i)); 
            }
            
            outputFile.close();
            
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Saved Successfully");
           alert.setHeaderText(null);
           alert.setContentText("Employee Saved!");

           alert.showAndWait();
           
           //clearing textfiels
           txtName.clear();
           txtniNo.clear();
           txtSurname.clear();
           txtPositionHeld.clear();
           txtSalary.clear();
           txtName2.clear();
           txtniNo2.clear();
           txtSurname2.clear();
           txtPositionHeld2.clear();
           txtSalary2.clear();
                      
        }
        catch (IOException e)
        {
         ExceptionMessage();  
        }
        
        //sorting Array into order.
        Collections.sort(employees);
    }
    
     //Delete method, same as the save just with a different success message. 
     public void  DeleteEmployeeFromFile()
    {
        
        //checking an employee has been selected
        if (txtniNo2.getText().isEmpty()){
           
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete");
        alert.setHeaderText("No Employee selected!");
        alert.setContentText("Selecte an employee to delete");
        alert.showAndWait();
      
        }
        
        else {
        /*Warning asking the user if they are sure they want to delete.
        I have then used a If statement so if they select yes the file will be 
        deleted and if no the warning will close the the employee will remain. 
        */
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Delete");
        alert2.setHeaderText("Delete Employee from record");
        alert2.setContentText("Are you sure?");
        
        
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK){
            
        try
        {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            employees.remove(selectedIndex);
            
            FileOutputStream file = new FileOutputStream("Employee.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            for (int i = 0; i<employees.size(); i++)
            {
                outputFile.writeObject(employees.get(i)); 
            }
            
            outputFile.close();
            
           Alert deleted = new Alert(Alert.AlertType.INFORMATION);
           deleted.setTitle("Deleted Successfully");
           deleted.setHeaderText(null);
           deleted.setContentText("Employee Deleted!");

           deleted.showAndWait();
           
           //sorting Array into order.
           
           Collections.sort(employees);
            
            //REFRESHING page
            Refresh();
            
        }
        catch (IOException e)
        {
          ExceptionMessage();  
        }
    }     
    }
    }
     
      // method to add positon to Array List 
    public void addPosition(){
        
        //Checking all fields have been filled
           if (txtPos.getText().isEmpty() || txtSalaryP.getText().isEmpty())
           
        {
            //Alert to let the user know all fields have not been filled. 
            new Alert(Alert.AlertType.WARNING, "Please enter all fields")
                .showAndWait();
        }
            else
        {
            
     /* here I am taking the information entered by the user in the text fields
      * and adding them to the List. At the same time I am converting from 
      * String to double for salary . 
      */
            Position pos = new Position (txtPos.getText().trim()
                    ,Double.parseDouble(txtSalaryP.getText().trim()));
                    
            position.add(pos);
            
           //Calling method to save new position to file.
            savePositionData();
            
            //closing window then refreshing the main page 
            windowP.close();   
            Refresh();
     
        }   
    }
            
    
    // method to save/overwrite the array to a file
    public void  savePositionData()
    {
        try
        {
            FileOutputStream file = new FileOutputStream("Position.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            //for loop to increase ArrayList with each Object the user saves
            for (int i = 0; i<position.size(); i++)
            {
                outputFile.writeObject(position.get(i)); 
            }
            
            outputFile.close();
            
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Saved Successfully");
           alert.setHeaderText(null);
           alert.setContentText("Position Saved!");

           alert.showAndWait();
           
           //clearing textfiels
           txtPos.clear();
           txtSalaryP.clear();
                    
        }
        catch (IOException e)
        {
         GUI.ExceptionMessage();  
        }
        
        //sorting Array into order.
        Collections.sort(position);
    }
    
    // Method to save/overwite array on file
    public void  savePosition2Data()
    {
        try
        {
            FileOutputStream file = new FileOutputStream("Position.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            //for loop to increase ArrayList with each Object the user saves
            for (int i = 0; i<position.size(); i++)
            {
                outputFile.writeObject(position.get(i)); 
            }
            
            outputFile.close();
            
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Saved Successfully");
           alert.setHeaderText(null);
           alert.setContentText("Position Saved!");

           alert.showAndWait();
           
           //clearing textfiels
           txtPosPE.clear();
           txtSalaryPE.clear();
           
                      
        }
        catch (IOException e)
        {
         GUI.ExceptionMessage();  
        }
        
        //sorting Array into order.
        Collections.sort(position);
    }
    
     //Delete method, same as the save just with a different success message. 
     public void Delete()
    {
        
       if (txtPosPE.getText().isEmpty()) 
       {
           
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete");
        alert.setHeaderText("No Position Selected!");
        alert.setContentText("Select a position to delete");
        alert.showAndWait();
       }
       
       else{
           
       
        /*Warning asking the user if they are sure they want to delete.
        I have then used a If statement so if they select yes the file will be 
        deleted and if no the warning will close the the employee will remain. 
        */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Position from record");
        alert.setContentText("Are you sure?");
        
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        try
        {
            int selectedIndex = posBoxPE.getSelectionModel().getSelectedIndex();
            position.remove(selectedIndex);
            
            FileOutputStream file = new FileOutputStream("Position.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            for (int i = 0; i<position.size(); i++)
            {
                outputFile.writeObject(position.get(i)); 
            }
            
            outputFile.close();
           
            
           Alert deleted = new Alert(Alert.AlertType.INFORMATION);
           deleted.setTitle("Deleted Successfully");
           deleted.setHeaderText(null);
           deleted.setContentText("Position Deleted!");

           deleted.showAndWait();
            
            
        }
        catch (IOException e)
        {
          GUI.ExceptionMessage();  
        }
        
        //sorting Array into order. 
        Collections.sort(position);
        
        //closing window then refreshing the main page 
        windowPE.close();
        Refresh();
                
    }
            
          else {
         
        }
    }    
    }
    
    //------------Populate Array Methods -----------------------------------
    
     /** Here I have created a methods to get the ArrayList information from my 
     * saved files and populate the array list. 
     */
    public void populateEmp()
    {
        
       try
       {
           //opening file to be used
          FileInputStream file = new FileInputStream("Employee.dat");
          //reading from the input stream 
          ObjectInputStream inputFile = new ObjectInputStream(file);
          
          /*checking for the end of the file
           (start on false as when you start reading it wont be the end)*/
          boolean endOfFile = false;
          /*while loop to make sure while its not the end of the file data
          continues to be read/*
          */
          while (!endOfFile)
          {
             try
             {
                employees.add((Employee)inputFile.readObject());
             }
             // when end of file happens change endOFfile to true
             catch (EOFException e)
             {
               endOfFile = true; 
             }
             catch (IOException | ClassNotFoundException f)
             {
               
             }
          }
          
          inputFile.close();
       }
       catch (IOException e)
       {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Employees");
         alert.setHeaderText(null);
         alert.setContentText("No employees found. Please add employees");

           alert.showAndWait();
       }
       //sorting Array into order. 
       Collections.sort(employees);
    }
    
    // Method to populate the arrayList
    public void populatePos()
    {
        
       try
       {
           //opening file to be used
          FileInputStream file = new FileInputStream("Position.dat");
          //reading from the input stream 
          ObjectInputStream inputFile = new ObjectInputStream(file);
          
          /*checking for the end of the file
           (start on false as when you start reading it wont be the end)*/
          boolean endOfFile = false;
          /*while loop to make sure while its not the end of the file data
          continues to be read/*
          */
          while (!endOfFile)
          {
             try
             {
                position.add((Position)inputFile.readObject());
             }
             // when end of file happens change endOFfile to true
             catch (EOFException e)
             {
               endOfFile = true; 
             }
             catch (IOException | ClassNotFoundException f)
             {
               
             }
          }
          
          inputFile.close();
       }
       catch (IOException e)
       {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Positions");
         alert.setHeaderText(null);
         alert.setContentText("No positions found. Please add positions ");

           alert.showAndWait();
       }
       //sorting Array into order. 
       Collections.sort(position);
    }
    
     public void Refresh(){
         
         
        updateE.getChildren().clear();
        rightC.getChildren().clear();
        addRemove.getChildren().clear();
        tableView.getChildren().clear();
        mainLayout.getChildren().clear();
        MainPage();
      
    }
  
       // ------------------------  Exception Message Method ---------
     
    // Method to display and Exception error to the user
     static void ExceptionMessage(){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception Error");
              
        Exception ex = new Exception();

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
}
     
}
