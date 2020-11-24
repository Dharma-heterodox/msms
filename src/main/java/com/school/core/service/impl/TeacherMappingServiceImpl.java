package com.school.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.school.core.dto.EmployeeDto;
import com.school.core.dto.StudentDto;
import com.school.core.dto.SubjectDto;
import com.school.core.dto.TeacherMappingDto;
import com.school.core.entity.teacher.TeacherMapping;
import com.school.core.repo.TeacherMappingRepo;
import com.school.core.service.EmployeeService;
import com.school.core.service.StudentService;
import com.school.core.service.SubjectService;
import com.school.core.service.TeacherMappingService;
import com.school.core.util.Constant;

@Service
public class TeacherMappingServiceImpl implements TeacherMappingService {

	//private String academicYear =  "2020-2021";
	
	@Autowired
	private TeacherMappingRepo teacherMappingRepo;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static Logger logger = LoggerFactory.getLogger(TeacherMappingServiceImpl.class);
	
	@Override
	public List<TeacherMappingDto> getAllTeacherMappingBySchoolId(Long schoolId) {
		List<TeacherMapping> teacherMappings = teacherMappingRepo.findAllBySchoolId(schoolId);
		return teacherMappings.stream().map(a -> modelMapper.map(a, TeacherMappingDto.class)).collect(Collectors.toList());
	}

	public List<TeacherMappingDto> getAllTeacherMappingByStudentId(Long schoolId, Long studentId, String academicYear) {
		StudentDto student = studentService.getStudentById(studentId);
		return findMappedTeachers(schoolId, student.getGradeId(), student.getSectionId(), academicYear == null ? Constant.currentAcademicYear : academicYear);
	}

	@Override
	public TeacherMappingDto createTeacherMapping(Long schoolId, TeacherMappingDto teacherMappingDto) {
		TeacherMapping teacherMapping = modelMapper.map(teacherMappingDto, TeacherMapping.class);
		teacherMapping.setSchoolId(schoolId);
		teacherMapping.setActive(true);
		if(teacherMapping.getAcademicYear() == null)
			teacherMapping.setAcademicYear(Constant.currentAcademicYear);
		teacherMapping = teacherMappingRepo.save(teacherMapping);
		return modelMapper.map(teacherMapping, TeacherMappingDto.class);
	}
	@Override
	public List<TeacherMappingDto> findMappedTeachers(Long schoolId, Long gradeId, Long sectionId, String academicYear) {
		List<TeacherMapping> teacherMappings = teacherMappingRepo.findMappedTeachers(schoolId, gradeId, sectionId, academicYear);
		if (teacherMappings == null)
				return null;
		List<TeacherMappingDto> mappings = teacherMappings.stream().map(a -> modelMapper.map(a, TeacherMappingDto.class)).collect(Collectors.toList());
		List<TeacherMappingDto> teachers = new ArrayList<TeacherMappingDto>();
		if(!CollectionUtils.isEmpty(mappings)) {
			List<SubjectDto> subjects = subjectService.findAllBySchoolIdAndGradeId(schoolId, gradeId);
			for(TeacherMappingDto mapping : mappings) {
				for(SubjectDto subject: subjects) {
					if(mapping.getSubjectId().equals(subject.getId())) {
						mapping.setSubject(subject.getSubjectName());
						break;
					}
				}
				EmployeeDto employee = employeeService.getEmployee(mapping.getTeacherId());
				String lastName = employee.getLastName() != null ? employee.getLastName(): "";
				String teacherName = employee.getFirstName() + " " + lastName;
				mapping.setTeacherName(teacherName);
				teachers.add(mapping);
			}
		}
		return teachers;
	}
	
	public TeacherMappingDto getClassTeacher(Long schoolId, Long gradeId, Long sectionId, String academicYear) {
		List<TeacherMappingDto> teacherMappings = findMappedTeachers(schoolId, gradeId, sectionId, academicYear  == null ? Constant.currentAcademicYear : academicYear );
		TeacherMappingDto teacherMapping = null;
		if(!CollectionUtils.isEmpty(teacherMappings)) {
			for(TeacherMappingDto mapping: teacherMappings) {
				if(mapping.isClassTeacher()) {
					teacherMapping = mapping;
					break;
				}
			}
		}
		if(teacherMapping == null)
			return null;
		return modelMapper.map(teacherMapping, TeacherMappingDto.class);
				
	}
	
//	Created By : Dharma
//	Date:10-10-2020
//	Purpose: To retrieve teacher mapping by Teacher Id while login .
	@Override
	public Set<TeacherMappingDto> getTechMapByTeacher(Long teacherId)throws Exception
	{
		Set<TeacherMappingDto> teacherMappings = null;
		List<TeacherMapping> mappingEntity=null;
		try {
			logger.debug("Get Teacher Mapping by TeacherId :: "+teacherId);
			mappingEntity=teacherMappingRepo.findMapByTeacher(teacherId, Constant.currentAcademicYear);
			teacherMappings=getMappingDtoObj(mappingEntity);
		}catch(Exception ex){
			logger.error("Error in getTechMapByTeacher :: "+ex.getMessage());
			throw new Exception(ex);
		}
		return teacherMappings;
	}
	
	private Set<TeacherMappingDto> getMappingDtoObj(List<TeacherMapping> mappingList)throws Exception
	{
		Map<Long,TeacherMappingDto> map=new HashMap<Long,TeacherMappingDto>();
		Set<TeacherMappingDto> teacherMappings = new HashSet<TeacherMappingDto>();
		mappingList.stream().forEach(h -> {
			if(map.containsKey(h.getSectionId())){
				TeacherMappingDto dto=map.get(h.getSectionId());
				dto.getSubjectList().add(h.getSubjectId());
				teacherMappings.add(dto);
			}else {
				TeacherMappingDto dto=new TeacherMappingDto();
				dto.setAcademicYear(h.getAcademicYear());
				dto.setActive(true);
//				dto.setClassTeacher((h.get);
				dto.setDateFrom(h.getDateFrom());
				dto.setGradeId(h.getGradeId());
				dto.setId(h.getId());
				dto.setSectionId(h.getSectionId());
				dto.setSubjectId(h.getSubjectId());
				dto.getSubjectList().add(h.getSubjectId());
				dto.setTeacherId(h.getTeacherId());
				dto.setSection(h.getSection());
				dto.setSubject(h.getSubject());
				dto.setGrade(h.getGrade());
				map.put(h.getSectionId(), dto);
				teacherMappings.add(dto);
			}
			
		});
		return teacherMappings;
	}
}