package com.school.core.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

public class StudentDto {

	private Long id;
	private String title;
	private String firstName;
	private String lastName;
    private String displayName;
    private String gender;
    private LocalDate dob;
    private String mobile;
    private String alternateMobile;
    private String email;
    private String aadhaarNo;
    private String idNumber;
    private Integer rollNo;
    private Long gradeId;
    private String grade;
    private String section;
    private Long sectionId;
    private Long mediumId;
    private Long boardId;
    private String bloodGroup;
    private String emergencyContactNo;
    private String address;
    private String fatherName;
    private String motherName;
    private Set<ParentDto> parents;
    private boolean active;
    @NotEmpty
    private Long schoolId;
    private TeacherMappingDto classTeacher;
    private String admissionNo;
    private int studId;
    private String examNo;
    private String casteCat;
    private String caste;
    private String religion;
    private String landLine;
    private String RTE;
    private String emisno;
    private String enrollmentNo;
    private List<String> errors;
    private int requestType;
    private int childOrder;
    
    
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	public String getEnrollmentNo() {
		return enrollmentNo;
	}
	public void setEnrollmentNo(String enrollmentNo) {
		this.enrollmentNo = enrollmentNo;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public int getStudId() {
		return studId;
	}
	public void setStudId(int studId) {
		this.studId = studId;
	}
	public String getExamNo() {
		return examNo;
	}
	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	public String getCasteCat() {
		return casteCat;
	}
	public void setCasteCat(String casteCat) {
		this.casteCat = casteCat;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getLandLine() {
		return landLine;
	}
	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}
	public String getRTE() {
		return RTE;
	}
	public void setRTE(String rTE) {
		RTE = rTE;
	}
	public String getEmisno() {
		return emisno;
	}
	public void setEmisno(String emisno) {
		this.emisno = emisno;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getMediumId() {
		return mediumId;
	}
	public void setMediumId(Long mediumId) {
		this.mediumId = mediumId;
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
	public String getAdmissionNo() {
		return admissionNo;
	}
	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public Long getBoardId() {
		return boardId;
	}
	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}
	public TeacherMappingDto getClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(TeacherMappingDto classTeacher) {
		this.classTeacher = classTeacher;
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
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idCard) {
		this.idNumber = idCard;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<ParentDto> getParents() {
		return parents;
	}
	public void setParents(Set<ParentDto> parents) {
		this.parents = parents;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	public int getChildOrder() {
		return childOrder;
	}
	public void setChildOrder(int childOrder) {
		this.childOrder = childOrder;
	}
	@Override
	public int hashCode() {
		if(this.id!=null) {
			return this.id.hashCode();
		}
		return this.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(this.id == null)
			return false;
		StudentDto dto=(StudentDto)obj;
		if(dto.id==this.id || dto.id.equals(this.id))
			return true;
		
		return false;
	}
    
}
