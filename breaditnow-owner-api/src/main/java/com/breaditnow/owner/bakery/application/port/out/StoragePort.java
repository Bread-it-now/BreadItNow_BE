package com.breaditnow.owner.bakery.application.port.out;

public interface StoragePort {
    String uploadImage(byte[] imageBytes, String originalFilename);
}
