package com.breaditnow.owner.global.s3;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.breaditnow.owner.global.exception.OwnerException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {
	private final AmazonS3 amazonS3Client;

	@Value("${aws.s3.bucket}")
	private String bucket;

	public String upload(MultipartFile multipartFile, String dirName) {
		File localFile = convertToFile(multipartFile)
			.orElseThrow(() -> new OwnerException(FILE_CREATION_FAILED));

		String uploadFileToS3Url = uploadFileToS3(localFile, dirName);
		deleteLocalFile(localFile);
		return uploadFileToS3Url;
	}

	public void deleteFile(String fileUrl) {
		try {
			URL url = new URL(fileUrl);
			log.info("fileUrl : {}", fileUrl);
			String key = url.getPath().startsWith("/") ? url.getPath().substring(1) : url.getPath();
			amazonS3Client.deleteObject(bucket, key);
			log.info("S3에서 객체 삭제 완료: {}", key);
		} catch (MalformedURLException e) {
			log.error("잘못된 URL입니다: {}", fileUrl, e);
			throw new IllegalArgumentException("유효하지 않은 파일 URL입니다.");
		}
	}

	private String uploadFileToS3(File file, String dirName) {
		String key = dirName + "/" + file.getName();
		amazonS3Client.putObject(new PutObjectRequest(bucket, key, file));
		return amazonS3Client.getUrl(bucket, key).toString();
	}

	private Optional<File> convertToFile(MultipartFile multipartFile) {
		String tempDir = System.getProperty("java.io.tmpdir");
		String uniqueFileName = multipartFile.getOriginalFilename();
		File file = new File(tempDir, uniqueFileName);
		try {
			if (file.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(multipartFile.getBytes());
				}
				return Optional.of(file);
			} else {
				log.error("새 파일 생성에 실패했습니다: {}", file.getAbsolutePath());
			}
		} catch (IOException e) {
			throw new OwnerException(FILE_UPLOAD_FAILED, e);
		}
		return Optional.empty();
	}

	private void deleteLocalFile(File file) {
		if (file.delete()) {
			log.info("로컬 파일 삭제 성공: {}", file.getAbsolutePath());
		} else {
			log.warn("로컬 파일 삭제 실패: {}", file.getAbsolutePath());
		}
	}
}
