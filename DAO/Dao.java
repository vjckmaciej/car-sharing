package carsharing.DAO;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> getAll();
    Optional<T> get(int id);
    void add(T t);
    void update(T t);
    void delete(int id);
}
