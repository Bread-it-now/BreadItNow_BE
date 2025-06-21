package com.breaditnow.owner.image.application.port.out;

public interface StoragePort {
    String uploadImage(byte[] imageBytes, String originalFilename);
}
