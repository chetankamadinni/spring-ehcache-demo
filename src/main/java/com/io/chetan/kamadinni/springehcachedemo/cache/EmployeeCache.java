package com.io.chetan.kamadinni.springehcachedemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.io.chetan.kamadinni.springehcachedemo.entity.Employee;
import com.io.chetan.kamadinni.springehcachedemo.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeCache {

	@Autowired
	EmployeeRepository empRepository;

	@Cacheable(value = "twenty-second-cache", key = "#empName")
	public Employee getEmployee(String empName) {

		log.info("Get Employee {} from repository", empName);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			log.error("Exception while calling Thread.sleep : " + e);
		}

		return empRepository.findByFirstName(empName);
	}

}
