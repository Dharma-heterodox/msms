package com.school.core.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.CircularDto;
import com.school.core.entity.Circular;
import com.school.core.repo.CircularRepo;
import com.school.core.service.CircularService;

@Service
public class CircularServiceImpl implements CircularService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CircularRepo circularRepo;

	@Override
	public CircularDto createCircular(Long schoolId, CircularDto circularDto, MultipartFile file) throws Exception {
		Circular circular = modelMapper.map(circularDto, Circular.class);
		circular.setSchoolId(schoolId);
		circular.setActive(true);
		try {
			circular.setFile(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		circular = circularRepo.save(circular);
		return modelMapper.map(circular, CircularDto.class);
	}

	public byte[] getCircular(Long id) {
		Circular circular = circularRepo.getOne(id);
		return circular != null ? circular.getFile() : null;
	}
	
	@Override
	public List<CircularDto> getAllCircularBySchoolId(Long schoolId) {
		List<Circular> circulars = circularRepo.findAllBySchoolId(schoolId);
		return circulars.stream().map(a -> modelMapper.map(a, CircularDto.class)).collect(Collectors.toList());
	}

}
