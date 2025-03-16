package com.breaditnow.owner.global.s3;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
	String upload(MultipartFile multipartFile, String dirName);
}
