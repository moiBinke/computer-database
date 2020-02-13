package com.excilys.computerDatabase.daos;



import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
/**
 *DAO: implementation de ComputerDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-16 
 */
@Repository
public class ComputerDAO {
	
	/**
	 * Les requetes utilisées pour computer
	 */
	public static final String ADD_COMPUTER= "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :companyId);";
	public static final String GET_COMPUTER_BY_ID= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE computer.id=:id";
	public static final String GET_List_COMPUTER= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY computer.id ASC;";
	public static final String DELETE_COMPUTER= "DELETE FROM computer WHERE id=:id";
	public static final String UPDATE_COMPUTER_NAME= "UPDATE computer SET name=:name WHERE id=:id;";
	public static final String UPDATE_COMPUTER_INTRODUCED_DATE = "UPDATE computer SET introduced=:introduced WHERE id=:id;";
	public static final String UPDATE_COMPUTER_DISCONTINUED_DATE = "UPDATE computer SET discontinued=:discontinued WHERE id=:id;";
	public static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=:companyId WHERE id=:id;";
	public static final String GET_PAGE_COMPUTER = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id LIMIT :ligneDebut,:taillePage";
	public static final String COUNT = "SELECT COUNT(*) FROM computer;";
	public static final String DELETE_COMPUTER_WITH_COMPANY_ID="DELETE FROM computer WHERE company_id=?;";
	public static final String SEARCH="SELECT computer.id as computer_id, computer.name as computer_name , computer.introduced, computer.discontinued, computer.company_id, company.name as company_name  FROM computer LEFT JOIN company on company.id=computer.company_id WHERE LOWER(computer.name) LIKE :search OR  LOWER(company.name) LIKE :search OR introduced LIKE :search OR discontinued LIKE :search;";
	public static final String GET_ORDER_BY="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY ";
	public static final String LIMIT=" LIMIT :ligneDebut,:taillePage;";
	/**
	 * Construction du singleton:
	 */

	private DataSource dataSource;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	ComputerMapper computerMapper;
	private ComputerDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate,DataSource dataSource) {
		this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
		this.dataSource=dataSource;
		computerMapper=new ComputerMapper();
		
	}

	 
	public Optional<Computer> addCommputer(Computer computer) throws ParseException {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("name",computer.getName())
																	  .addValue("introduced", computer.getIntroduced())
																	  .addValue("discontinued", computer.getDiscontinued())
																	  .addValue("companyId", computer.getCompany().getId());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(ADD_COMPUTER, namedParameters, keyHolder);
		Long idComputer=keyHolder.getKey().longValue();
	    return getComputer((long)idComputer);
	}

	 
	public ArrayList<Computer> getComputerList() {
		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(GET_List_COMPUTER, this.computerMapper);
	       
	}

	 
	public Optional<Computer> getComputer(Long computerId) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("id",computerId);
	    return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(GET_COMPUTER_BY_ID, namedParameters, this.computerMapper));
	}
	
	 
	public Optional<Computer> deleteComputer(Long computerId) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("id",computerId);
		Optional<Computer> computer= getComputer(computerId);
		int resultat=namedParameterJdbcTemplate.update(DELETE_COMPUTER, namedParameters);
		if(resultat==0) {
			return computer.empty();
		}
	    return computer;
	}
	 
	public Optional<Computer> updateComputerName(Long computerId,String name) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("id",computerId)
																	  .addValue("name", name);
	    Optional.ofNullable(namedParameterJdbcTemplate.update(UPDATE_COMPUTER_NAME, namedParameters));

		return getComputer(computerId);
	}

	 
	public Optional<Computer> updateComputerIntroducedDate(Long computerToUpdateIntroducedDateId, LocalDate newIntroducedDate) {
	        SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("introduced",newIntroducedDate)
	        															  .addValue("id", computerToUpdateIntroducedDateId);
			Optional.ofNullable(namedParameterJdbcTemplate.update(UPDATE_COMPUTER_INTRODUCED_DATE, namedParameters));
			
			return getComputer(computerToUpdateIntroducedDateId);
	}

	 
	public Optional<Computer> updateComputerDiscontinuedDate(Long computerToUpdateDiscontinuedDateId,LocalDate newDiscontinuedDate) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("discontinued",newDiscontinuedDate)
			  														  .addValue("id", computerToUpdateDiscontinuedDateId);
		Optional.ofNullable(namedParameterJdbcTemplate.update(UPDATE_COMPUTER_DISCONTINUED_DATE, namedParameters));
		
		return getComputer(computerToUpdateDiscontinuedDateId);
	}

	 
	public Optional<Computer> updateComputerCompany(Long computerToUpdateCompanyId, Long newCompanyId) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("companyId",newCompanyId)
						  											  .addValue("id", computerToUpdateCompanyId);
		Optional.ofNullable(namedParameterJdbcTemplate.update(UPDATE_COMPUTER_COMPANY_ID, namedParameters));
		
		return getComputer(computerToUpdateCompanyId);
}
	 
	public ArrayList<Computer> getComputerListPage(int ligneDebut, int  taillePage, String orderBy ) {
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("ligneDebut",ligneDebut)
																	  .addValue("taillePage", taillePage);
		if(orderBy.startsWith("any")) {
			return  (ArrayList<Computer>) namedParameterJdbcTemplate.query(GET_PAGE_COMPUTER, namedParameters, this.computerMapper);
		}
		else {
			return  (ArrayList<Computer>) namedParameterJdbcTemplate.query(GET_ORDER_BY+orderBy+LIMIT, namedParameters, this.computerMapper);
		}
		
		
	}

	public int size() {
		return getComputerList().size();
	}
	public Computer updateComputer(Computer computerToUpdate) {
		updateComputerName(computerToUpdate.getId(),computerToUpdate.getName()).get();
		updateComputerIntroducedDate(computerToUpdate.getId(),computerToUpdate.getIntroduced()).get();
		updateComputerDiscontinuedDate(computerToUpdate.getId(),computerToUpdate.getDiscontinued()).get();
		computerToUpdate=updateComputerCompany(computerToUpdate.getId(),computerToUpdate.getCompany().getId()).get();
		return computerToUpdate;
	}
	public ArrayList<Computer> search(String search) {
		search="%"+search.toLowerCase()+"%";
		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("search",search)
																	  .addValue("search", search)
																	  .addValue("search", search)
																	  .addValue("search", search);
		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(SEARCH, namedParameters,this.computerMapper);
		
	       

	}

}
