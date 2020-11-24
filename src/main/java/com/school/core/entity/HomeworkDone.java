package com.school.core.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
//Created By : Dharma
//Date : 13-09-2020
//Purpose : To Intract with DB for finished students homework
@Entity
@Table(name = "homework_done")
public class HomeworkDone extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homeworkdone_id")
    private Long id; 
	private String title;
	private String description;
	private Long gradeId;
	private Long sectionId;
	private Long subjectId;
	private Long teacherId;
	private Long schoolId;
	private Long studentId;
	private LocalDate homeworkDate;
//	private LocalDate startDate;
//	private LocalDate endDate;
//	@Lob
//	private byte[] file;
	private boolean verified;
	private Long teacherHomeworkId ;
	private String rating;
	@Lob
	private String fileURL;
	@Column(length = 30)
	private String studentName;
	private Integer rollNo;
	
	
	
	
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public Long getTeacherHomeworkId() {
		return teacherHomeworkId;
	}
	public void setTeacherHomeworkId(Long teacherHomeworkId) {
		this.teacherHomeworkId = teacherHomeworkId;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public LocalDate getHomeworkDate() {
		return homeworkDate;
	}
	public void setHomeworkDate(LocalDate homeworkDate) {
		this.homeworkDate = homeworkDate;
	}
//	public LocalDate getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}
//	public LocalDate getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(LocalDate endDate) {
//		this.endDate = endDate;
//	}
//	public byte[] getFile() {
//		return file;
//	}
//	public void setFile(byte[] file) {
//		this.file = file;
//	}
	

}
