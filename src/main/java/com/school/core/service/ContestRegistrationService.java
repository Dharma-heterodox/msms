package com.school.core.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.school.core.dto.ContestRegistrationDto;

public interface ContestRegistrationService {

	List<ContestRegistrationDto> gteAllRegistrations(Long schoolId);
	ContestRegistrationDto createRegistration(Long schoolId, ContestRegistrationDto contestRegsitrationDto);
	ByteArrayInputStream download(Long schoolId) throws IOException;
}
