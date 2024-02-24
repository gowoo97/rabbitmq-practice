package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.MessageDto;
import com.example.demo.service.MessageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {

	private final MessageService messageService;
	
	@RequestMapping(value = "/send/message" , method = RequestMethod.POST)
	public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto){
		messageService.sendMessage(messageDto);
		return ResponseEntity.ok("Message sent to RabbitMQ!");
	}
}
