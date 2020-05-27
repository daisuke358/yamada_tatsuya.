
package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import beans.UserEdit;
import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;

public class EditDao {

    public void insert(Connection connection, User user) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO users ( ");
            sql.append("    account, ");
            sql.append("    password, ");
            sql.append("    name, ");
            sql.append("    branch_office, ");
            sql.append("    department, ");
            sql.append("    status, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");                                  // account
            sql.append("    ?, ");                                  // password
            sql.append("    ?, ");                                  // name
            sql.append("    ?, ");                                  // branch_office
            sql.append("    ?, ");                                  // department
            sql.append("    ?, ");                                  // status
            sql.append("    CURRENT_TIMESTAMP, ");  // created_date
            sql.append("    CURRENT_TIMESTAMP ");       // updated_date
            sql.append(")");

            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, user.getAccount());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getBranch_office());
            ps.setString(5, user.getDepartment());
            ps.setString(6, user.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public User select(Connection connection, String id) {

        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";

            ps = connection.prepareStatement(sql);

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            List<User> users = toUsers(rs);
            if (users.isEmpty()) {
                return null;
            } else if (2 <= users.size()) {
                throw new IllegalStateException("ユーザーが重複しています");
            } else {
                return users.get(0);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<User> toUsers(ResultSet rs) throws SQLException {

        List<User> users = new ArrayList<User>();
        try {
            while (rs.next()) {
                User user = new User();
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

	public void update(Connection connection, UserEdit user) {

		    PreparedStatement ps = null;
		    try {
		        StringBuilder sql = new StringBuilder();
		        if(user.getPassword().equals(null)) {
		        	  sql.append("UPDATE users SET ");
				        sql.append("    account = ?, ");
				        sql.append("    name = ?, ");
				        sql.append("    branch_office = ?, ");
				        sql.append("    department = ?, ");
				        sql.append("    status = ?, ");
				        sql.append("    updated_date = CURRENT_TIMESTAMP ");
				        sql.append("WHERE id = ?");

				        ps = connection.prepareStatement(sql.toString());

				        ps.setString(1, user.getAccount());
				        ps.setString(2, user.getName());
				        ps.setInt(3, user.getBranch_office());
				        ps.setInt(4, user.getDepartment());
				        ps.setInt(5, user.getStatus());
				        ps.setInt(6, user.getId());

				        ps.toString();

		        }else {
		        	  sql.append("UPDATE users SET ");
				        sql.append("    account = ?, ");
				        sql.append("    password = ?, ");
				        sql.append("    name = ?, ");
				        sql.append("    branch_office = ?, ");
				        sql.append("    department = ?, ");
				        sql.append("    status = ?, ");
				        sql.append("    updated_date = CURRENT_TIMESTAMP ");
				        sql.append("WHERE id = ?");

				        ps = connection.prepareStatement(sql.toString());

				        ps.setString(1, user.getAccount());
				        ps.setString(2, user.getPassword());
				        ps.setString(3, user.getName());
				        ps.setInt(4, user.getBranch_office());
				        ps.setInt(5, user.getDepartment());
				        ps.setInt(6, user.getStatus());
				        ps.setInt(7, user.getId());

				        ps.toString();
		        }

		        int count = ps.executeUpdate();
		        if (count == 0) {
		            throw new NoRowsUpdatedRuntimeException();
		        }
		    } catch (SQLException e) {
		        throw new SQLRuntimeException(e);
		    } finally {
		        close(ps);
		    }
		}

	public void statusUpdate(Connection connection, UserEdit user) {
		PreparedStatement ps = null;
	    try {
	        StringBuilder sql = new StringBuilder();
	        sql.append("UPDATE users SET ");
	        sql.append("    status = ?, ");
	        sql.append("    updated_date = CURRENT_TIMESTAMP ");
	        sql.append("WHERE id = ?");

	        ps = connection.prepareStatement(sql.toString());

	        ps.setInt(1, user.getStatus());
	        ps.setInt(2, user.getId());

	        ps.toString();

	        int count = ps.executeUpdate();
	        if (count == 0) {
	            throw new NoRowsUpdatedRuntimeException();
	        }
	    } catch (SQLException e) {
	        throw new SQLRuntimeException(e);
	    } finally {
	        close(ps);
	    }

	}
}