package com.weixin.service.bean;

/**
 * ��Ӧ�Ĳ�Ʒ��Ҫչʾ����Ϣ
 * 
 * @author 13072937
 *
 */
public class Product {
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String number, String name, double price) {
		this.setNumber(number);
		this.setName(name);
		this.setPrice(price);
	}

	/**
	 * ���
	 */
	private String number;
	private String name;
	private double price;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
