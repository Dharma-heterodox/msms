package com.school.core.service;

import com.school.core.entity.Notification;

public interface NotificationService {
	Notification createNotification(Notification notification);
	Notification updateNotification(Notification notification);
	boolean sendEmail(String to, String cc, String bcc, String message);
	boolean sendSMS(String mobileNo, String message, String formName, Long referenceId);
	
}
