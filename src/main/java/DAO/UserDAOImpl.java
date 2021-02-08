package DAO;

import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useJDBCCompliantTimezoneShift" +
            "=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "artem";

    private final String SELECT_USER_BY_ID = "SELECT user.id, user.name, user.age, role.name FROM user " +
            "join user_role on user.id = user_role.user_id join role on user_role.role_id = role.id " +
            "WHERE user.id = ?";
    private final String SELECT_ALL_USERS = "SELECT user.id, user.name, user.age, role.name FROM user " +
            "join user_role on user.id = user_role.user_id join role on user_role.role_id = role.id ";
    private final String SELECT_ID_ROLE_BY_NAME = "SELECT id FROM role WHERE name = ?";
    private final String ADD_USER = "INSERT into user(id, name, age) values(?,?,?)";
    private final String ADD_USER_ROLE = "INSERT INTO user_role(user_id, role_id) values(?,?)";
    private final String UPDATE_USER_BY_ID = "UPDATE user SET name=?,age=? WHERE id=?";
    private final String UPDATE_USER_ROLE_BY_ID = "UPDATE user_role SET role_id = ? WHERE user_id=?";
    private final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    @Override
    public User get(Integer id) {
        User user = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Integer user_id = rs.getInt("id");
                String name = rs.getString("name");
                Integer age = rs.getInt("age");
                String role = rs.getString("role");
                user = new User(user_id, name, age, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_ROLE_BY_NAME);
            statement.setString(1, user.getRole());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int roleId = rs.getInt("id");

                statement = connection.prepareStatement(UPDATE_USER_BY_ID);
                statement.setString(1, user.getName());
                statement.setInt(2, user.getAge());
                statement.setInt(3, user.getId());
                statement.execute();

                statement = connection.prepareStatement(UPDATE_USER_ROLE_BY_ID);
                statement.setInt(1, roleId);
                statement.setInt(2, user.getId());
                statement.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID_ROLE_BY_NAME);
            statement.setString(1, user.getRole());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int role_id = rs.getInt("id");

                statement = connection.prepareStatement(ADD_USER);
                statement.setInt(1, user.getId());
                statement.setString(2, user.getName());
                statement.setInt(3, user.getAge());
                statement.execute();

                statement = connection.prepareStatement(ADD_USER_ROLE);
                statement.setInt(1, user.getId());
                statement.setInt(2, role_id);
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer user_id = rs.getInt("id");
                String name = rs.getString("name");
                Integer age = rs.getInt("age");
                String role = rs.getString("role");
                list.add(new User(user_id, name, age, role));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
