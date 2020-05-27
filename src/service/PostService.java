package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Post;
import beans.UserPost;
import dao.PostDao;
import dao.UserPostDao;

public class PostService {

    public void insert(Post post) {

        Connection connection = null;
        try {
            connection = getConnection();
            new PostDao().insert(connection, post);
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

	public List<UserPost> select() {
        final int LIMIT_NUM = 1000;

        Connection connection = null;
        try {
            connection = getConnection();
            List<UserPost> posts = new UserPostDao().select(connection, LIMIT_NUM);
            commit(connection);

            return posts;
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

	public void delete(Post post) {

        Connection connection = null;
        try {
            connection = getConnection();
            new PostDao().delete(connection, post);
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
}
