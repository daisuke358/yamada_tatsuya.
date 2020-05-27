package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Comment;
import beans.PostComment;
import dao.CommentDao;
import dao.PostCommentDao;

public class CommentService {

    public void insert(Comment comment) {

        Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().insert(connection, comment);
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

	public List<PostComment> select() {
        final int LIMIT_NUM = 1000;

        Connection connection = null;
        try {
            connection = getConnection();
            List<PostComment> comments = new PostCommentDao().select(connection, LIMIT_NUM);
            commit(connection);

            return comments;
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

	public void delete(Comment comment) {

        Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().delete(connection, comment);
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