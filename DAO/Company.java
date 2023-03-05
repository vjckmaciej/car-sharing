package carsharing.DAO;

public class Company {
    private String name;
    private int ID;

    public Company(String name, int id) {
        this.name = name;
        this.ID = ID;
    }

    public Company(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
