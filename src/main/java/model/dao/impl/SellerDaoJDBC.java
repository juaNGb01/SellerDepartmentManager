package model.dao.impl;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        String sql =  "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "VALUES ( ? , ? , ?, ? , ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, Date.valueOf(seller.getBirthDate()));
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDepartment().getId());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        int id = rs.getInt(1);
                        System.out.println("New ID "+ id + ", added successfully! " + rowsAffected + " rows Affected");

                    }else{
                        throw new DbException("Unexpected error, no rows affected!");
                    }
                }
            }

        }catch (SQLException e){
            throw new DbException("Error inserting new seller: " + e.getMessage());
        }

    }

    @Override
    public void update(Seller seller) {
        String sql = "UPDATE seller " +
                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                "WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, Date.valueOf(seller.getBirthDate()));
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDepartment().getId());
            stmt.setInt(6, seller.getId());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println( rowsAffected + " rows affected");
            }

        }catch (SQLException e){
            throw new DbException("Error during update: " + seller.getId() + e.getMessage() );
        }


    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM seller WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Done! " + rowsAffected + " rows affected" );

            }else{
                throw new DbException("Unexpected error, no rows affected! ");
            }


        }catch (SQLException e){
            throw new DbException("Error removing ID " + id + e.getMessage() );
        }

    }

    @Override
    public Seller findById(Integer id) {

        String sql = "SELECT seller.*,department.Name as DepName" +
                " FROM seller INNER JOIN department" +
                " ON seller.DepartmentId = department.id" +
                " WHERE seller.Id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){

                    Department dep = instantiateDepartment(rs);
                    Seller seller = instantiateSeller(rs, dep);
                    return seller;
                }
                return null;
            }

        }catch (SQLException e){
            throw new DbException("Erro ao buscar o id:  " + id +  e.getMessage());

        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        String sql = "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE DepartmentId = ? " +
                "ORDER BY Name";

        List<Seller> list = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, department.getId());

            try(ResultSet rs = stmt.executeQuery()){
                //map com key id e valor department
                Map<Integer, Department> map = new HashMap<>();

                while(rs.next()){

                    //pega o valor do map com base no id do rs
                    Department dep = map.get(rs.getInt("departmentId"));

                    //verifica se o dep existe, se for null não existe e pode ser instanciado
                    if(dep == null){
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("departmentId"), dep);
                    }

                    //instancia um seller e add na lista
                    Seller seller = instantiateSeller(rs, dep);
                    list.add(seller);

                }

                return list;

            }


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        String sql = "SELECT *, department.name AS DepName FROM seller JOIN  department " +
                "ON seller.departmentid = department.id;  ";

        List<Seller> list = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){
                Map<Integer, Department> map = new HashMap<>();

                while(rs.next()){
                    Department dep = map.get(rs.getInt("departmentId"));
                    if(dep == null){
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("departmentId"), dep);
                    }

                    Seller seller = instantiateSeller(rs, dep);
                    list.add(seller);

                }

                return list;
            }

        }catch (SQLException e){
            throw new DbException("Erro ao listar todos os dados: " + e.getMessage());
        }
    }

    public Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department(rs.getInt("departmentId"),rs.getString("DepName"));
        return dep;
    }

    public Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{

        LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
        Seller seller = new Seller(rs.getInt("id"), rs.getString("name"),
                rs.getString("email"), birthDate,
                rs.getDouble("baseSalary"), dep );

        return seller;
    }
}
