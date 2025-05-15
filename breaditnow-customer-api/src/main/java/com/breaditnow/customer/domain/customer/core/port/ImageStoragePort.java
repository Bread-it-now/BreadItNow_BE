package com.breaditnow.customer.domain.customer.core.port;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoragePort {
    String upload(MultipartFile file, String dirName);
}
