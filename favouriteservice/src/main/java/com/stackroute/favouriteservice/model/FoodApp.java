package com.stackroute.favouriteservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "foodapp")
public class FoodApp {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int itemId;
	
	@Column(name = "itemName")
	private String name;
	
	@Column(name = "itemNo")
	private String ndbno;
	
	@Column(name = "userId")
	private String userId;
	
	private String comments;

	public FoodApp(int itemId, String itemName, String itemNo, String userId, String comments) {
		super();
		this.itemId = itemId;
		this.name = itemName;
		this.ndbno = itemNo;
		this.userId = userId;
		this.comments = comments;
	}

	public FoodApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + itemId;
		result = prime * result + ((name== null) ? 0 : name.hashCode());
		result = prime * result + ((ndbno == null) ? 0 : ndbno.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodApp other = (FoodApp) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (itemId != other.itemId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ndbno == null) {
			if (other.ndbno != null)
				return false;
		} else if (!ndbno.equals(other.ndbno))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNdbno() {
		return ndbno;
	}

	public void setNdbno(String ndbno) {
		this.ndbno = ndbno;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
