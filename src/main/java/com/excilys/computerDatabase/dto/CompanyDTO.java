package com.excilys.computerDatabase.dto;

public class CompanyDTO {

	private Long id;
	private String name;
	
	public CompanyDTO(long id) {
		this.id=id;
	}
	public CompanyDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
