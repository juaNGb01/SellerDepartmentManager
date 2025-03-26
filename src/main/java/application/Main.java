package application;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Department dep = new Department(1,"Books");
        System.out.println(dep);

        Seller seller = new Seller(1, "Bob", "bob@gmail.com", LocalDate.now(),4500.5, dep );
        System.out.println(seller);
    }
}