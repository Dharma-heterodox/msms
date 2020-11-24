package com.school.core.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class EmployeeDto {

	private Long id;
	private String category;
	private String subjectTaking;
	private String qualification;
	private String designation;
	private String major;
	private String address;
	private LocalDate dateOfJoin;
	private String marritalStatus;
	private boolean active;
	private String title;
	private String firstName;
	private String lastName;
	private String spouseName;
    private String displayName;
    private String gender;
    private LocalDate dob;
    private String mobile;
    private String alternateMobile;
    private String email;
    private String aadhaarNo;
    private String classHandling;
    private int subCategory;
    private String subCategoryName;
    private Set<TeacherMappingDto> teacherMapping;
    private Long userId;
    
    
    
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public Set<TeacherMappingDto> getTeacherMapping() {
		return teacherMapping;
	}
	public void setTeacherMapping(Set<TeacherMappingDto> teacherMapping) {
		this.teacherMapping = teacherMapping;
	}
	public int getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(int subCategory) {
		this.subCategory = subCategory;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubjectTaking() {
		return subjectTaking;
	}
	public void setSubjectTaking(String subjectTaking) {
		this.subjectTaking = subjectTaking;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClassHandling() {
		return classHandling;
	}
	public void setClassHandling(String classHandling) {
		this.classHandling = classHandling;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDateOfJoin() {
		return dateOfJoin;
	}
	public void setDateOfJoin(LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}
	public String getMarritalStatus() {
		return marritalStatus;
	}
	public void setMarritalStatus(String marritalStatus) {
		this.marritalStatus = marritalStatus;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAlternateMobile() {
		return alternateMobile;
	}
	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAadhaarNo() {
		return aadhaarNo;
	}
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	
}
