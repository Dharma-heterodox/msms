package com.school.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.school.core.dto.ParentDto;
import com.school.core.dto.StudentDto;
import com.school.core.entity.Parent;
import com.school.core.entity.Student;
import com.school.core.entity.User;
import com.school.core.repo.ParentRepo;
import com.school.core.service.AddressService;
import com.school.core.service.ParentService;
import com.school.core.service.UserService;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ParentRepo parentRepo;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	
	public ParentDto getParentByMobile(String mobile)throws Exception {
		Parent parent = parentRepo.findByMobile(mobile);
		if(parent == null)
			return null;
		return getParentDto(parent);
	}

	@Override
	public ParentDto createParent(ParentDto parentDto) {
		Parent parent = modelMapper.map(parentDto, Parent.class);
		parentDto.setActive(true);
		User user=createParentAccount(parent);
		parent.setUserId(user.getId());
		parent = parentRepo.save(parent);
		return modelMapper.map(parent, ParentDto.class);
	}
	
	public ParentDto addStudent(Long parentId, StudentDto studentDto) {
		Parent parent = parentRepo.getOne(parentId);
		//AddressDto addressDto= studentDto.getAddress();
		Student student = modelMapper.map(studentDto, Student.class);
		student.setActive(true);
		if(CollectionUtils.isEmpty(parent.getChilds())) {
			Set<Student> students = new HashSet<Student>();
			students.add(student);
			parent.setChilds(students);
		} else {
			parent.getChilds().add(student);
		}
		parent = parentRepo.save(parent);
		/*
		 * if(addressDto != null && addressDto.getAddressLine1() != null) { for(Student
		 * stnt : parent.getChilds()) {
		 * if(stnt.getFirstName().equalsIgnoreCase(studentDto.getFirstName())) {
		 * addressDto.setOrganizationId(stnt.getId());
		 * addressService.createAddress(addressDto); break; } } }
		 */
		return modelMapper.map(parent, ParentDto.class);
	}

	@Override
	public List<ParentDto> getAllParentByStudentId(Long studentId) {
		//List<Parent> parents = parentRepo.findAllBySchoolId(schoolId);
		//return parents.stream().map(a -> modelMapper.map(a, ParentDto.class)).collect(Collectors.toList());
		return null;
	}
	
	private User createParentAccount(Parent parent) {
		User user = new User();
		user.setUserName(parent.getMobile());
		user.setMobile(parent.getMobile());
		user.setFirstName(parent.getFirstName());
		user.setLastName(parent.getLastName());
		user.setPassword("test123$");
		user.setEmail(parent.getEmail());
		user.setActive(true);
		/*
		 * if(parent.getSchoolId() != null) { Organization org =
		 * orgservice.getOrganization(student.getSchoolId()); Set<Organization> orgs =
		 * new HashSet<Organization>(); orgs.add(org); user.setOrganizations(orgs); }
		 */
		userService.saveParentAccount(user);
		return user;
	}
	
//	Created By : Dharma
//	Date:17-11-2020
//	Purpose: To set data in DTO object in login process.
//	Assumption:: 
	private ParentDto getParentDto(Parent parent)throws Exception{
		ParentDto dto=new ParentDto();
		List<StudentDto> dtoList=new ArrayList<StudentDto>();
		dto.setAadhaarNo(parent.getAadhaarNo());
		dto.setActive(true);
		dto.setAlternateMobile(parent.getAlternateMobile());
		dto.setCaste(parent.getCaste());
		dto.setCasteCat(parent.getCasteCat());
		dto.setDisplayName(parent.getDisplayName());
		dto.setDob(parent.getDob());
		dto.setFirstName(parent.getFirstName());
		dto.setGender(parent.getGender());
		dto.setId(parent.getId());
		dto.setLandLine(parent.getLandLine());
		dto.setMobile(parent.getMobile());
		dto.setRelationship(parent.getRelationship());
		dto.setReligion(parent.getReligion());
		dto.setSpouseName(parent.getSpouseName());
		dto.setTitle(parent.getTitle());
		parent.getChildList().forEach(h -> {
			StudentDto sdto=new StudentDto();
			sdto.setAadhaarNo(h.getAadhaarNo());
			sdto.setActive(true);
			sdto.setAddress(h.getAddress());
			sdto.setAdmissionNo(h.getAdmissionNo());
			sdto.setCaste(h.getCaste());
			sdto.setCasteCat(h.getCasteCat());
			sdto.setClassTeacher(null);
			sdto.setDisplayName(h.getDisplayName());
			sdto.setDob(h.getDob());
			sdto.setEmergencyContactNo(h.getEmergencyContactNo());
			sdto.setEmisno(h.getEmisno());
			sdto.setEnrollmentNo(h.getExamNo());
			sdto.setExamNo(h.getExamNo());
			sdto.setFatherName(h.getFatherName());
			sdto.setFirstName(h.getFirstName());
			sdto.setGender(h.getGender());
			sdto.setGrade(h.getGrade());
			sdto.setGradeId(h.getGradeId());
			sdto.setId(h.getId());
			sdto.setIdNumber(h.getIdNumber());
			sdto.setLandLine(h.getLandLine());
			sdto.setMotherName(h.getMotherName());
			sdto.setReligion(h.getReligion());
			sdto.setRollNo(h.getRollNo());
			sdto.setRTE(h.getRTE());
			sdto.setSchoolId(h.getSchoolId());
			sdto.setSection(h.getSection());
			sdto.setSectionId(h.getSectionId());
			sdto.setStudId(h.getStudId());
			sdto.setTitle(h.getTitle());
			dtoList.add(sdto);
		});
		dto.setChilds(dtoList);
		return dto;
	}

}
