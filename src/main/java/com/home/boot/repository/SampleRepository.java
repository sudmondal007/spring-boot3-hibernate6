package com.home.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.boot.model.Student;

public interface SampleRepository extends JpaRepository<Student, Integer> {

}
