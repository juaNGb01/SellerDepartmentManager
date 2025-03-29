package application;

import model.dao.DepartmentDao;
import model.dao.factory.DaoFactory;
import model.entities.Department;

import java.util.List;

public class DepartmentMain {
    public static void main(String[] args){
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("--Insert Department--");
        Department newDep = new Department(null, "RH");
        depDao.insert(newDep);

        System.out.println("--Find All--");
        List<Department> list  = depDao.findAll();
        for(Department dep : list){
            System.out.println(dep);
        }

        System.out.println("--Find by Id--");
        Department dep = depDao.findById(1);
        System.out.println(dep);



        System.out.println("--Update--");
        dep = depDao.findById(1);
        dep.setName("Finance");
        depDao.updade(dep);
        System.out.println(dep);

        System.out.println("--Delete by ID--");
        depDao.deleteById(6);



    }
}
