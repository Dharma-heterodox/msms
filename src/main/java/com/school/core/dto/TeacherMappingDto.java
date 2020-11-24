package com.school.core.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.school.core.entity.Organization;

public class TeacherMappingDto {

	private Long id;
	private Long teacherId;
	private String teacherName;
	private Long gradeId;
	private Long sectionId;
	private Long subjectId;
	private String subject;
	private String academicYear;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private boolean classTeacher;
	private boolean active;
	private String grade;
	private String section;
	private List<Long> subjectList=new ArrayList<Long>();
	
	
	
	public List<Long> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<Long> subjectList) {
		this.subjectList = subjectList;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacher) {
		this.teacherId = teacher;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public LocalDate getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public LocalDate getDateTo() {
		return dateTo;
	}
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	public boolean isClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(boolean classTeacher) {
		this.classTeacher = classTeacher;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeacherMappingDto other = (TeacherMappingDto) obj;
		if(this.sectionId.equals(other.getSectionId())||this.sectionId==other.getSectionId()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Organization [id=" + id + ", section=" + sectionId + ", subjectName="
				+ subject + ", active=" + active + "]";
	}
}
