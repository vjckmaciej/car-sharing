package carsharing.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDaoImpl implements Dao<Company> {
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

    public List<Company> getAll() {
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
        return allCompanies;
    }

    @Override
    public Optional<Company> get(int id) {
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
    public void add(Company company) {
        try {
            Statement stmt = connection.createStatement();
            String sql =  "INSERT INTO " + tableName + " (name) VALUES "
                    + "('" + company.getName() + "')";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company) {
    }

    @Override
    public void delete(int id) {
    }

}
