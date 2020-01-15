package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Company;

public class CompanyMapper {

	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Company company=new Company();
		company.setId(resultset.getLong("id"));
		company.setName(resultset.getString("name"));
		return company;
	}
}
