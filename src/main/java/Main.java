import DAO.UserDAOImpl;
import entity.User;

public class Main {

    public static void main(String[] args) {
        User user = new User("Artem", 23, "Admin");
        UserDAOImpl users = new UserDAOImpl();
        users.save(user);
        System.out.println(users.get(1));
        user.setRole("Director");
        users.update(user);
        User user1 = new User("Andrew", 20,"driver");
        users.save(user1);
        System.out.println(users.get(2));
        users.delete(2);
        System.out.println(users.getAll());
    }
}
