package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Management;
import beans.User;
import beans.UserAccount;
import beans.UserEditAccount;
import dao.ManagementDao;
import dao.UserDao;
import utils.CipherUtil;


public class UserService {

    public void insert(User user) {

        Connection connection = null;
        try {
            // パスワード暗号化
            String encPassword = CipherUtil.encrypt(user.getPassword());
            user.setPassword(encPassword);

            connection = getConnection();
            new UserDao().insert(connection, user);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public User select(String account, String password) {

        Connection connection = null;
        try {
            // パスワード暗号化
            String encPassword = CipherUtil.encrypt(password);

            connection = getConnection();
            User user = new UserDao().select(connection, account, encPassword);
            commit(connection);

            return user;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

	public List<Management> select() {
        final int LIMIT_NUM = 500;

        Connection connection = null;
        try {
            connection = getConnection();
            List<Management> users = new ManagementDao().select(connection, LIMIT_NUM);
            commit(connection);

            return users;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public UserAccount select2(String string) {

        Connection connection = null;
        try {

            connection = getConnection();
            UserAccount user = new UserDao().select2(connection, string);
            commit(connection);

            return user;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

	public UserEditAccount select4(String account, String oldAccount) {
		Connection connection = null;
        try {

            connection = getConnection();
            UserEditAccount user = new UserDao().select4(connection, account, oldAccount);
            commit(connection);

            return user;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

}
