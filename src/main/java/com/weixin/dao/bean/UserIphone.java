package com.weixin.dao.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.common.mydb.Column;
import com.common.mydb.Entity;
import com.common.mydb.Id;

@Entity(name = "user_iphone")
public class UserIphone implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String iphoneNumber;
	private int mask;
	private Timestamp maskDate;

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "iphone_number")
	public String getIphoneNumber() {
		return iphoneNumber;
	}

	public void setIphoneNumber(String iphoneNumber) {
		this.iphoneNumber = iphoneNumber;
	}
	
	@Column(name = "mask")
	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	@Column(name = "mask_date")
	public Timestamp getMaskDate() {
		return maskDate;
	}

	public void setMaskDate(Timestamp maskDate) {
		this.maskDate = maskDate;
	}

}
