package org.cat.jaumebalmes.eltestjb.models.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.cat.jaumebalmes.eltest.models.User;

/**
 *
 * Manages access to database data
 *
 * @author Zaid
 */
public class UserDao {

    private Connection conn;
    private final String SELECT_ALL_USERS = "SELECT * FROM users";
    private final String SELECT_WHERE_USERNAME = "SELECT * FROM users WHERE username = ?";
    private final String SELECT_WHERE_MAIL = "SELECT * FROM users WHERE mail = ?";
    private final String SELECT_WHERE_ID = "SELECT * FROM users WHERE userid = ?";
    private final String INSERT_NEW_USER = "INSERT INTO users(username, mail, "
            + "password) VALUES "
            + "(?,?,?)";

    private final String UPDATE_USER = "UPDATE users set username = ?, mail = ?, "
            + "password = ? WHERE userid = ?";

    private final String DELETE_USER = "DELETE FROM users WHERE userid = ?";

    /**
     * Empty builder.
     */
    public UserDao() {
    }

    /**
     *
     * Add a user into the database given from the controller
     *
     * @param user : User to add.
     * @return -2 if the username already exists in the database, -1 if the
     * email already exists in the database, 0 if the phone already exists in
     * the database, 1 if it has managed to add it, -3 otherwise.
     */
    public int addUser(User user) {
        int result = validateUser(user);

        if (result > 0) {
            try {
                conn = DBConnect.getConnection();
                if (conn != null) {
                    PreparedStatement ps = conn.prepareStatement(INSERT_NEW_USER);
                    ps.setString(1, user.getUserName());
                    ps.setString(2, user.getMail());
                    ps.setString(3, user.getPassword());
                    ps.executeUpdate();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                result = -3;
            }
        }
        return result;
    }

    /**
     * Search all the users from the database.
     *
     * @return list of users from the database.
     */
    public List<User> findAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            conn = DBConnect.getConnection();
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
                //Statement smt = conn.createStatement();
                ResultSet rs = ps.executeQuery(SELECT_ALL_USERS);
                while (rs.next()) {
                    User user = getUser(rs);
                    if (user != null) {
                        result.add(user);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = null;
        }

        return result;
    }

    /**
     *
     * Find user from the database with a given email from the controller.
     *
     * @param email :Email of the user to find.
     * @return User object, null in case of error.
     */
    public User findUserByEmail(String email) {
        User user = null;

        try {
            conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_WHERE_MAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

    /**
     *
     * Find user from the database with a given user name from the controller.
     *
     * @param userName: User name of the user to find.
     * @return User object, null in case of error.
     */
    public User findUserByUserName(String userName) {
        User user = null;
        try {
            conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_WHERE_USERNAME);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }

        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

    /**
     *
     * Modify a user into the database given from the controller.
     *
     * @param user : User to modify.
     * @return -2 if the username already exists in the other user of the
     * database, -1 if the email already exists in the other user of the
     * database, 0 if the phone already exists in the other user of the
     * database, 1 if it has managed to modify it, -3 otherwise.
     */
    public int modifyUser(User user) {
        int result = validateForModifyUser(user);

        if (result > 0) {
            try {
                conn = DBConnect.getConnection();
                if (conn != null) {
                    PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
                    ps.setString(1, user.getUserName());
                    ps.setString(2, user.getMail());
                    ps.setString(3, user.getPassword());
                    ps.executeUpdate();
                    result = 1;
                }

            } catch (SQLException ex) {
                result = -3;
            }
        }
        return result;
    }

    /**
     *
     * Remove user from the database given from the controller.
     *
     * @param user : User to remove.
     * @return 0 if the user not exist in the database, 1 if the user is
     * removed, -1 otherwise.
     */
    public int removeUser(User user) {
        int result = -1;

        User oldUser = findUserById(user.getId());
        if (oldUser != null) {
            try {
                conn = DBConnect.getConnection();
                if (conn != null) {
                    PreparedStatement ps = conn.prepareStatement(DELETE_USER);
                    ps.setInt(1, user.getId());
                    ps.executeUpdate();
                    result = 1;
                }

            } catch (SQLException ex) {
                result = -1;
            }
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * Find user from the database with a given id.
     *
     * @param id: id of the user to find.
     * @return User object, null in case of error.
     */
    public User findUserById(int id) {
        User user = null;

        try {
            conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_WHERE_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }

        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

    /**
     * Construct a user object with a given result set.
     *
     * @param rs : Result set with which to build the user object.
     * @return user object, null in case of error.
     */
    private User getUser(ResultSet rs) {
        User user;
        try {
            int id = rs.getInt("userid");
            String userName = rs.getString("username");
            String mail = rs.getString("mail");
            String password = rs.getString("password");
            user = new User(id, userName, mail, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            user = null;
        }
        return user;
    }

    /**
     * Validates whether the user can be added to the database.
     *
     * @param user: User to validate.
     * @return -2 if the username already exists in the database, -1 if the
     * email already exists in the database, 0 if the phone already exists in
     * the database, 1 if you can add it to the database.
     */
    private int validateUser(User user) {
        int result;
        User oldUser = findUserByUserName(user.getUserName());
        if (oldUser != null) {
            result = -2;
        } else {
            oldUser = findUserByEmail(user.getMail());
            if (oldUser != null) {
                result = -1;
            } else {
                //oldUser = findUserByPhone(user.getPhone());
                if (oldUser != null) {
                    result = 0;
                } else {
                    result = 1;
                }
            }
        }
        return result;
    }

    /**
     * Validates whether the user can be modified to the database.
     *
     * @param user: User to validate.
     * @return -2 if the username already exists in other user from the
     * database, -1 if the email already exists in other user from the database,
     * 0 if the phone already exists in other user from the database, 1 if you
     * can modified it.
     */
    private int validateForModifyUser(User user) {
        int result;
        //validate username
        User otherUser = validateDates("select * from users where userid != "
                + user.getId() + " and username = '" + user.getUserName() + "'");
        if (otherUser == null) {
            //Validate mail
            otherUser = validateDates("select * from users where userid != "
                    + user.getId() + " and mail = '" + user.getMail() + "'");
            if (otherUser == null) {
                otherUser = validateDates("select * from users where userid != "
                        + user.getId() + " and phone = '");
                if (otherUser == null) {
                    result = 1;
                } else {
                    result = 0;
                }
            } else {
                result = -1;
            }
        } else {
            result = -2;
        }

        return result;
    }

    /**
     * Search a user in the database with a given query
     *
     * @param query : Query with which the query will be performed
     * @return user object, null in case of error.
     */
    private User validateDates(String query) {
        User user = null;
        try {
            conn = DBConnect.getConnection();
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery(query);
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            user = null;
        }
        return user;
    }

}
