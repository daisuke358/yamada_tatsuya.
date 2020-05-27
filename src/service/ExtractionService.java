package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Extraction;
import dao.ExtractionDao;

public class ExtractionService {


	public List<Extraction> select(Extraction posts) {
        final int LIMIT_NUM = 1000;

        Connection connection = null;
        try {
            connection = getConnection();
            List<Extraction> postsB = new ExtractionDao().select(connection, posts, LIMIT_NUM);
            commit(connection);

            return postsB;
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
