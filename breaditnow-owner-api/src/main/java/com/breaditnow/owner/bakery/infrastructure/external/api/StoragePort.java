package com.breaditnow.owner.bakery.infrastructure.external.api;

public interface StoragePort {
    String uploadImage(byte[] imageBytes, String originalFilename);
}
