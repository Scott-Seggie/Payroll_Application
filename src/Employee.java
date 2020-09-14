/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scott
 */

import java.io.*;

/* implements serializable allows me to write an object to a file and read 
the file when needed, I will use this to hold all my employee's in a list.

I have also implemented Compareable<T> so I can sort my List into order. 
*/

public class Employee implements Serializable, Comparable<Employee> {
    
    
    
    // varibles 
    private String name;
    private String surname;
    private String niNo;
    private String Position;
    private double salary;
    private double grossT;
    private double niT;
    private double taxT;
    private double netT;
    private int counter;
    private int firstHalf;
    private int secondHalf;

    public Employee(String name, String surname, String niNo, String Position, 
            double salary) {
        
        this.name = name;
        this.surname = surname;
        this.niNo = niNo;
        this.Position = Position;
        this.salary = salary;
        this.grossT = 0;
        this.niT = 0;
        this.taxT = 0;
        this.netT = 0;
        this.counter = 4;
        this.firstHalf = 17;
        this.secondHalf = 18;
        
    }

   
   
   
   //Comparing surnames to order them 
    @Override
    public int compareTo(Employee eIn) {
        return surname.compareTo(eIn.surname);
    }
    
   
   /* I am using this equals methods to compare and make sure
    they are pointing to the same memory loacation (they are the same Object).
    */
    public boolean equals (Employee employee)
    {
       return this.surname.equals(employee.surname) 
               && this.name.equals(employee.name)
               && this.niNo.equals(employee.niNo);
    }

    
    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNiNo() {
        return niNo;
    }

    public void setNiNo(String niNo) {
        this.niNo = niNo;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getNiT() {
        return niT;
    }

    public void setNiT(double niT) {
        this.niT = niT;
    }

    public double getTaxT() {
        return taxT;
    }

    public void setTaxT(double taxT) {
        this.taxT = taxT;
    }

    public double getGrossT() {
        return grossT;
    }

    public void setGrossT(double grossT) {
        this.grossT = grossT;
    }

    public double getNetT() {
        return netT;
    }

    public void setNetT(double netT) {
        this.netT = netT;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(int firstHalf) {
        this.firstHalf = firstHalf;
    }

    public int getSecondHalf() {
        return secondHalf;
    }

    public void setSecondHalf(int secondHalf) {
        this.secondHalf = secondHalf;
    }

    
    

  
  
    

    

   


}

