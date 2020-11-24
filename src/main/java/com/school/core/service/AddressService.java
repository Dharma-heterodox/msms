package com.school.core.service;

import com.school.core.dto.AddressDto;

public interface AddressService {

	AddressDto getByOrganizationId(Long orgId);
	AddressDto getByStudentId(Long studentId);
	AddressDto getByEmployeeId(Long employeeId);
	AddressDto createAddress(AddressDto address);
}
