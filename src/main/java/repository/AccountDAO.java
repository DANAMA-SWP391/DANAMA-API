
package repository;

import context.DBContext;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO extends DBContext {

    public boolean addAccount(Account account) {
        String sql = "INSERT INTO Account (name, email, phone, avatar, googleId, roleId, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.setString(4, account.getAvatar());
            ps.setString(5, account.getGoogleId());
            ps.setInt(6, account.getRoleId());
            ps.setString(7, account.getPassword());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            try {
                if (ps != null) ps.close(); // Đảm bảo đóng PreparedStatement sau khi sử dụng
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateAccountByID(int accountId, Account account) {
        String sql = "UPDATE Account SET name = ?, email = ?, phone = ?, avatar = ?, googleId = ?, roleId = ?, password = ? WHERE UID = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.setString(4, account.getAvatar());
            ps.setString(5, account.getGoogleId());
            ps.setInt(6, account.getRoleId());
            ps.setString(7, account.getPassword());
            ps.setInt(8, accountId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateAccount(int UID, String name, String phone, String avatar, String password) {
        String sql = "UPDATE Account SET name = ?, phone = ?, avatar = ?, password = ? WHERE UID = ?";
        PreparedStatement statement = null;

        try {
            // Sử dụng connection từ DBContext
            statement = connection.prepareStatement(sql);

            // Set các tham số cho câu lệnh SQL
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, avatar);
            statement.setString(4, password);
            statement.setInt(5, UID);

            // Thực thi câu lệnh
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean updateProfile(Account account) {

        String sql = "UPDATE Account SET name = ?, phone = ?, avatar = ? WHERE UID = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.setString(3, account.getAvatar());
            ps.setInt(4, account.getUID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean updatePassword(int UID, String password) {
        String sql = "UPDATE Account SET password = ? WHERE UID = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, UID);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean banAccount(int UID) {

        String sql = "UPDATE Account SET roleId = 0 WHERE UID = ?";
        PreparedStatement statement = null;

        try {
            // Use the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set the UID parameter for the SQL query
            statement.setInt(1, UID);

            // Execute the update statement
            int rowsAffected = statement.executeUpdate();

            // Return true if at least one row was updated
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteAccount(int UID) {

        String sql = "DELETE FROM Account WHERE UID = ?";
        PreparedStatement statement = null;

        try {
            // Use the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set the UID parameter for the SQL query
            statement.setInt(1, UID);

            // Execute the delete statement
            int rowsAffected = statement.executeUpdate();

            // Return true if at least one row was deleted
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Account getAccountById(int UID) {
        Account account = null;
        String sql = "SELECT UID, name, email, phone, avatar, googleId, roleId FROM Account WHERE UID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setUID(resultSet.getInt("UID"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setPhone(resultSet.getString("phone"));
                account.setAvatar(resultSet.getString("avatar"));
                account.setGoogleId(resultSet.getString("googleId"));
                account.setRoleId(resultSet.getInt("roleId"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    public Account getAccountByGoogleId(String googleId) {
        Account account = null;
        String sql = "SELECT UID, name, email, phone, avatar, googleId, roleId FROM Account WHERE googleId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, googleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setUID(resultSet.getInt("UID"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setPhone(resultSet.getString("phone"));
                account.setAvatar(resultSet.getString("avatar"));
                account.setGoogleId(resultSet.getString("googleId"));
                account.setRoleId(resultSet.getInt("roleId"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public ArrayList<Account> getListAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT UID, name, email, phone, avatar, googleId, roleId, password FROM Account";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setUID(resultSet.getInt("UID"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setPhone(resultSet.getString("phone"));
                account.setAvatar(resultSet.getString("avatar"));
                account.setGoogleId(resultSet.getString("googleId"));
                account.setRoleId(resultSet.getInt("roleId"));
                accounts.add(account);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
//
//        // Tạo đối tượng Account với thông tin mới
//        Account updatedAccount = new Account("New Name", "newemaisl@example.com", "1234256789", "newavat2ar.png", "newGo2ogleId", 2, "newPas2sword");
//
//        // ID của account cần cập nhật
//        int accountIdToUpdate = 4;
//
//        // Cập nhật thông tin
//        boolean result = accountDAO.updateAccountByID(accountIdToUpdate, updatedAccount);
//
//        // Kiểm tra kết quả
//        if (result) {
//            System.out.println("Cập nhật tài khoản thành công.");
//        } else {
//            System.out.println("Cập nhật tài khoản thất bại.");
//        }
        Account account = accountDAO.getAccountById(1);
        System.out.println(account);
    }
}