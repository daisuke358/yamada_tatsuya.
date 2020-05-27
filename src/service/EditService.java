package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;

import beans.User;
import beans.UserEdit;
import dao.EditDao;
import dao.UserDao;
import utils.CipherUtil;

public class EditService {
    public void update(UserEdit user) {

        Connection connection = null;
        try {
            // パスワード暗号化
            String encPassword = CipherUtil.encrypt(user.getPassword());
            user.setPassword(encPassword);

            connection = getConnection();
            new EditDao().update(connection, user);
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

    public User select(String userId) {

        Connection connection = null;
        try {
            connection = getConnection();
            User user = new EditDao().select(connection, userId);
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

	public void statusUpdate(UserEdit user) {

		    Connection connection = null;
		    try {
		        connection = getConnection();
		        new EditDao().statusUpdate(connection, user);
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

    public User select3(String id) {
        Connection connection = null;
        try {

            connection = getConnection();
            User user = new UserDao().select3(connection, id);
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
