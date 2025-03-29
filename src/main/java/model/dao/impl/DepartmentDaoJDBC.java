package model.dao.impl;

import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn = null;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department dep) {
        String sql = "INSERT INTO department (name) VALUES (?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, dep.getName());
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                try(ResultSet rs =  stmt.getGeneratedKeys()){
                    if(rs.next()){
                        int id = rs.getInt(1);
                        System.out.println("Done! Department added, ID: " + id);
                    }else{
                        throw new DbException("Unexpected error, no rows affected!");
                    }
                }
            }

        }catch (SQLException e){
            throw new DbException("Error to insert new department: " + e.getMessage());
        }
    }

    @Override
    public void updade(Department dep) {
        String sql = "UPDATE seller SET name = ? WHERE  id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, dep.getName());
            stmt.setInt(2, dep.getId());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Department updated!");
            }

        }catch (SQLException e){
            throw new DbException("Error updating department: "  + e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Department removed! " + rowsAffected + " rows affected!");
            }


        }catch (SQLException e){
            throw new DbIntegrityException("Error deleting department" + e.getMessage());
        }

    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, id);
            try(ResultSet rs =  stmt.executeQuery()){
                if (rs.next()){
                    Department dep = new Department(rs.getInt("id"), rs.getString("name"));
                    return dep;
                }
                return null;
            }
        }catch (SQLException e){
            throw new DbException("Error finding ID " + id + ": " + e.getMessage());
        }


    }

    @Override
    public List<Department> findAll() {

        String sql = "SELECT * FROM department";
        List<Department> list =  new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){

                while (rs.next()){
                    Department dep = new Department(rs.getInt("id"), rs.getString("name"));
                    list.add(dep);
                }

                return list;

            }
        }catch (SQLException e ){
            throw new DbException("Error finding all departments: " + e.getMessage());
        }
    }
}
