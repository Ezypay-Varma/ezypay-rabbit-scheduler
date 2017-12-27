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

		Date date = new Date();
		String strDateFormat = "hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate= dateFormat.format(date);
		System.out.println("end=================="+msg+formattedDate);

		throw new AmqpRejectAndDontRequeueException("failed");
	}
}