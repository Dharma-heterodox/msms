package com.school.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.core.entity.Parent;
import com.school.core.entity.Role;
import com.school.core.entity.Student;
import com.school.core.entity.StudentSectionRecord;
import com.school.core.entity.User;
import com.school.core.entity.UserRequest;
import com.school.core.repo.ParentRepo;
import com.school.core.repo.RoleRepository;
import com.school.core.repo.UserRepository;
import com.school.core.repo.UserRequestRepo;
import com.school.core.service.UserRequestService;
import com.school.core.service.UserService;
import com.school.core.util.Constant;
import com.school.core.util.Roles;
@Service
public class UserRequestServiceImpl implements UserRequestService{
	
	@Autowired
	private UserRequestRepo userRequestRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ParentRepo parentRepo;
	
	private Logger logger = LoggerFactory.getLogger(HomeworkDoneServiceImpl.class);

	@Override
	@Transactional
	public int createStudentParentAcc(List<Long> ids,Long schoolId) throws Exception {
		// TODO Auto-generated method stub
		List<UserRequest> requestedUsers=null;
		List<User> userList=null;
		List<Parent> parentList=null;
		int result=0;
		logger.debug("Create student parent acc ::");
		try {
			requestedUsers=userRequestRepo.getStudentParentReq(schoolId);
//			requestedUsers=userRequestRepo.getStudentParentReq(ids);
			userList=userRepo.saveAll(getUserObject(requestedUsers));
			parentList=parentRepo.saveAll(getParents(userList, requestedUsers));
			if(parentList.size()>0) {
				result=userRequestRepo.updateReqStatus(Constant.REQUEST_APPROVED,ids);
			}
		}catch(Exception ex) {
			logger.debug("Exception in createStudentParentAcc");
			logger.error("createStudentParentAcc Exception :: "+ex.getMessage());
			throw new Exception(ex);
		}
		return result;
	}
	
	private List<User> getUserObject(List<UserRequest> userReqList) throws Exception{
		List<User> userList=new ArrayList<User>();
		Set<Role> role=getParentRole();
		try {
			userReqList.forEach(h -> {
				User user=new User();
				user.setActive(true);
				user.setContactNo(h.getMobile());
				user.setEmergencyContactNo(h.getAlternateMobile());
				user.setFirstName(h.getMotherName());
				user.setMobile(h.getMobile());
				user.setPassword("$TESTONE");
				user.setRoles(role);
				user.setUserName(h.getMotherName());
				user.setUserType("");
				user.setLastName(h.getFatherName());
				userList.add(user);
			});
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return userList;
		
	}
	
	private List<Parent> getParents(List<User> users,List<UserRequest> userReqList)throws Exception{
		List<Parent> parents=new ArrayList<Parent>();
		try {
			for(int i=0;i<userReqList.size();i++) {
				Parent parent=new Parent();
				Student student=new Student();
				StudentSectionRecord sectionRecord=new StudentSectionRecord();
				UserRequest req=(UserRequest)userReqList.get(i);
				User user=(User)users.get(i);
				parent.setAadhaarNo(req.getAadhaarNo());
				parent.setActive(true);
				parent.setAlternateMobile(req.getAlternateMobile());
				parent.setCaste(req.getCaste());
				parent.setCasteCat(req.getCasteCat());
				parent.setDisplayName(req.getMotherName());
				parent.setDob(req.getDob());
				parent.setGender("FEMALE");
				parent.setLandLine(req.getLandLine());
				parent.setMobile(user.getMobile());
				parent.setRelationship("MOTHER");
				parent.setReligion(req.getReligion());
				parent.setSpouseName(req.getFatherName());
				parent.setUserId(user.getId());
				student.setAadhaarNo(req.getAadhaarNo());
				student.setAddress(req.getAddress());
				student.setAdmissionNo(req.getAdmissionNo());
				student.setCaste(req.getCaste());
				student.setCasteCat(req.getCasteCat());
				student.setDisplayName(req.getStudentName());
				student.setDob(req.getDob());
				student.setEmergencyContactNo(req.getAlternateMobile());
				student.setEmisno(req.getEmisno());
				student.setExamNo(req.getExamNo());
				student.setFatherName(req.getFatherName());
				student.setFirstName(req.getStudentName());
				student.setGender(req.getGender());
				student.setGrade(req.getGrade());
				student.setGradeId(req.getGradeId());
				student.setLandLine(req.getLandLine());
				student.setMobile(req.getMobile());
				student.setReligion(req.getReligion());
				student.setRollNo(req.getRollNo());
				student.setRTE(req.getRTE());
				student.setSchoolId(req.getSchoolId());
				student.setSection(req.getSection());
				student.setSectionId(req.getSectionId());
				student.setStudId(req.getStudId());
				sectionRecord.setAcademicYear(Constant.currentAcademicYear);
				sectionRecord.setGradeId(req.getGradeId());
				sectionRecord.setGrade(req.getGrade());
				sectionRecord.setSection(req.getSection());
				sectionRecord.setSectionId(req.getSectionId());
				sectionRecord.addStudent(student);
				parent.addChild(student);
				parents.add(parent);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return parents;
	}
	
	private Set<Role> getParentRole(){
		Set<Role> role=new HashSet<Role>();
		role.add(roleRepository.findByRole(Roles.PARENT.name()));
		return role;
	}

}
