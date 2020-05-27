package beans;

import java.io.Serializable;
import java.util.Date;

public class PostComment implements Serializable {

    private String id;
    private String text;
    private String user_id;
    private String post_id;
    private Date created_date;
    private Date updated_date;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}

	public String[] getSplitedText() {
        return text.split("\n");
    }

	public void setText(String text) {
		this.text = text;
	}
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getPostId() {
		return post_id;
	}
	public void setPostId(String post_id) {
		this.post_id = post_id;
	}
	public Date getCreatedDate() {
		return created_date;
	}
	public void setCreatedDate(Date created_date) {
		this.created_date = created_date;
	}
	public Date getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

}