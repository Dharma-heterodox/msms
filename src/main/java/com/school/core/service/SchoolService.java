package com.school.core.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.school.core.entity.Circular;
import com.school.core.entity.Employee;
import com.school.core.entity.Feedback;
import com.school.core.entity.Grade;
import com.school.core.entity.Homework;
import com.school.core.entity.News;
import com.school.core.entity.Parent;
import com.school.core.entity.SchoolBoard;
import com.school.core.entity.Section;
import com.school.core.entity.Student;
import com.school.core.entity.teacher.TeacherMapping;

@Service
public interface SchoolService {

	Employee saveEmployeeDetails(Employee employee);
	TeacherMapping assignTeacher(TeacherMapping teacherMapping);
	Grade addGrade(Grade grade);
	Section addSection(Section section);
	SchoolBoard addSchoolBoard(SchoolBoard board);
	Student addStudent(Student student);
	Parent addParent(Parent parent);
	Homework addHomework(Homework homework);
	Circular addCircular(Circular circular);
	News addNews(News news);
	Feedback addFeedback(Feedback feedback);
	List<SchoolBoard> getAvailableBoards(Long schoolId);
	List<Grade> getGrades(Long schoolId, Long boardId);
	List<Section> getSections(Long schoolId, Long gradeId);
	List<Employee> getEmployees(Long schoolId);
	List<Feedback> getFeedback(Long schoolId);
	List<Circular> getCircular(Long schoolId);
	List<Homework> getHomework(Long schoolId);
	List<News> getNews(Long schoolId);
	List<Student> getStudents(Long schoolId);
	List<TeacherMapping> getTeachers(Long schoolId, Long gradeId, Long sectionId, String academicYear);
	TeacherMapping getTeacher(Long schoolId, Long gradeId, Long sectionId, String academicYear, Long subject);
}
