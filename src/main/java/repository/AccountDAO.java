
package repository;

import context.DBContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO extends DBContext {
    // Method to update account details
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
        accountDAO.updateAccount(1, "New Name", "0123456789", "new_avatar.png", "new_password");
    }
}
