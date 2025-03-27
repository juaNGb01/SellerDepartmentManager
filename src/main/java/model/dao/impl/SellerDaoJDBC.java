package model.dao.impl;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void updade(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

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

                    Department dep = new Department(rs.getInt("departmentId"),rs.getString("DepName"));

                    LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                    Seller seller = new Seller(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), birthDate,
                            rs.getDouble("baseSalary"), dep );

                    return seller;
                }
                return null;
            }

        }catch (SQLException e){
            throw new DbException("Erro ao buscar os dados de SELLER: " + e.getMessage());

        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
