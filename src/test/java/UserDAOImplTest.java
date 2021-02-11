import DAO.Implements.UserDAOImpl;
import entity.User;

public class UserDAOImplTest {
    UserDAOImpl userDAO = new UserDAOImpl();

    @org.junit.Test
    public void get() {
        System.out.println(userDAO.get(1));
    }

    @org.junit.Test
    public void update() {
        User user = userDAO.get(1);
        user.setPassword("12345");
        userDAO.update(user);
    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void add() {
        User user = new User(1,"login","pass","Admin");
        userDAO.add(user);
    }

    @org.junit.Test
    public void getAll() {
        System.out.println(userDAO.getAll());
    }
}