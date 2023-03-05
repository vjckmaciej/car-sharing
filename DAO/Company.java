package carsharing.DAO;

public class Company {
    private String name;
    private Integer id;

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
