package com.home.boot.model;

import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STUDENT", schema = "HOME")
public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Student() {}
	
	public Student(String firsName, String lastName) {
		this.firstName = firsName;
		this.lastName = lastName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "STUDENT_ID", unique = true, nullable = false)
	private BigInteger id;
	
	@Column(name = "FIRST_NM", unique = false, nullable = false, length = 250)
	private String firstName;
	
	@Column(name = "LAST_NM", unique = false, nullable = false, length = 250)
	private String lastName;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}