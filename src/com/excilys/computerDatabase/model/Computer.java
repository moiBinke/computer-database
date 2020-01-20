package com.excilys.computerDatabase.model;

import java.time.LocalDate;

/**
 * model Computer .
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class Computer {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	private Computer(ComputerBuilder computerBuilder) {
		this.id=computerBuilder.id;
		this.name=computerBuilder.name;
		this.introduced=computerBuilder.introduced;
		this.discontinued=computerBuilder.discontinued;
		this.company=computerBuilder.company;
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
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		if(introduced!=null) {
			if(this.getDiscontinued()!=null) {
				if(this.discontinued.isAfter(introduced)) {
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
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		if(discontinued!=null) {
			if(this.introduced!=null) {
				if(this.introduced.isBefore(discontinued)) {
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company.toString() + "]" + "\n";
	}
	
	
	/**
	 * La classe Builder
	 */
	public static class ComputerBuilder{
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public ComputerBuilder(String name) {
			this.name=name;
		}
		
		public ComputerBuilder initializeWithId(Long id) {
			this.id=id;
			return this;
		}
		
		public ComputerBuilder initializeWithIntroducedDate(LocalDate introduced) {
			this.introduced=introduced;
			return this;
		}
		
		public ComputerBuilder initializeWithDiscontinuedDate(LocalDate discontinued) {
			this.discontinued=discontinued;
			return this;
	    }
		
		public ComputerBuilder initializeWithCompany(Company company) {
			this.company=company;
			return this;
		}
		public Computer build() {
			Computer computer = new Computer(this);
			//Validators();
			return computer;
		}
	}
}
