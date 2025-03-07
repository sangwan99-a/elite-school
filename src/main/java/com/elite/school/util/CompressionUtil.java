package com.elite.school.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
@Component
public class CompressionUtil {

    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CompressionUtil.class);


    /**
     * Compresses the input byte array using GZIP.
     *
     * @param data the byte array to be compressed
     * @return the compressed byte array
     */
    public static byte[] compressData(byte[] data) {
        LOGGER.debug("Starting data compression.");
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            gzipOutputStream.write(data);
            gzipOutputStream.finish();
            LOGGER.debug("Data compression completed.");
            return outputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Failed to compress data", e);
            throw new RuntimeException("Failed to compress data", e);
        }
    }

    /**
     * Converts an object to a JSON byte array.
     *
     * @param object the object to be converted to JSON
     * @return the JSON byte array
     */
    public byte[] convertToJsonBytes(Object object) {
        LOGGER.debug("Converting object to JSON bytes.");
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (IOException e) {
            LOGGER.error("Failed to convert object to JSON bytes", e);
            throw new RuntimeException("Failed to convert object to JSON bytes", e);
        }
    }

    /**
     * Compresses JSON content.
     *
     * @param data the data object to be included in the response
     * @return the compressed JSON data
     */
    public  byte[] compressedResponse(Object data) {
        LOGGER.debug("Preparing compressed response.");
        byte[] originalContent = convertToJsonBytes(data);
        LOGGER.debug("Original content size: {} bytes", originalContent.length);
        byte[] compressedContent = compressData(originalContent);
        LOGGER.debug("Compressed content size: {} bytes", compressedContent.length);
        LOGGER.debug("Compressed response prepared successfully.");
        return compressedContent;
    }
}
