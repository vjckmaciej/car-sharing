package carsharing.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements Dao<Customer>{
    private final String tableName;
    private final Connection connection;

    public CustomerDaoImpl(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
        String sqlCompany = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "RENTED_CAR_ID INTEGER DEFAULT NULL," +
                "PRIMARY KEY ( ID ), " +
                "CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID));";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCompany);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Customer> getAll() {
        List<Customer> allCustomers = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RENTED_CAR_ID"));
                allCustomers.add(customer);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public Optional<Customer> get(int id) {
        Customer customer = null;
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName + " WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                customer = new Customer(rs.getString("NAME"), rs.getInt("RENTED_CAR_ID"));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public void add(Customer customer) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO " + tableName + " (NAME) VALUES "
                    + "('" + customer.getName() + "')";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        try {
            Statement stmt = connection.createStatement();
            String sql =  "UPDATE " + tableName + " SET RENTED_CAR_ID = " + customer.getRentedCarID()
                    + " WHERE ID = " + customer.getID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

    }

    public Optional<Car> getRentedCar(Integer id) {
        Car car = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM CAR INNER JOIN CUSTOMER " +
                    " ON CAR.ID = CUSTOMER.RENTED_CAR_ID " +
                    " WHERE CUSTOMER.ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                car = new Car(rs.getInt("CAR.ID"), rs.getString("CAR.NAME"), rs.getInt("CAR.COMPANY_ID"));
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return Optional.ofNullable(car);
    }

    public boolean isCarLocked(Integer id) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT 1 FROM " + tableName + " WHERE RENTED_CAR_ID = " + id);
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

