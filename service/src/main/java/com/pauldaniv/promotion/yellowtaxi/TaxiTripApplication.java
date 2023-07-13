package com.pauldaniv.promotion.yellowtaxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TaxiTripApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiTripApplication.class, args);
	}

}
