package application;

import model.dao.SellerDao;
import model.dao.factory.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class SellerMain {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--Find by ID--");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("--Find by department--");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("--Find All--");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("--Insert--");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", LocalDate.now(),
                4000.0, department);
        sellerDao.insert(newSeller);

        System.out.println("--Update--");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);

        System.out.println("--Delete by ID--");
        sellerDao.deleteById(2);
    }
}