package com.breaditnow.customer.customer.domain.port;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoragePort {
    String upload(MultipartFile file, String dirName);
}
