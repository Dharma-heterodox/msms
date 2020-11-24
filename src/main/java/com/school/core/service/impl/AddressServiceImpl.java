package com.school.core.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.dto.AddressDto;
import com.school.core.entity.Address;
import com.school.core.repo.AddressRepo;
import com.school.core.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private ModelMapper mapper;
	

	public AddressDto createAddress(AddressDto addressDto) {
		Address address = mapper.map(addressDto, Address.class);
		address = addressRepo.save(address);
		return mapper.map(address, AddressDto.class);
	}
	
	@Override
	public AddressDto getByOrganizationId(Long orgId) {
		return mapper.map(addressRepo.findByOrganizationId(orgId), AddressDto.class);
	}

	@Override
	public AddressDto getByStudentId(Long studentId) {
		return mapper.map(addressRepo.findByStudentId(studentId), AddressDto.class);
	}

	@Override
	public AddressDto getByEmployeeId(Long employeeId) {
		return mapper.map(addressRepo.findByEmployeeId(employeeId), AddressDto.class);
	}

}
