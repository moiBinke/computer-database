package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.model.Company;

/**
 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Company Bean 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class CompanyMapper implements RowMapper<Company>  {

	/**
	 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en company Bean 
	 *@param resultSet de type ResultSet(ligne  SQL)
	 *@throws SQLException
	 *@return company: un objet company
	 */
	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Company company=new Company();
		company.setId(resultset.getLong("id"));
		company.setName(resultset.getString("name"));
		return company;
	}
	
	public static CompanyDTO mapFromCompanyToCompanyDto(Company company) {
		if(company==null) {
			return null;
		}
		CompanyDTO companyDto=new CompanyDTO();
		companyDto.setId(company.getId());
		companyDto.setName(company.getName());
		return companyDto;
	}
	public static Company mapFromCompanyDtoToCompany(CompanyDTO companyDto) {
		Company company=new Company();
		company.setId(companyDto.getId());
		company.setName(companyDto.getName());
		return company;
	}

	public static Optional<CompanyDTO> mapFromCompanyToCompanyDto(Optional<Company> company) {
		if(company.isPresent()) {
			CompanyDTO companyDto=new CompanyDTO();
			companyDto.setId(company.get().getId());
			companyDto.setName(company.get().getName());
			return Optional.of(companyDto);
		}
		else return Optional.empty();
	}

	@Override
	public Company mapRow(ResultSet resultset, int rowNum) throws SQLException {
		return mapCompany(resultset);
	}
}
