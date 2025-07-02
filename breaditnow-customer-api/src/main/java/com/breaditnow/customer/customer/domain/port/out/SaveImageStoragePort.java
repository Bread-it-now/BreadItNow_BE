package com.breaditnow.customer.customer.domain.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface SaveImageStoragePort {
    String upload(MultipartFile file, String dirName);
}
