package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.OneTimePassword;

@Repository
public interface OTPRepo extends JpaRepository<OneTimePassword, Long> {

	@Query(
			  value = "SELECT otp FROM OneTimePassword otp WHERE otp.mobile = ?1 and otp.status = ?2")
	OneTimePassword getOTP(String mobileNo, String status);
	@Query(
			  value = "SELECT otp FROM OneTimePassword otp WHERE otp.mobile = ?1 and otp.status != ?2")
	List<OneTimePassword> getUserOTPs(String mobileNo, String status);
}
