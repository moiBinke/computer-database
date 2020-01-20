package com.excilys.computerDatabase.model;

/**
 * model Company .
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class Company {

	private Long id;
	private String name;
	
	public Company() {
		super();
	}

	public Company( Long id ,String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	public Company(Long id) {
		// TODO Auto-generated constructor stub
		this.id=id;
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
		if(name!=null)
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]"+"\n";
	}
	
	
	
}
