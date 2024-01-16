package com.fourdays.foodage.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
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

		String password1 = "password1";
		String password2 = "password2";

		System.out.println("### Encode1 : " + jasyptEncoding(password1));
		System.out.println("### Encode2 : " + jasyptEncoding(password2));
	}

	private String jasyptEncoding(String value) {

		String key = "Vnelwl@1!";
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
		pbeEnc.setPassword(key);
		pbeEnc.setIvGenerator(new RandomIvGenerator());
		return pbeEnc.encrypt(value);
	}
}
