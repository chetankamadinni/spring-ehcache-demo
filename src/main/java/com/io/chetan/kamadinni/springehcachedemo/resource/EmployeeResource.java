package com.io.chetan.kamadinni.springehcachedemo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.io.chetan.kamadinni.springehcachedemo.cache.EmployeeCache;
import com.io.chetan.kamadinni.springehcachedemo.entity.Employee;

@RestController
@RequestMapping("api")
public class EmployeeResource {
	
	@Autowired
	EmployeeCache empCache;
	
	@GetMapping("/employee/{empName}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("empName") String empName){
		Employee employee = empCache.getEmployee(empName); 
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

}
