package com.ezypay.rc;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@SpringBootApplication
@EnableBinding(Sink.class)
public class RcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void listen(Map<String, Object> msg){
		throw new AmqpRejectAndDontRequeueException("failed");
	}
}

















//please refer below for managing x-death to define number of cycles

/*
	@StreamListener(Sink.INPUT)
	public void listen(String in, @Header(name = "x-death", required = false) Map<?,?> death) {
		if (death != null && death.get("count").equals(3L)) {
			// giving up - don't send to DLX
			throw new ImmediateAcknowledgeAmqpException("Failed after 4 attempts");
		}
		throw new AmqpRejectAndDontRequeueException("failed");
	}*/
