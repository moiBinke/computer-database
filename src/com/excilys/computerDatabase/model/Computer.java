package com.excilys.computerDatabase.model;

import  java.sql.Timestamp;

/**
 * model Computer .
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class Computer {
	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Long company_id;
	public Computer() {
		super();
	}
	public Computer(String name) {
		super();
		this.name = name;
	}
	
	public Computer( String name, Timestamp introduced) {
		super();
		this.name = name;
		this.introduced = introduced;
	}
	
	public Computer( String name, Timestamp introduced, Timestamp discontinued, Long company_id) {
		
		super();
		this.name = name;
		if(discontinued.after(introduced)) {
		this.introduced = introduced;
		this.discontinued = discontinued;
		}
		this.company_id = company_id;
	}
	public Computer( String name, Timestamp introduced, Timestamp discontinued) {
		super();
		this.name = name;
		if(discontinued.after(introduced)) {
			this.introduced = introduced;
			this.discontinued = discontinued;
			}
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
	public Timestamp getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Timestamp introduced) {
		if(introduced!=null) {
			if(this.getDiscontinued()!=null) {
				if(this.discontinued.after(introduced)) {
					this.introduced = introduced;
				}
				else {
					System.out.println("discontinued doit etre superieur à introduced");
				}
			}
			else {
				this.introduced = introduced;
			}
			
		}
		else {
			this.introduced = introduced;
		}
	}
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Timestamp discontinued) {
		if(discontinued!=null) {
			if(this.introduced!=null) {
				if(this.introduced.before(discontinued)) {
					this.discontinued = discontinued;
				}
				else {
					System.out.println("discontinued doit etre superieur à introduced");
				}
			}
			else {
				this.discontinued = discontinued;
			}
			
		}
		else {
			this.discontinued=discontinued;
		}
		
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + company_id + "]"+"\n";
	}
	
	
}
