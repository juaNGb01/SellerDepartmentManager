package application;

import model.dao.SellerDao;
import model.dao.factory.DaoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {



        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
    }
}