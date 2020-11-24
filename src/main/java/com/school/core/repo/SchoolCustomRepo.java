package com.school.core.repo;

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

public interface SchoolCustomRepo {

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
}
