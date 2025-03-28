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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    Department dep = instantiateDepartment(rs);
                    Seller seller = instantiateSeller(rs, dep);
                    return seller;
                }
                return null;
            }

        }catch (SQLException e){
            throw new DbException("Erro ao buscar os dados de SELLER: " + e.getMessage());

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
