package com.school.core.service;

import com.school.core.dto.OrganizationDto;
import com.school.core.entity.Organization;

public interface OrganizationService {

	OrganizationDto saveOrganization(OrganizationDto organization);
	Organization getOrganization(Long id);
	OrganizationDto getOrganizationDetail(Long id)throws Exception;
}
