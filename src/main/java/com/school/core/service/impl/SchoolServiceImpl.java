package com.school.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.dto.OrganizationDto;
import com.school.core.entity.Circular;
import com.school.core.entity.Employee;
import com.school.core.entity.Feedback;
import com.school.core.entity.Grade;
import com.school.core.entity.Homework;
import com.school.core.entity.News;
import com.school.core.entity.Organization;
import com.school.core.entity.Parent;
import com.school.core.entity.SchoolBoard;
import com.school.core.entity.Section;
import com.school.core.entity.Student;
import com.school.core.entity.User;
import com.school.core.entity.teacher.TeacherMapping;
import com.school.core.repo.SchoolRepo;
import com.school.core.service.OrganizationService;
import com.school.core.service.SchoolService;
import com.school.core.service.UserService;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolRepo schoolRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService orgservice;
	
	@Override
	public Employee saveEmployeeDetails(Employee employee) {
		return schoolRepo.saveEmployeeDetails(employee);
	}
	
	public TeacherMapping assignTeacher(TeacherMapping teacherMapping) {
		return schoolRepo.assignTeacher(teacherMapping);
	}

	@Override
	public Grade addGrade(Grade grade) {
		return schoolRepo.addGrade(grade);
	}

	@Override
	public Section addSection(Section section) {
		return schoolRepo.addSection(section);
	}

	@Override
	public SchoolBoard addSchoolBoard(SchoolBoard board) {
		return schoolRepo.addSchoolBoard(board);
	}
	
	@Override
	public Student addStudent(Student student) {
		student = schoolRepo.addStudent(student);
		createStudentAccount(student);
		return student;
	}
	
	@Override
	public Parent addParent(Parent parent) {
		parent = schoolRepo.addParent(parent);
		createParentAccount(parent);
		return parent;
	}

	private User createStudentAccount(Student student) {
		User user = new User();
		user.setUserName(student.getMobile());
		user.setPassword("test123$");
		user.setEmail(student.getEmail());
		user.setActive(true);
		if(student.getSchoolId() != null) {
			Organization org = orgservice.getOrganization(student.getSchoolId());
			Set<Organization> orgs = new HashSet<Organization>();
			orgs.add(org);
			user.setOrganizations(orgs);
		}
		userService.saveUser(user);
		return user;
	}
	
	private User createParentAccount(Parent parent) {
		User user = new User();
		user.setUserName(parent.getMobile());
		user.setPassword("test123$");
		user.setEmail(parent.getEmail());
		user.setActive(true);
		/*
		 * if(parent.getSchoolId() != null) { Organization org =
		 * orgservice.getOrganization(student.getSchoolId()); Set<Organization> orgs =
		 * new HashSet<Organization>(); orgs.add(org); user.setOrganizations(orgs); }
		 */
		userService.saveUser(user);
		return user;
	}
	
	public Homework addHomework(Homework homework) {
		return schoolRepo.addHomework(homework);
	}
	
	public Circular addCircular(Circular circular) {
		return schoolRepo.addCircular(circular);
	}
	
	public News addNews(News news) {
		return schoolRepo.addNews(news);
	}

	@Override
	public Feedback addFeedback(Feedback feedback) {
		return schoolRepo.addFeedback(feedback);
	}

	@Override
	public List<SchoolBoard> getAvailableBoards(Long schoolId) {
		return schoolRepo.getAvailableBoards(schoolId);
	}

	@Override
	public List<Grade> getGrades(Long schoolId, Long boardId) {
		return schoolRepo.getGrades(schoolId, boardId);
	}

	@Override
	public List<Section> getSections(Long schoolId, Long gradeId) {
		return schoolRepo.getSections(schoolId, gradeId);
	}

	@Override
	public List<Employee> getEmployees(Long schoolId) {
		return schoolRepo.getEmployees(schoolId);
	}

	@Override
	public List<Feedback> getFeedback(Long schoolId) {
		return schoolRepo.getFeedback(schoolId);
	}

	@Override
	public List<Circular> getCircular(Long schoolId) {
		return schoolRepo.getCircular(schoolId);
	}

	@Override
	public List<Homework> getHomework(Long schoolId) {
		return schoolRepo.getHomework(schoolId);
	}

	@Override
	public List<News> getNews(Long schoolId) {
		return schoolRepo.getNews(schoolId);
	}

	@Override
	public List<Student> getStudents(Long schoolId) {
		return schoolRepo.getStudents(schoolId);
	}

	@Override
	public List<TeacherMapping> getTeachers(Long schoolId, Long gradeId, Long sectionId, String academicYear) {
		return schoolRepo.getTeachers(schoolId, gradeId, sectionId, academicYear);
	}

	@Override
	public TeacherMapping getTeacher(Long schoolId, Long gradeId, Long sectionId, String academicYear, Long subject) {
		return schoolRepo.getTeacher(schoolId, gradeId, sectionId, academicYear, subject);
	}
}
