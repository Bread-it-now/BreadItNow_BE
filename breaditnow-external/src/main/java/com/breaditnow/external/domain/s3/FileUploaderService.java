package com.breaditnow.external.domain.s3;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
	String upload(MultipartFile multipartFile, String dirName);
}
