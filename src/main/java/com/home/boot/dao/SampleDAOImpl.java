package com.home.boot.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.home.boot.model.Student;
import com.home.boot.repository.SampleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class SampleDAOImpl implements SampleDAO {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private SampleRepository repository;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Student> findAll() {
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> criteriaQuery = criteria.createQuery(Student.class);
		Root<Student> root = criteriaQuery.from(Student.class);
		TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
		List<Student> list = query.getResultList();
		return list;
	}

	@Override
	public List<Student> getOne() {
		List<Predicate> andCriteria = new ArrayList<>();
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteria.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        andCriteria.add(criteria.equal(root.get("firstName"), "John"));
        Predicate[] predicates = new Predicate[andCriteria.size()];
        predicates = andCriteria.toArray(predicates);
        criteriaQuery.where(predicates);
        TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
        List<Student> list = query.getResultList();
        return list;
	}

	@Override
	public List<Student> getById() {
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteria.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.where(criteria.gt(root.get("id"), 12));
        TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
        List<Student> list = query.getResultList();
        return list;
	}
	
	@Override
    public void save() {
        Student std1 = new Student("std1", "std1");
        repository.save(std1);
    }

}
