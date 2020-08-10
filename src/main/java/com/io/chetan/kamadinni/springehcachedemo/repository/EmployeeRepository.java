package com.io.chetan.kamadinni.springehcachedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.chetan.kamadinni.springehcachedemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByFirstName(String empName);

}
