package carsharing.DAO;

import picocli.CommandLine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDaoImpl implements Dao {
    List<Company> companies;
    private final Connection connection;
    private final String tableName;

    public CompanyDaoImpl(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (ID INTEGER NOT NULL AUTO_INCREMENT," +
                "NAME VARCHAR(255) UNIQUE NOT NULL," +
                "PRIMARY KEY ( ID ))";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<List<Company>> getAllCompanies() {
        List<Company> allCompanies = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Company company = new Company(rs.getString("name"), rs.getInt("ID"));
                allCompanies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(allCompanies);
    }

    @Override
    public Optional<Company> getCompany(int id) {
        Company company = null;
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName + " WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                company = new Company(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(company);
    }

    @Override
    public void addCompany(String nameOfCompany, int id) {
        try {
            Statement stmt = connection.createStatement();
            String sql =  "INSERT INTO COMPANIES (name, id) VALUES "
                    + "(" + nameOfCompany + ", " + id + ");";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(int id) {
    }

}
