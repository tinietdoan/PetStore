// package observerPackage;

// import itemPackage.*;
// import employeePackage.*;
// import java.io.*;
// import java.util.*;

// public class salesObs implements Subscriber{
//     int changeIdx;
    
//     public salesObs() {
//         changeIdx = 0;
//     }

//     @Override
//     public void update(Object o) {
//         this.changeIdx = (int) o;
//         this.display();
//     }

//     @Override
//     public void display() {
//         Pet currPet = this.escaped;
//         System.out.println("\n==> OBSERVATION: " +  currPet.getName() + " the " + currPet.getBreed() + " has escaped! No worries, " +  this.emp.getName() + " the " + this.type + " caught " + currPet.getName() + "\n");
//     }

//     public void setEmp(Employee e, String type){
//         this.emp = e;
//         this.type = type;
//     }
// }
