package com.school.core.service;

public interface MessageService {
	
	boolean sendOTP(String mobileNo);
	boolean verifyOTP(String mobileNo, String code);
	boolean sendOTPV2(String mobileNo)throws Exception;
	boolean verifyOTPV2(String mobileNo,String code)throws Exception;

}
