package com.school.core.util;

public interface ErrorCodeV {
	String NAME_NOTEMPTY="name should not be empty";
	String NAME_REGEX="name should be less than 30 alphabets only";
	String STUDID_NOTEMPTY="Student ID should not be empty";
	String STUDID_REGEX="Student ID should be only numeric";
	String GENDER_NOTEMPTY="Gender Should not be empty";
	String GENDER_REGEX="Gender should be less than 8 alphabets only";
	String DOB_NOTEMPTY="DOB Should not be empty";
	String DOB_REGEX="DOB should have only numeric & -";
	String CASTCAT_NOTEMPTY="Caste category should not be empty";
	String CASTCAT_REGEX="Caste category should be alphabetics ";
	String CAST_NOTEMPTY="Caste should not be empty";
	String RELIGION_NOTEMPTY="Religion should not be empty";
	String RELIGION_REGEX="Religion should be alphabets less than 20";
	String ADDRESS_NOTEMPTY="Address should not be empty";
	String MOBILE_NOTEMPTY="Mobile no should not be empty";
	String MOBILE_REGEX="Mobile no should be 10 digit";
	String LANDLINE_REGEX="Land line should be numbers";
	String STD_NOTEMPTY="Standard should not be empty";
	String STD_REGEX="Standard not matched";
	String SECTION_NOTEMPTY="Section should not be empty";
	String RTE_NOTEMPTY="RTE should not be empty";
	String AADHAR_NOTEMPTY="AADHAR number should not be empty";
	String AADHAR_REGEX="AADHAR no should be 12 digits";
	String EMINO_NOTEMPTY="EMINO should not be empty";
	
	interface Header{
		int LENGTH=21;
		String SL_NO="slno";
		String STU_ID="Stu. Id";
		String EXAM_NO="Exam No";
		String STU_NAME="Stu Name";
		String GENDER="Gender";
		String DOB="DOB";
		String MOTHER_NAME="MotherName";
		String FATHER_NAME="Father Name";
		String CASTE_DIV="Caste Div";
		String CASTE="caste";
		String RELIGION="Religion";
		String ADDRESS="Address";
		String MOBILE="Mobile";
		String MOBILE_2="Mobile 2";
		String LAND_LINE="Land Line No";
		String CLASS="class";
		String SECTION="Section";
		String RTE="RTE";
		String AADHAAR_NO="AadhaarNo";
		String ENROLLMENT_NO="Enrolment No";
		String EMI_SNO="EMISNO";
	}

}
