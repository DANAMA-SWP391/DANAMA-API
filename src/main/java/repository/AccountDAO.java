
package repository;

import context.DBContext;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void updateAccount(int UID, String name, String phone, String avatar, String password) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();

        // Tạo đối tượng Account với thông tin mới
        Account updatedAccount = new Account("New Name", "newemaisl@example.com", "1234256789", "newavat2ar.png", "newGo2ogleId", 2, "newPas2sword");

        // ID của account cần cập nhật
        int accountIdToUpdate = 4;

        // Cập nhật thông tin
        boolean result = accountDAO.updateAccountByID(accountIdToUpdate, updatedAccount);

        // Kiểm tra kết quả
        if (result) {
            System.out.println("Cập nhật tài khoản thành công.");
        } else {
            System.out.println("Cập nhật tài khoản thất bại.");
        }
    }
}