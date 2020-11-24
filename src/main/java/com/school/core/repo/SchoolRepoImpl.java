package com.school.core.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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

@Repository
public class SchoolRepoImpl implements SchoolCustomRepo {

	@PersistenceContext
	private EntityManager em;
	
    @Transactional
	public Employee saveEmployeeDetails(Employee employee) {
		em.persist(employee);
		return employee;
	}
    
    @Transactional
	public TeacherMapping assignTeacher(TeacherMapping teacherMapping) {
		em.persist(teacherMapping);
		return teacherMapping;
	}
    
    @Transactional
	public Grade addGrade(Grade grade) {
		em.persist(grade);
		return grade;
	}
    
    @Transactional
	public Section addSection(Section section) {
		em.persist(section);
		return section;
	}
    
    @Transactional
	public SchoolBoard addSchoolBoard(SchoolBoard board) {
		em.persist(board);
		return board;
	}

    @Transactional
	public Student addStudent(Student student) {
		em.persist(student);
		return student;
	}

    @Transactional
	public Parent addParent(Parent parent) {
		em.persist(parent);
		return parent;
	}
	
    @Transactional
	public Homework addHomework(Homework homework) {
		em.persist(homework);
		return homework;
	}
    
    @Transactional
	public Circular addCircular(Circular circular) {
		em.persist(circular);
		return circular;
	}
    
    @Transactional
	public News addNews(News news) {
		em.persist(news);
		return news;
	}
    
    @Transactional
	public Feedback addFeedback(Feedback feedback) {
		em.persist(feedback);
		return feedback;
	}
}
