package com.school.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.dto.SectionDto;
import com.school.core.entity.Section;
import com.school.core.repo.section.SectionRepo;
import com.school.core.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private SectionRepo sectionRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public SectionDto createSection(Long schoolId, SectionDto sectionDto) {
		Section section = modelMapper.map(sectionDto, Section.class);
		section.setSchoolId(schoolId);
		section.setActive(true);
		sectionRepo.save(section);
		return modelMapper.map(section, SectionDto.class);
	}

	@Override
	public List<SectionDto> getAllSectionBySchoolId(Long schoolId) {
		List<Section> sections = sectionRepo.findAllBySchoolId(schoolId);
		return sections.stream().map(s -> modelMapper.map(s, SectionDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<SectionDto> findAllBySchoolIdAndGradeId(Long schoolId, Long gradeId) {
		List<Section> sections = sectionRepo.findAllBySchoolIdAndGradeId(schoolId, gradeId);
		return sections.stream().map(s -> modelMapper.map(s, SectionDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public SectionDto getBySection(Long schoolId, Long gradeId, String section) {
		Section sectionObj = sectionRepo.findAllBySchoolIdAndSection(schoolId, gradeId, section);
		if (sectionObj == null)
			return null;
		return modelMapper.map(sectionObj, SectionDto.class);
	}
	
	@Override
	public Map<Long, List<SectionDto>> getAllBySchool(Long schoolId)throws Exception{
		List<Section> sections = sectionRepo.findAllBySchoolId(schoolId);
		Map<Long, List<SectionDto>> dtoMap=new HashMap<Long, List<SectionDto>>();
		sections.forEach(h -> {
			SectionDto dto=new SectionDto();
			dto.setActive(true);
			dto.setGradeId(h.getGradeId());
			dto.setId(h.getId());
			dto.setSection(h.getSection());
			if(dtoMap.containsKey(h.getGradeId())) {
				List<SectionDto> dtoList=dtoMap.get(h.getGradeId());
				dtoList.add(dto);
				dtoMap.put(h.getGradeId(), dtoList);
			}else {
				List<SectionDto> dtoList=new ArrayList<SectionDto>();
				dtoList.add(dto);
				dtoMap.put(h.getGradeId(), dtoList);
			}
			
		});
		return dtoMap;
	}

}
