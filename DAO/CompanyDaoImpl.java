package carsharing.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDaoImpl implements Dao {
    private final Connection connection;
    private final String tableName;

    public CompanyDaoImpl(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (ID INT AUTO_INCREMENT PRIMARY KEY," +
                " NAME VARCHAR(255) UNIQUE NOT NULL)";
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
            stmt.close();
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
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(company);
    }

    @Override
    public void addCompany(String nameOfCompany) {
        try {
            Statement stmt = connection.createStatement();
            String sql =  "INSERT INTO " + tableName + " (name) VALUES "
                    + "('" + nameOfCompany + "')";
            stmt.execute(sql);
            stmt.close();
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
