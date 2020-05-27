package beans;

import java.io.Serializable;
import java.util.Date;

public class UserPost implements Serializable {

    private String id;
    private String title;
    private String text;
    private String category;
    private String userId;
    private Date created_date;
    private Date updated_date;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return created_date;
	}
	public void setCreatedDate(Date created_date) {
		this.created_date = created_date;
	}
	public Date getUpdatedDate() {
		return updated_date;
	}
	public void setUpdatedDate(Date updated_date) {
		this.updated_date = updated_date;
	}

}