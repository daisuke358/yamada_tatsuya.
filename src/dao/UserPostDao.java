package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UserPost;
import exception.SQLRuntimeException;

public class UserPostDao {

    public List<UserPost> select(Connection connection, int num) {

        PreparedStatement ps = null;
        try {
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

            List<UserPost> posts = toUserPosts(rs);
            return posts;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<UserPost> toUserPosts(ResultSet rs) throws SQLException {

        List<UserPost> posts = new ArrayList<UserPost>();
        try {
            while (rs.next()) {
                UserPost post = new UserPost();
                post.setId(rs.getString("id"));
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