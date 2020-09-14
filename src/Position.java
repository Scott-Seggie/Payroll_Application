
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scott
 */
public class Position implements Serializable, Comparable <Position>{

    
    //varibales 
    private String posHeld;
    private double salary;
    
    //Constructor 
    public Position(String posHeld, double salary) {
        this.posHeld = posHeld;
        this.salary = salary;
    }
    
    //Comparing posHeld (job positions)to order them 
    @Override
    public int compareTo(Position pIn) {
      return posHeld.compareTo(pIn.posHeld);
    }
    
   /* I am using this equals methods to compare and make sure
    they are pointing to the same memory loacation (they are the same Object).
    */
    public boolean equals (Position position)
    {
       return this.posHeld.equals(position.posHeld) 
               && this.salary == position.salary;
               
    }

    //Getters and Setters
    public String getPosHeld() {
        return posHeld;
    }

    public void setPosHeld(String posHeld) {
        this.posHeld = posHeld;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    
    
    
}
