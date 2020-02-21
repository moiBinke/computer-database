package com.excilys.computerDatabase.daos;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerDatabase.logging.Logging;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
/**
 *DAO: implementation de ComputerDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-16 
 */
@Repository
public class ComputerDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Optional<Computer> addCommputer(Computer computer)  {
    	Long generatedId = null;
    	
    	try {
    		entityManager.persist(computer);
    		System.out.println(computer);
    	}
    	catch(Exception persistanceException) {
    		persistanceException.printStackTrace();
    		Logging.afficherMessageError("Error when Creating Entity");
    	}
    	finally {
    		return computer.getId()==null? Optional.empty() : Optional.of(computer);
    	}

	}

	 
	public ArrayList<Computer> getComputerList() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);
		TypedQuery<Computer> computers = entityManager.createQuery(criteriaQuery);
		return (ArrayList<Computer>) computers.getResultList();
	}

	 
	public Optional<Computer> getComputer(Long computerId) {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery=criteriaBuilder.createQuery();
		Root<Computer>root=criteriaQuery.from(Computer.class);
		Predicate byId=criteriaBuilder.equal(root.get("id"), computerId);
		criteriaQuery.select(root);
		criteriaQuery.where(byId);
		
		TypedQuery<Computer> computerQuery=entityManager.createQuery(criteriaQuery);
		Computer computer = computerQuery.getSingleResult();
		Logging.afficherMessageError("le id est :"+computerId);
		if(computer!=null ) {
			return Optional.of(computer);
		}
		return Optional.empty();
	}
	
	 @Transactional
	public Optional<Computer> deleteComputer(Long computerId) {
		Optional<Computer> computer=getComputer(computerId);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = criteriaBuilder.createCriteriaDelete(Computer.class);
		Root<Computer>root = criteriaDelete.from(Computer.class);
		Predicate byId = criteriaBuilder.equal(root.get("id"),computerId);
		criteriaDelete.where(byId);
		int estSupprime=entityManager.createQuery(criteriaDelete).executeUpdate();
		return estSupprime==1? computer : Optional.empty();

	}
	 
	
    @Transactional 
	public Optional<Computer> updateComputerName(Long computerId,String name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("name", name);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), computerId));
		Query update = entityManager.createQuery(criteriaUpdate);
		update.executeUpdate();
		Optional<Computer> computerToUpdate = getComputer(computerId);
		return computerToUpdate;
	}

	@Transactional
	public Optional<Computer> updateComputerIntroducedDate(Long computerToUpdateIntroducedDateId, LocalDate newIntroducedDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("introduced", newIntroducedDate);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), computerToUpdateIntroducedDateId));
		Query update = entityManager.createQuery(criteriaUpdate);
		update.executeUpdate();
		Optional<Computer> computerToUpdate = getComputer(computerToUpdateIntroducedDateId);
		return computerToUpdate;

	}

	@Transactional
	public Optional<Computer> updateComputerDiscontinuedDate(Long computerToUpdateDiscontinuedDateId,LocalDate newDiscontinuedDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("discontinued", newDiscontinuedDate);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), computerToUpdateDiscontinuedDateId));
		Query update = entityManager.createQuery(criteriaUpdate);
		update.executeUpdate();
		Optional<Computer> computerToUpdate = getComputer(computerToUpdateDiscontinuedDateId);
		return computerToUpdate;

	}

	@Transactional
	public Optional<Computer> updateComputerCompany(Long computerToUpdateCompanyId, Long newCompanyId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("company_id", newCompanyId);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), computerToUpdateCompanyId));
		Query update = entityManager.createQuery(criteriaUpdate);
		update.executeUpdate();
		Optional<Computer> computerToUpdate = getComputer(computerToUpdateCompanyId);
		return computerToUpdate;
	}
	

	public Computer updateComputer(Computer computerToUpdate) {
		updateComputerName(computerToUpdate.getId(),computerToUpdate.getName()).get();
		
		updateComputerIntroducedDate(computerToUpdate.getId(),computerToUpdate.getIntroduced()).get();
		updateComputerDiscontinuedDate(computerToUpdate.getId(),computerToUpdate.getDiscontinued()).get();
		computerToUpdate=updateComputerCompany(computerToUpdate.getId(),computerToUpdate.getCompany().getId()).get();
		return computerToUpdate;
	}
	
	
	private CriteriaQuery<Computer> getOrderBy(CriteriaQuery criteriaQuery,CriteriaBuilder criteriaBuilder,Root<Computer> root,String orderBy) {
		
		if(orderBy.endsWith(" ASC")) {
			orderBy=orderBy.split(" ")[0];
			if(orderBy.startsWith("company")) {
				String[] splitted = orderBy.split("\\."); 
				return criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(splitted[0]).get(splitted[1])));
			}
			return criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(orderBy)));
		}
		orderBy=orderBy.split(" ")[0];
		if(orderBy.startsWith("company")) {
			String[] splitted = orderBy.split("\\."); 
			return criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(splitted[0]).get(splitted[1])));
		}
		
		return criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(orderBy)));
	}
	
	
	private CriteriaQuery<Computer> geetOrderBy(CriteriaQuery criteriaQuery,CriteriaBuilder criteriaBuilder,Root<Computer> root,String orderBy) {
		
		if(orderBy.endsWith(" ASC")) {
			orderBy=orderBy.split(" ")[0];
			String[] splitted = orderBy.split("\\."); 
			return criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(splitted[0]).get(splitted[1])));
		}
		
		orderBy=orderBy.split(" ")[0];
		return criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(orderBy)));
	}
	
	
	public ArrayList<Computer> getComputerListPage(int ligneDebut, int  taillePage, String orderBy ) {
//		SqlParameterSource namedParameters=new MapSqlParameterSource().addValue("ligneDebut",ligneDebut)
//																	  .addValue("taillePage", taillePage);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class); 
		
		TypedQuery<Computer> computers=null;
		if(orderBy.startsWith("any")) {
			computers = entityManager.createQuery(criteriaQuery).setFirstResult(ligneDebut).setMaxResults(taillePage);
		}
		else {
			criteriaQuery=getOrderBy(criteriaQuery,criteriaBuilder,root, orderBy);
			computers = entityManager.createQuery(criteriaQuery).setFirstResult(ligneDebut).setMaxResults(taillePage);
		}
	
		return (ArrayList<Computer>) computers.getResultList();

		
	}

	
	public int size() {
		return getComputerList().size();
	}
	
	
	public ArrayList<Computer> search(String search) {
		search="%"+search.toLowerCase()+"%";
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery=criteriaBuilder.createQuery();
		Root<Computer>root=criteriaQuery.from(Computer.class); 
		criteriaQuery.select(root);
		Join<Company,Computer> companyParty=root.join("company",JoinType.LEFT);
		
		Predicate byComputerName=criteriaBuilder.like(root.get("name"), search);
		Predicate byCompanyName=criteriaBuilder.like(companyParty.get("name"), search);
		Predicate orSearch=criteriaBuilder.or(byComputerName,byCompanyName);
		criteriaQuery.where(orSearch);
		
		return (ArrayList<Computer>) entityManager.createQuery(criteriaQuery).getResultList() ;

	}

}
