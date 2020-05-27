package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Management;
import exception.SQLRuntimeException;

public class ManagementDao {

    public List<Management> select(Connection connection, int num) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    users.id, ");
            sql.append("    users.account, ");
            sql.append("    users.password, ");
            sql.append("    users.name, ");
            sql.append("    users.branch_office, ");
            sql.append("    users.department, ");
            sql.append("    users.status, ");
            sql.append("    users.created_date, ");
            sql.append("    users.updated_date ");
            sql.append("FROM users ");
            sql.append("ORDER BY created_date DESC limit " + num);

            ps = connection.prepareStatement(sql.toString());

            ResultSet rs = ps.executeQuery();

            List<Management> users = toManagements(rs);
            return users;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<Management> toManagements(ResultSet rs) throws SQLException {

        List<Management> users = new ArrayList<Management>();
        try {
            while (rs.next()) {
                Management user = new Management();
                user.setId(rs.getString("id"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setBranch_office(rs.getString("branch_office"));
                user.setDepartment(rs.getString("department"));
                user.setStatus(rs.getString("status"));
                user.setCreatedDate(rs.getTimestamp("created_date"));
                user.setUpdatedDate(rs.getTimestamp("updated_date"));

                users.add(user);
            }
            return users;
        } finally {
            close(rs);
        }
    }
}