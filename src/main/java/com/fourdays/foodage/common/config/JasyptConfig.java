package com.fourdays.foodage.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

	@Value("${jasypt.encryptor.password}")
	private String password;

	@Bean("jasyptEncryptorAES")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(password);                       // 암호화 키
		config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256"); // 암호화 알고리즘
		config.setKeyObtentionIterations("1000");           // 해싱 횟수
		config.setPoolSize("1");                            // 인스턴스 pool
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");               // 인코딩 방식
		encryptor.setConfig(config);
		return encryptor;
	}
}