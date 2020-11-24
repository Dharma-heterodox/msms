package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.school.core.dto.NewsDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.NewsService;

@RestController
@RequestMapping("/{schoolId}/news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	@PostMapping
	public ResponseObj createNews(@PathVariable("schoolId") Long schoolId,
			@RequestParam("newsDto") String news ,@RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		NewsDto newsDto = mapper.readValue(news, NewsDto.class);
		newsDto.setActive(true);
		return new ResponseObj(newsService.createNews(schoolId, newsDto, file),HttpStatus.OK);
	}

	@GetMapping
	public ResponseObj getAllNewsBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(newsService.getAllNewsBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseObj getCircular(@PathVariable("schoolId") Long schoolId, @PathVariable("id") Long id) {
		return new ResponseObj(newsService.getNews(id),HttpStatus.OK);
	}
}
