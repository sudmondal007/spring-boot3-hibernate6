package com.home.boot.dao;

import java.util.List;

import com.home.boot.model.Student;

public interface SampleDAO {
	List<Student> findAll();
	List<Student> getOne();
	List<Student> getById();
	void save();
}
