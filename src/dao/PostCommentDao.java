package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.PostComment;
import exception.SQLRuntimeException;

public class PostCommentDao {

    public List<PostComment> select(Connection connection, int num) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    comments.id as id, ");
            sql.append("    comments.text as text, ");
            sql.append("    comments.user_id as user_id, ");
            sql.append("    comments.post_id as post_id, ");
            sql.append("    comments.created_date as created_date ");
            sql.append("FROM comments ");
            sql.append("INNER JOIN posts ");
            sql.append("ON comments.post_id = posts.id ");
            sql.append("ORDER BY created_date ASC limit " + num);

            ps = connection.prepareStatement(sql.toString());

            ResultSet rs = ps.executeQuery();

            List<PostComment> comments = toPostComments(rs);
            return comments;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<PostComment> toPostComments(ResultSet rs) throws SQLException {

        List<PostComment> comments = new ArrayList<PostComment>();
        try {
            while (rs.next()) {
                PostComment comment = new PostComment();
                comment.setId(rs.getString("id"));
                comment.setText(rs.getString("text"));
                comment.setUserId(rs.getString("user_id"));
                comment.setPostId(rs.getString("post_id"));
                comment.setCreatedDate(rs.getTimestamp("created_date"));
                comments.add(comment);
            }
            return comments;
        } finally {
        	close(rs);
        }
    }
}