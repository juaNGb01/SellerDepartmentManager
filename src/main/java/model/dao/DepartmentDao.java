package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {
     void insert(Department dep);
     void updade(Department dep);
     void deleteById(Integer id);
     Department findById(Integer id);
     List<Department> findAll();
}
