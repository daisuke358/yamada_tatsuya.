package beans;

import java.io.Serializable;
import java.util.Date;

public class UserEdit implements Serializable {

    private int id;
    private String account;
    private String password;
    private String name;
    private int branch_office;
    private int department;
    private int status;
    private Date createdDate;
    private Date updatedDate;
    private String passwordB;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getBranch_office() {
		return branch_office;
	}
	public void setBranch_office(int branch_office) {
		this.branch_office = branch_office;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswordB() {
		return passwordB;
	}
	public void setPasswordB(String passwordB) {
		this.passwordB = passwordB;
	}



}