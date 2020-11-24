package com.school.core.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.school.core.dto.OneTimePasswordDto;

public final class OTPUtill {
	
	private static Map<String,OneTimePasswordDto> otp=new HashMap<String,OneTimePasswordDto>();
	
	public static OneTimePasswordDto saveOTP(String mobileNo,OneTimePasswordDto otpObj)throws Exception{
		otp.put(mobileNo,otpObj);
		return otpObj;
	}
	
	public static boolean checkOTP(String mobileNo,String otpCode)throws Exception{
		if(otp.containsKey(mobileNo)) {
			OneTimePasswordDto otpObj=(OneTimePasswordDto)otp.get(mobileNo);
			if(otpCode!=null && LocalDateTime.now().isBefore(otpObj.getExpiryTime()) && 
					otpCode.equals(otpObj.getCode())){
				otpObj.setStatus(Constant.STATUS_VERIFIED);
				otp.put(mobileNo, otpObj);
				otp.remove(mobileNo);
				return true;
			}
		}
		return false;
	}

}
