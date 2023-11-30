package com.example.walletpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@EnableOpenApi
public class WalletPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletPaymentApplication.class, args);
	}

}
