package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Extraction;
import exception.SQLRuntimeException;

public class ExtractionDao {
	public List<Extraction> select(Connection connection, Extraction post, int num) {

        PreparedStatement ps = null;
        try {
        	 if(!post.getStart().isEmpty() && !post.getEnd().isEmpty() && post.getCategory().isEmpty()){
        		StringBuilder sql = new StringBuilder();
                sql.append("SELECT ");
                sql.append("    posts.id as id, ");
                sql.append("    posts.title as title, ");
                sql.append("    posts.text as text, ");
                sql.append("    posts.category as category, ");
                sql.append("    posts.user_id as user_id, ");
                sql.append("    posts.created_date as created_date ");
                sql.append("FROM posts ");
                sql.append("INNER JOIN users ");
                sql.append("ON posts.user_id = users.id ");

                sql.append("WHERE posts.created_date  ");
                sql.append("BETWEEN ");
                sql.append("    ? ");
                sql.append("and");
                sql.append("    ? ");

                sql.append("ORDER BY created_date DESC limit " + num);
                ps = connection.prepareStatement(sql.toString());

                ps.setString(1, post.getStart());
                ps.setString(2, post.getEnd());

                ResultSet rs = ps.executeQuery();
                List<Extraction> posts = toExtractions(rs);
                return posts;
        	}else if(post.getStart().isEmpty() || post.getEnd().isEmpty() && !post.getCategory().isEmpty() ) {
        		 StringBuilder sql = new StringBuilder();
                 sql.append("SELECT ");
                 sql.append("    posts.id as id, ");
                 sql.append("    posts.title as title, ");
                 sql.append("    posts.text as text, ");
                 sql.append("    posts.category as category, ");
                 sql.append("    posts.user_id as user_id, ");
                 sql.append("    posts.created_date as created_date ");
                 sql.append("FROM posts ");
                 sql.append("INNER JOIN users ");
                 sql.append("ON posts.user_id = users.id ");
                 sql.append("WHERE posts.category  ");
                 sql.append("LIKE ");
                 sql.append("?");
                 sql.append("ORDER BY created_date DESC limit " + num);
                 ps = connection.prepareStatement(sql.toString());
                 ps.setString(1, "%" +post.getCategory() + "%");

                 ResultSet rs = ps.executeQuery();
                 List<Extraction> posts = toExtractions(rs);
                 return posts;
        	}else if(!post.getStart().isEmpty() && !post.getEnd().isEmpty() && !post.getCategory().isEmpty() ) {
	            StringBuilder sql = new StringBuilder();
	            sql.append("SELECT ");
	            sql.append("    posts.id as id, ");
	            sql.append("    posts.title as title, ");
	            sql.append("    posts.text as text, ");
	            sql.append("    posts.category as category, ");
	            sql.append("    posts.user_id as user_id, ");
	            sql.append("    posts.created_date as created_date ");
	            sql.append("FROM posts ");
	            sql.append("INNER JOIN users ");
	            sql.append("ON posts.user_id = users.id ");

	            sql.append("WHERE posts.created_date  ");
	            sql.append("BETWEEN ");
	            sql.append("    ? ");
	            sql.append("and");
	            sql.append("    ? ");

	            sql.append("AND posts.category  ");
	            sql.append("LIKE ");
	            sql.append("?");

	            sql.append("ORDER BY created_date DESC limit " + num);
	            ps = connection.prepareStatement(sql.toString());

	            ps.setString(1, post.getStart());
	            ps.setString(2, post.getEnd());
	            ps.setString(3, "%" +post.getCategory() + "%");

	            ResultSet rs = ps.executeQuery();
	            List<Extraction> posts = toExtractions(rs);
	            return posts;
        	}else if (post.getStart().isEmpty() || post.getEnd().isEmpty() && post.getCategory().isEmpty()) {
        		StringBuilder sql = new StringBuilder();
                sql.append("SELECT ");
                sql.append("    posts.id as id, ");
                sql.append("    posts.title as title, ");
                sql.append("    posts.text as text, ");
                sql.append("    posts.category as category, ");
                sql.append("    posts.user_id as user_id, ");
                sql.append("    posts.created_date as created_date ");
                sql.append("FROM posts ");
                sql.append("INNER JOIN users ");
                sql.append("ON posts.user_id = users.id ");

                sql.append("ORDER BY created_date DESC limit " + num);
                ps = connection.prepareStatement(sql.toString());

                ResultSet rs = ps.executeQuery();
                List<Extraction> posts = toExtractions(rs);
                return posts;
        	}

        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
		return null;
    }

	private List<Extraction> toExtractions(ResultSet rs) throws SQLException {

        List<Extraction> posts = new ArrayList<Extraction>();
        try {
            while (rs.next()) {
                Extraction post = new Extraction();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setText(rs.getString("text"));
                post.setCategory(rs.getString("category"));
                post.setUserId(rs.getString("user_id"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                posts.add(post);
            }
            return posts;
        } finally {
        	close(rs);
        }
    }
}
