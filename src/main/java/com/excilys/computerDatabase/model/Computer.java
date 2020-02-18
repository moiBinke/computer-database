package com.excilys.computerDatabase.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.excilys.computerDatabase.validators.ComputerValidator;
/**
 * model Computer .
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
@Entity
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "introduced")
	private LocalDate introduced;
	@Column(name = "discontinued")
	private LocalDate discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	private Computer() {
		
	}
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
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
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
				 + "\n";
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
			return computer;
		}
	}
}
