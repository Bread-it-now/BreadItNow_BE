package com.breaditnow.image.application.port.out;

public interface StoragePort {
    String uploadImage(byte[] imageBytes, String originalFilename);
}
