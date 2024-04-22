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

	@GetMapping("/viewstudents")
	public String getPermissionList(Model model) {
		List<Student> studentList = sampleDAO.findAll();
		if(CollectionUtils.isNotEmpty(studentList)) {
			Set<Student> studentSet = new HashSet<Student>(studentList);
			model.addAttribute("studentList", studentSet);
		}
		return "helloworld.html";
	}
}
