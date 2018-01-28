package com.wzh.demo.domain;

import java.io.Serializable;

public class UserBean implements Serializable{

	private static final long serialVersionUID = -2959897964759682757L;
	
	private Long id;
	private String name;
	private String sex;
	private Long age;
	
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBean(Long id, String name, String sex, Long age) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", age=" + age + "]";
	}

}
