package carsharing.DAO;

public class Customer {
    private Integer id;
    private String name;
    private Integer rentedCarID;

    public Customer(String name, Integer rentedCarID) {
        this.name = name;
        this.rentedCarID = rentedCarID;
    }

    public Customer(Integer id, String name, Integer rentedCarID) {
        this.id = id;
        this.name = name;
        this.rentedCarID = rentedCarID;
    }


    public Customer(String name) {
        this.name = name;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRentedCarID() {
        return rentedCarID;
    }

    public void setRentedCarID(Integer rentedCarID) {
        this.rentedCarID = rentedCarID;
    }
}
