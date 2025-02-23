package com.breaditnow.owner.global.s3;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class S3UploaderTest {

	@Autowired
	private S3Uploader s3Uploader;

	@Test
	void uploadToRealS3() {
		String fileContent = "This is a test file for S3 integration upload.";
		MockMultipartFile multipartFile = new MockMultipartFile(
			"file",
			"integration-test.txt",
			"text/plain",
			fileContent.getBytes(StandardCharsets.UTF_8)
		);

		String dirName = "integration-test";
		String uploadUrl = s3Uploader.upload(multipartFile, dirName);
		System.out.println("Uploaded file URL: " + uploadUrl);

		assertNotNull(uploadUrl);
		assertTrue(uploadUrl.contains("s3"));
	}
}
