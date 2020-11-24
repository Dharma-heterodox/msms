package com.school.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

	Address findByOrganizationId(Long orgId);
	Address findByStudentId(Long studentId);
	Address findByEmployeeId(Long employeeId);
	
}
