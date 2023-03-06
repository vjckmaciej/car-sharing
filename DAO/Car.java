package carsharing.DAO;

public class Car {
    private Integer id;
    private String name;
    private Integer companyID;

    public Car(String name, Integer companyID) {
        this.name = name;
        this.companyID = companyID;
    }

    public Car(Integer id, String name, Integer companyID) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
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

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }
}
