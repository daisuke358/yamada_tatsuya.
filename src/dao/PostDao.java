package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Post;
import exception.SQLRuntimeException;

public class PostDao {

  public void insert(Connection connection, Post post) {

    PreparedStatement ps = null;
    try {
      StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO posts ( ");
            sql.append("    title, ");
            sql.append("    text, ");
            sql.append("    category, ");
            sql.append("    user_id, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");                                  // title
            sql.append("    ?, ");                                  // text
            sql.append("    ?, ");                                  // category
            sql.append("    ?, ");                                  // user_id
            sql.append("    CURRENT_TIMESTAMP, ");  // created_date
            sql.append("    CURRENT_TIMESTAMP ");       // updated_date
            sql.append(")");

      ps = connection.prepareStatement(sql.toString());

      ps.setString(1, post.getTitle());
      ps.setString(2, post.getText());
      ps.setString(3, post.getCategory());
      ps.setString(4, post.getUserId());

      ps.executeUpdate();
    } catch (SQLException e) {
        throw new SQLRuntimeException(e);
    } finally {
        close(ps);
    }
  }

	public void delete(Connection connection, Post post) {

	    PreparedStatement ps = null;
	    try {
	      StringBuilder sql = new StringBuilder();
	      sql.append("DELETE FROM posts WHERE id = ?");

	      ps = connection.prepareStatement(sql.toString());

	      ps.setInt(1, post.getId());

	      ps.executeUpdate();
	    } catch (SQLException e) {
	        throw new SQLRuntimeException(e);
	    } finally {
	        close(ps);
	    }
	}
}