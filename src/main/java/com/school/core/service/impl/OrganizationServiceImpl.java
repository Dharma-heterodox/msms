package com.school.core.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.dto.AddressDto;
import com.school.core.dto.OrganizationDto;
import com.school.core.entity.Organization;
import com.school.core.entity.User;
import com.school.core.repo.OrganizationRepo;
import com.school.core.service.AddressService;
import com.school.core.service.GradeService;
import com.school.core.service.OrganizationService;
import com.school.core.service.UserService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService{

	@Autowired
	private OrganizationRepo organizationRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private AddressService addressService;
	
	public Organization getOrganization(Long id) {
		return organizationRepo.getOne(id);
	}
	
	public OrganizationDto getOrganizationDetail(Long id)throws Exception {
		Organization org = organizationRepo.getOne(id);
		if(org == null) {
			return null;
		}
		OrganizationDto organizationDto = mapper.map(org, OrganizationDto.class);
		organizationDto.setGrades(gradeService.getAllGradesBySchoolId(organizationDto.getId()));
		return organizationDto;
	}
	
	public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
		//AddressDto addressDto = organizationDto.getAddress();
		Organization organization = mapper.map(organizationDto, Organization.class);
        Organization updatedOrganization = organizationRepo.save(organization);
		/*
		 * if(addressDto != null && addressDto.getAddressLine1() != null) {
		 * addressDto.setOrganizationId(updatedOrganization.getId());
		 * addressService.createAddress(addressDto); }
		 */
//        updatedOrganization.setTenantId(updatedOrganization.getId().intValue());
        updatedOrganization = organizationRepo.save(updatedOrganization);
        createOrgAccount(updatedOrganization);
        return mapper.map(updatedOrganization, OrganizationDto.class);
    }
	
	private User createOrgAccount(Organization organization) {
		User user = new User();
		user.setUserName(organization.getEmail());
		user.setPassword("test123$");
		user.setEmail(organization.getEmail());
		user.setActive(true);
		Set<Organization> orgs = new HashSet<Organization>();
		orgs.add(organization);
		user.setOrganizations(orgs);
		userService.saveOrgAdminAccount(user);
		return user;
	}
}
