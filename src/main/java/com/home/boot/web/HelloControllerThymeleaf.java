package com.home.boot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.home.boot.dao.SampleDAO;
import com.home.boot.model.Student;

@Controller
public class HelloControllerThymeleaf {
	
	@Autowired
	private SampleDAO sampleDAO;

	@GetMapping("/hellothymeleaf")
	public String getPermissionList(Model model) {
		List<Student> studentList = sampleDAO.findAll();
		model.addAttribute("studentList", studentList);
		return "helloworld.html";
	}
}
