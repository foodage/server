package com.fourdays.foodage.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

class JasyptTest {

	/*
	 *  💡 tip.
	 *      설정 파일(application.yml)에 노출되지 않아야 할 값이 있을 경우,
	 *      해당 테스트 코드를 통해 값을 암호화하여 사용합니다.
	 *      다음의 사이트를 활용하지 않도록 합니다. 사용되는 암호화 알고리즘이 달라 오류가 발생합니다.
	 *      > https://www.devglan.com/online-tools/jasypt-online-encryption-decryption#google_vignette
	 *
	 *  📒 guide.
	 *      1. 평문_암호화() 메소드의 String password에 암호화할 plain text를 입력합니다.
	 *      2. 메소드를 실행하고 콘솔창에 출력된 결과 암호문을 확인합니다.
	 *      3. 암호화된 값을 복사하여 yml 설정 파일에 입력합니다.
	 *      4. 서버 실행시 VM Option에 '-Djasypt.encryptor.password=암호화키'를 추가하여 실행합니다.
	 *
	 *  ▶️ ex.
	 *      <application.yml>
	 *      db:
	 *        username: root
	 *        password: ENC(NDFNEII2=/MENFIAJQ==)
	 */

	@Test
	void 평문_암호화() {

		String password1 = "plain";
		String password2 = "plain";

		System.out.println("### Encode Password1 : " + jasyptEncoding(password1, true));
		System.out.println("### Encode Password2 : " + jasyptEncoding(password2, true));
	}

	@Test
	void 복호화() {

		String password1 = "cipher";
		String password2 = "cipher";

		System.out.println("### Decode Password1 : " + jasyptEncoding(password1, false));
		System.out.println("### Decode Password2 : " + jasyptEncoding(password2, false));
	}

	private String jasyptEncoding(String value, boolean isEncode) {

		String key = "Vnelwl@1!";

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(key);                       // 암호화 키
		config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘
		config.setKeyObtentionIterations(1000);           // 해싱 횟수
		config.setPoolSize(1);                            // 인스턴스 pool
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");               // 인코딩 방식
		encryptor.setConfig(config);

		if (isEncode) {
			return encryptor.encrypt(value);

		}
		return encryptor.decrypt(value);
	}
}
