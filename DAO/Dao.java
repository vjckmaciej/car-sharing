package carsharing.DAO;
import java.util.List;
import java.util.Optional;

public interface Dao {
    public Optional<List<Company>> getAllCompanies();
    public Optional<Company> getCompany(int id);
    public void addCompany(String nameOfCompany, int id);
    public void updateCompany(Company company);
    public void deleteCompany(int id);
}
