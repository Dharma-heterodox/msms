package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	List<Employee> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT employee FROM Employee employee where employee.schoolId= ?1 and employee.mobile = ?2 and employee.active=true")
	Employee getEmployeeByMobile(Long schoolId, String mobile);
	
	@Query(
			  value = "SELECT employee FROM Employee employee where employee.userId = ?1 and employee.active=true")
	Employee getEmployeeByUserId(Long userId);
	
}
