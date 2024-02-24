package com.example.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.MessageDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {

	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	private final RabbitTemplate rabbitTemplate;
	
	
	public void sendMessage(MessageDto messageDto) {
		System.out.println("message sent: {"+messageDto.toString()+"}");
		System.out.println(rabbitTemplate);
		rabbitTemplate.convertAndSend(exchangeName,routingKey,messageDto);
	}
	
	@RabbitListener( queues = "sample.queue")
	public void receiveMessage(MessageDto messageDto) {
		System.out.println("Received Message: {"+messageDto.toString()+"}");
	}
}
