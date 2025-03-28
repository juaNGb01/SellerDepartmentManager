package application;

import model.dao.SellerDao;
import model.dao.factory.DaoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department dep = new Department(1, "Computers");

        List<Seller> sellersList= sellerDao.findByDepartment(dep);
        System.out.println(sellersList);
    }
}