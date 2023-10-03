package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.Math.pow;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		/*int count = 0;

		// 1-я задача

		for (int x = 1; x < 5 * pow(3, 0.5); x++) {
			for (int y = 1; y < 10; y++) {
				if (y > x * pow(3, -0.5) && y < -x * pow(3, -0.5) + 10)
					count++;
			}
		}

		System.out.println(count);*/

		// 2-я задача

		/*for (int x = 0; x <= 18; x++) {
			for (int y = 0; y <= 8; y++) {
				count++;
			}
		}

		for (int x = 10; x <= 17; x++) {
			for (int y = 9; y <= 21; y++) {
				count++;
			}
		}

		System.out.println(count);*/

	}

}
