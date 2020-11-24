package com.school.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="student_section_record")
public class StudentSectionRecord extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_section_id")
	private Long id;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy="sectionRecord",orphanRemoval = true)
    private List<Student> studentList=new ArrayList<Student>(); 
//	@Column(name="student_id")
//	private Long studentId;
	@Column(name="section_id")
	private Long sectionId;
	@Column(name="grade_id")
	private Long gradeId;
	@Column(name="academicyear",length=20)
	private String academicYear;
	@Column(name="result",length=20)
	private String result;
	@Column(length=6)
    private String grade;
	@Column(length=6)
    private String section;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Long getStudentId() {
//		return studentId;
//	}
//	public void setStudentId(Long studentId) {
//		this.studentId = studentId;
//	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	public void addStudent(Student student) {
		studentList.add(student);
		student.setSectionRecord(this);;
	}
	
	public void removeStrudent(Student student) {
		studentList.remove(student);
		student.setSectionRecord(null);;
	}
	
	
	
	
	
	
	

}
