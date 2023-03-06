package carsharing.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements Dao<Car> {
    private final String tableName;
    private final Connection connection;

    public CarDaoImpl(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        String sqlCompany = "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "COMPANY_ID INTEGER NOT NULL, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "PRIMARY KEY ( ID ), " +
                "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCompany);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCarsByCompanyId(Integer  id) {
        List<Car> allCarsByCompany = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName + " WHERE COMPANY_ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Car car = new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID"));
                allCarsByCompany.add(car);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCarsByCompany;
    }

    @Override
    public List<Car> getAll() {
        List<Car> allCars = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Car car = new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID"));
                allCars.add(car);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCars;
    }

    @Override
    public Optional<Car> get(int id) {
        Car car = null;
        try {
            Statement stmt = connection.createStatement();
            String sql =  "SELECT * FROM " + tableName + " WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                car = new Car(rs.getString("NAME"), rs.getInt("COMPANY_ID"));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(car);
    }

    @Override
    public void add(Car car) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO " + tableName + " (NAME, COMPANY_ID) VALUES "
                    + "('" + car.getName() + "', " + car.getCompanyID() + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(int id) {

    }
}
