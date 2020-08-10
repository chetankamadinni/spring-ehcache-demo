package com.io.chetan.kamadinni.springehcachedemo.initializer;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.io.chetan.kamadinni.springehcachedemo.entity.Employee;
import com.io.chetan.kamadinni.springehcachedemo.repository.EmployeeRepository;

@Component
public class DataInitializer {

	@Autowired
	EmployeeRepository empRepository;
	
	@PostConstruct
	public void init() {
		List<Employee> employeeList = getEmployeeList();
		empRepository.saveAll(employeeList);
	}

	private List<Employee> getEmployeeList() {
		
		return Arrays.asList(new Employee("Chetan", "Kamadinni"), new Employee("Sumit", "Ghorpade"));
		
	}

}
