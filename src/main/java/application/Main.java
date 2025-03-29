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

        System.out.println("Insert: ");
        Department department = new Department(2, null);
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com",LocalDate.now(), 4000.0,
                department);
        sellerDao.insert(newSeller);

      /*  System.out.println("Find by Department: ");
        Department dep = new Department(1, null);
        List<Seller> sellersList= sellerDao.findByDepartment(dep);
        for(Seller seller: sellersList){
            System.out.println(seller);

        }

        System.out.println("Remove By ID: ");
        sellerDao.deleteById(10);*/
    }
}