
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Payroll 
{

    //employees arraylist
    List<Employee> employees = new ArrayList<>(); 
    
    //varible for a counter 
    int counter = 4;
    String month = "";
    int firstHalf = 17;
    int secondHalf = 18;
    
    
    public void payroll (){
        
    //ArrayList to fill with employee Objects
    populateEmp();  
    
    //setting counter to correct period
    try{
    //setting counter to the correct period
    counter = employees.get(0).getCounter();
    firstHalf = employees.get(0).getFirstHalf();
    secondHalf = employees.get(0).getSecondHalf();
    }
    catch(Exception e){
        
     Alert array = new Alert(Alert.AlertType.WARNING);
     array.setTitle("Error");
     array.setHeaderText(null);
     array.setContentText("Add employees to run payroll");

           array.showAndWait();   
    }
    
    if (counter == 4){
       
          // increase year when period gets to 4    
          firstHalf++;
          secondHalf++;
    }
    
    // setting stage
    Stage window = new Stage();
    window.setTitle("Payroll");
    window.initModality(Modality.APPLICATION_MODAL);
    
    //Nodes 
    HBox root = new HBox(20);
    root.setAlignment(Pos.CENTER);
    VBox left = new VBox(20);
    left.setAlignment(Pos.CENTER);
    HBox leftTop = new HBox(10);
    leftTop.setAlignment(Pos.CENTER);
    VBox right = new VBox(20);
    right.setAlignment(Pos.CENTER);
    
    Label period = new Label("Period:");
    
    //switch to set the month depending on the counter
      switch (counter) {
            case 1:  month = "January";
                     break;
            case 2:  month = "February";
                     break;
            case 3:  month = "March";
                     break;
            case 4:  month = "April";
                     break;
            case 5:  month= "May";
                     break;
            case 6:  month = "June";
                     break;
            case 7:  month = "July";
                     break;
            case 8:  month = "August";
                     break;
            case 9:  month = "September";
                     break;
            case 10: month = "October";
                     break;
            case 11: month= "November";
                     break;
            case 12: month = "December";
                     break;
            default: month = "Error";
                     break;
      }
    
    
    Label taxmonth = new Label(month+"-"+firstHalf+"/"+secondHalf); 
    
    Label empPayslip = new Label("View Employee Payslip");
   
    TextArea payslip = new TextArea();
    payslip.setStyle("-fx-text-alignment: center");
    
      ComboBox<String> emp = new ComboBox<>();
    //Populating the comboBox with the position array   
       String [] empList = new String[employees.size()];
      
       for (int i =0; i<employees.size(); i++)
       empList[i] = employees.get(i).getSurname()+" "
               +employees.get(i).getName();
       
       emp.getItems().addAll(empList);
       
    
    //---------------Producing payslips on button click ---------------------
    
    Button pay = new Button("Produce Payslips");
    pay.setStyle
        (" -fx-background-color: linear-gradient(#80ff80,#006600)");
    pay.setOnAction(e -> {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payroll");
        alert.setHeaderText("Produce Payslips");
        alert.setContentText("Are you sure you wish to run the payroll for "+
                month+"-"+firstHalf+"/"+secondHalf+" and produce payslips for "
                        + "all employees?");
        
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){   
            
            
        if (counter == 4){
            
          //resetting total deductions and earnning when period returns to 4
          for (int i = 0; i<employees.size(); i++){  
            
          employees.get(i).setGrossT(0);  
          employees.get(i).setNiT(0);  
          employees.get(i).setTaxT(0);
          employees.get(i).setNetT(0); 
          
          //storing the updated year
          employees.get(i).setFirstHalf(firstHalf);
          employees.get(i).setSecondHalf(secondHalf);    
          
          saveEmployeeData();   
        } 
        }
          
        /*inrementing counter for the pay period if it is less than 12
        otherise it will reset to 1 and inrease the tax year 
        */
        if (counter>=12){
            
            counter = 1;
        
        }
        else{
            counter ++;
        }
        
        
        //loop to run through each employee and produce a payslip
        for (int i = 0; i<employees.size(); i++){
        
        //saving pay month for each employee
        employees.get(i).setCounter(counter);
        
        //varible to set the payslip save name
        String save = employees.get(i).getName()
                +" "+employees.get(i).getSurname();
        
        //setting the formate for doubles 
        DecimalFormat df = new DecimalFormat("#"); 
        DecimalFormat df2 = new DecimalFormat("#,###.00"); 
        
        String tempName = employees.get(i).getName()
                +" "+employees.get(i).getSurname();
        
        String tempNInumber = employees.get(i).getNiNo();
        
        String tempPosition = employees.get(i).getPosition();
                
        double sal = employees.get(i).getSalary();
        String salpass =String.valueOf(sal);        
        
        String taxCode,tempNI,tempTax,tempTotal,tempNet;
        
        String newLine = System.getProperty("line.separator");
        
      
      // Calculating the correct tax code and displaying it
          double taxcode = 0;
          double ni;
          double tax;
          double total;
          double net;
          //taking salary pass in and taking away 100k the divided by 2
          double taxcal = (sal - 100000) / 2;
          //taking the taxcal off of 11850 to the the higherTax code
          double higherTax = 11850 - taxcal;
          
          /* if salary passed in from array is less than or equla too 100k
          then set taxCode as 1185L
          */
             if (sal<= 100000){
                 
              taxcode = 1185;
              taxCode = df.format(taxcode)+"L";
          }
             
             else{
                 /* if the highTax is more than 0 display amount followed by
                 the letter T
               */
                if (higherTax > 0){
                    
                   /*putting double in textfiled with a format followed by 
                    letter T
                    */ 
                   
                   taxCode = df.format(higherTax)+"T";
                }
                // else display 0T
                else {
                    higherTax = 0;
                    taxCode = df.format(higherTax)+"T";
                }
          }
         
      //----Calculating NI 
      
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
          
          double niM = ni/12;
          String niMpass = String.valueOf(df2.format(niM));
          tempNI = niMpass;
          
          
      // calculatiing Tax 
        
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
         
          double taxM = tax/12;
          String taxMpass = String.valueOf(df2.format(taxM));
          tempTax = taxMpass;
        
        //----setting total deductions-
          
          total = taxM + niM;
          String totalPass = String.valueOf(df2.format(total));
          tempTotal = totalPass;
        
        
        //-net and gross pay (monthly)-
            
         double mSal = sal/12;
         String mSalpass =String.valueOf(df2.format(mSal));
        
         net = (sal/12) - total;
         String netPass = String.valueOf(df2.format(net));
         tempNet = netPass;
            
         
         /* setting a running total of what the employees have earnt
         so far this year*/
         
          double acuGross = employees.get(i).getGrossT();    
          employees.get(i).setGrossT(acuGross+mSal);  
         
          double acuNi = employees.get(i).getNiT();    
          employees.get(i).setNiT(acuNi+niM);  
          
          double acuTax = employees.get(i).getTaxT();
          employees.get(i).setTaxT(acuTax+taxM);
          
          double acuSal = employees.get(i).getNetT();
          employees.get(i).setNetT(acuSal+net); 
          
          saveEmployeeData();
         
        //-- wrting final payslip to a file for each employee
        
        //make direcotry for payslips if it doesnt already exist
        
         File directory = new File("Payslips");
         if (! directory.exists()){
         directory.mkdir();
        
    }

            File dir = new File("Payslips"+"/"+save+" "
                    +month+"-"+firstHalf+"-"+secondHalf+".txt");
              try
              (
            FileWriter file = new FileWriter(dir.getAbsoluteFile());
            PrintWriter writer = new PrintWriter(file);
                      ){
            
         
           
              writer.println("\t\t******EMPLOYEE PAYSLIP******");
              writer.println();
              writer.println("\t\tPeriod: "
                      +month+"-"+firstHalf+"/"+secondHalf);
              writer.println();
              writer.println();
              writer.println(" Name: " + tempName +"\t\t" +
                "Position: "+tempPosition);
              writer.println();
              writer.println(" NI Number: "+tempNInumber +"\t\t" +
                "TaxCode: " + taxCode);
              writer.println();
              writer.println("********************** This Pay "
                      + "*************************");
              writer.println();
              writer.println("\t\tGross Pay: "+"£"+mSalpass);
              writer.println();
              writer.println(" NI Deduction: "+"£" +tempNI +"\t\t" +
                "Tax Deduction: " +"£" +tempTax);
              writer.println();
              writer.println("\t\tTotal Deduction: "+"£" +tempTotal);
              writer.println();
              writer.println("\t\tNet Pay: "+"£" +tempNet);
              writer.println();
              writer.println();
              writer.println("********************* Year to Date "
                      + "**********************");    
              writer.println();
              writer.println("\t\tYTD Gross: "+"£"+  
                      df2.format(employees.get(i).getGrossT()));
              writer.println();
              writer.println("\t\tYTD NI: "+"£"+  
                      df2.format(employees.get(i).getNiT()));
              writer.println();
              writer.println("\t\tYTD Tax: "+"£"+ 
                      df2.format(employees.get(i).getTaxT()));
              writer.println();
              writer.println("\t\tYTD Salary: "+ "£"+ 
                      df2.format(employees.get(i).getNetT()));
              writer.println();
              
              
        }
              
        catch (IOException f)
        {
         Alert file = new Alert(Alert.AlertType.WARNING);
         file.setTitle("Error");
         file.setHeaderText(null);
         file.setContentText("Error writing files");

           file.showAndWait();
        }
          
        
        }
        
         Alert slip = new Alert(Alert.AlertType.INFORMATION);
         slip.setTitle("Payroll "+month+"-"+firstHalf+secondHalf);
         slip.setHeaderText(null);
         slip.setContentText("Payslips produced and saved files have been "
                 + "stored in the payslips folder.");

           slip.showAndWait();
           
        window.close();
        
        }
    });
    
    
    
    Button close = new Button("Close");
    close.setStyle
        (" -fx-background-color: linear-gradient(#ffb3b3,#cc0000)");
    close.setOnAction(e -> window.close());
    
  
    emp.setOnAction(e -> {
        
        //setting the formate for doubles 
        DecimalFormat df = new DecimalFormat("#"); 
        DecimalFormat df2 = new DecimalFormat("#,###.00"); 
        
        //------Setting variables from the Selected Positon in Array -----
        int index = emp.getSelectionModel().getSelectedIndex();
        
        String tempName = employees.get(index).getName()
                +" "+employees.get(index).getSurname();
        
        String tempNInumber = employees.get(index).getNiNo();
        
        String tempPosition = employees.get(index).getPosition();
                
        double sal = employees.get(index).getSalary();
        String salpass =String.valueOf(sal);        
        
        String taxCode,tempNI,tempTax,tempTotal,tempNet;
        
        String newLine = System.getProperty("line.separator");
        
        
        
      // ---------- working out the correct tax code -------------------
      
      // Calculating the correct tax code and displaying it
          double taxcode = 0;
          double ni;
          double tax;
          double total;
          double net;
          //taking salary pass in and taking away 100k the divided by 2
          double taxcal = (sal - 100000) / 2;
          //taking the taxcal off of 11850 to the the higherTax code
          double higherTax = 11850 - taxcal;
          
          /* if salary passed in from array is less than or equla too 100k
          then set taxCode as 1185L
          */
             if (sal<= 100000){
                 
              taxcode = 1185;
              taxCode = df.format(taxcode)+"L";
          }
             
             else{
                 /* if the highTax is more than 0 display amount followed by
                 the letter T
               */
                if (higherTax > 0){
                    
                   /*putting double in textfiled with a format followed by 
                    letter T
                    */ 
                   
                   taxCode = df.format(higherTax)+"T";
                }
                // else display 0T
                else {
                    higherTax = 0;
                    taxCode = df.format(higherTax)+"T";
                }
          }
         
      // -------------------Calculating NI ----------------------
      
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
          
          double niM = ni/12;
          String niMpass = String.valueOf(df2.format(niM));
          tempNI = niMpass;
          
          
      // ---------------- calculatiing Tax   -----------------------
        
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
         
          double taxM = tax/12;
          String taxMpass = String.valueOf(df2.format(taxM));
          tempTax = taxMpass;
        
        //----------------setting total deductions--------------
          
          total = taxM + niM;
          String totalPass = String.valueOf(df2.format(total));
          tempTotal = totalPass;
        
        
        //-----------------net and gross pay (monthly)-----------------------------
            
         double mSal = sal/12;
         String mSalpass =String.valueOf(df2.format(mSal));
        
         net = (sal/12) - total;
         String netPass = String.valueOf(df2.format(net));
         tempNet = netPass;
         
         
         // ------- producing payslip --------
        
        payslip.clear(); 
        payslip.appendText("******EMPLOYEE PAYSLIP******");
        payslip.appendText(newLine);
        payslip.appendText("\n"+"Period: "+month+"-"+firstHalf+"/"+secondHalf); 
        payslip.appendText(newLine);
        payslip.appendText("\n"+"Name: " + tempName +"\t\t" +
                "Position: "+tempPosition);
        payslip.appendText(newLine);
        payslip.appendText("\n"+"NI Number: "+tempNInumber +"\t\t" +
                "TaxCode: " + taxCode);
        payslip.appendText(newLine);
        payslip.appendText("\n"+"********************************************"
                + "*********************");
        payslip.appendText(newLine);
        payslip.appendText("\n"+"Gross Pay: "+"£"+mSalpass); 
        payslip.appendText(newLine);
        payslip.appendText("\n"+"NI Deduction: "+"£" +tempNI +"\t\t" +
                "Tax Deduction: " +"£" +tempTax);
        payslip.appendText(newLine);
        payslip.appendText("\n"+"Total Deduction: "+"£" +tempTotal); 
        payslip.appendText(newLine);
        payslip.appendText("\n"+"Net Pay: "+"£" +tempNet); 
        payslip.appendText(newLine);

        
    });
       
    
    
    // setting Layout
    right.getChildren().addAll(empPayslip,emp,payslip);
    leftTop.getChildren().addAll(period,taxmonth);
    left.getChildren().addAll(leftTop,pay,close);
    root.getChildren().addAll(left,right);
    
    Scene scene = new Scene(root,950,400);
    
    window.setScene(scene);
    scene.getStylesheets().add("Payroll.css");
    
    window.showAndWait();
        
    }
    
  
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
          
                     
        }
        catch (IOException e)
        {
         Alert deleted = new Alert(Alert.AlertType.WARNING);
         deleted.setTitle("Error");
         deleted.setHeaderText(null);
         deleted.setContentText("Error updating employees");

           deleted.showAndWait();
        }
        
        //sorting Array into order.
        Collections.sort(employees);
    }
    
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
       }
       //sorting Array into order. 
       Collections.sort(employees);
    }
   
}
