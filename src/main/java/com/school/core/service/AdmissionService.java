package com.school.core.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.school.core.dto.AdmissionDto;

public interface AdmissionService {

	AdmissionDto createAdmission(Long schoolId, AdmissionDto admissionDto);

	List<AdmissionDto> getAllAdmissionBySchoolId(Long schoolId);
	ByteArrayInputStream download(Long schoolId) throws IOException;
}
