package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import beans.UserAccount;
import beans.UserEditAccount;
import exception.SQLRuntimeException;

public class UserDao {

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

        }  catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public User select(Connection connection, String account, String password) {

        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM users WHERE account = ? AND password = ?";

            ps = connection.prepareStatement(sql);

            ps.setString(1, account);
            ps.setString(2, password);

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

                if (!rs.getString("status").equals("2")) {
                	users.add(user);
                }
                }
            return users;
        } finally {
            close(rs);
        }
    }

	public UserAccount select2(Connection connection , String account) {

        PreparedStatement ps = null;
        try {
            String sql = "SELECT account FROM users where account like ?";

            ps = connection.prepareStatement(sql);

            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();

            List<UserAccount> users = toUserAccounts(rs);
            if (users.isEmpty()) {
                return null;
            }else {
                return users.get(0);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }
	private List<UserAccount> toUserAccounts(ResultSet rs) throws SQLException{
        List<UserAccount> users = new ArrayList<UserAccount>();
			   try {
	    			while (rs.next()) {
	    				if(rs.getString("account").length() == 0) {
	    					UserAccount user = new UserAccount();
		            		user.setAccount(rs.getString("account"));
		                    users.add(user);
	    					users = null;
	    				}else {
	    					UserAccount user = new UserAccount();
		            		user.setAccount(rs.getString("account"));
		                    users.add(user);
	    				}
	        		}
	            return users;
	        }finally {
	            close(rs);
	        }
		}


	public UserEditAccount select4(Connection connection, String account, String oldAccount){
		PreparedStatement ps = null;
        try {
        	String sql = "SELECT account FROM users where account like ? and account not like ? ";

            ps = connection.prepareStatement(sql);

            ps.setString(1, account);
            ps.setString(2, oldAccount);
            ResultSet rs = ps.executeQuery();

            List<UserEditAccount> users = toUserEditAccounts(rs);
            if (users.isEmpty()) {
            	System.out.println(users);
                return null;
            }else {
                return users.get(0);
            }


        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

	private List<UserEditAccount> toUserEditAccounts(ResultSet rs) throws SQLException{
        List<UserEditAccount> users = new ArrayList<UserEditAccount>();
			   try {
	    			while (rs.next()) {
	    				if(rs.getString("account").length() == 0) {
	    					users = null;
	    				}else {
	    					UserEditAccount user = new UserEditAccount();
		            		user.setAccount(rs.getString("account"));
		                    users.add(user);
	    				}
	        		}
	            return users;
	        }finally {
	            close(rs);
	        }
		}

	public User select3(Connection connection, String id) {

        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";

            ps = connection.prepareStatement(sql);

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            List<User> users = toUsers3(rs);

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

    private List<User> toUsers3(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<User>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                	users.add(user);

                }
            return users;
        } finally {
            close(rs);
        }
    }

}
