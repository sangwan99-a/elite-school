//package com.elite.school.framework.AWS;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.InputStream;
//
//@Slf4j
//@Service
//public class AwsCommonMethod {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AwsCommonMethod.class);
//
//    @Autowired
//    private S3Client s3Client;
//
//    @Autowired
//    private AbtApiConfig abtApiConfig;
//
//    public void uploadReport(MultipartFile file, String objectKey) throws AbtExternalServiceException {
//        uploadFileToS3(file, "abt-sample/" + objectKey);
//    }
//
//    public void uploadFileToS3(MultipartFile file, String objectKey) throws AbtExternalServiceException {
//        try {
//            // Transfer the file to a temporary file on the local file system
//            File tempFile = File.createTempFile("temp", null);
//            file.transferTo(tempFile);
//            // Create a Path object from the temporary file
//            Path filePath = tempFile.toPath();
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket(abtApiConfig.getS3Bucket())
//                    .key(objectKey)
//                    .build();
//            s3Client.putObject(request, filePath);
//            // Delete the temporary file
//            Files.delete(tempFile.getAbsoluteFile().toPath());
//        } catch (SdkClientException e) {
//            log.error("SdkClientException:  ", e);
//            throw new AbtExternalServiceException(e);
//        } catch (IOException e) {
//            throw new AbtExternalServiceException(e);
//        }
//    }
//
//    public InputStream downloadFileFromS3(String objectKey) {
//        try {
//            GetObjectRequest request = GetObjectRequest.builder()
//                    .bucket(abtApiConfig.getS3Bucket())
//                    .key(objectKey)
//                    .build();
//            ResponseInputStream<GetObjectResponse> s3ClientObject = s3Client.getObject(request);
//            return new ByteArrayInputStream(s3ClientObject.readAllBytes());
//        } catch (SdkClientException | IOException e) {
////            LOGGER.error("SdkClientException: ", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    private File convertS3ObjectToFile(InputStream inputStream) throws IOException {
//        File tempFile = File.createTempFile("temp", null);
//        tempFile.deleteOnExit();
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            return tempFile;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public File downloadFileFromS3Bucket(String objectKey) throws IOException {
//        return convertS3ObjectToFile(downloadFileFromS3(objectKey));
//    }
//
//
//    public byte[] convertIntoByteArray(File file) {
////        FileInputStream fis = null;
//        byte[] byteArray = null;
//        try (FileInputStream fis = new FileInputStream(file)) {
//            byteArray = new byte[(int) file.length()];
//            int bytesRead = fis.read(byteArray);
//            LOGGER.info("Total bytes read : {}",bytesRead);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return byteArray;
//    }
//
//    private void deleteFileFromS3(String objectKey) throws AbtExternalServiceException {
//        try {
//            DeleteObjectRequest request = DeleteObjectRequest.builder()
//                    .bucket(abtApiConfig.getS3Bucket())
//                    .key(objectKey)
//                    .build();
//            s3Client.deleteObject(request);
//        } catch (SdkClientException e) {
////            LOGGER.error("SdkClientException: ", e);
//            throw new AbtExternalServiceException(e);
//        }
//    }
//
//    public void uploadFileToS3UsingFileOutputStream(File file, String objectKey) {
//        try (FileInputStream fileInputStream = new FileInputStream(file)) {
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket(abtApiConfig.getS3Bucket())
//                    .key(objectKey)
//                    .build();
//
//            RequestBody requestBody = RequestBody.fromInputStream(fileInputStream, file.length());
//            s3Client.putObject(request, requestBody);
//        } catch (IOException e) {
//            throw new RuntimeException("Error uploading file to S3", e);
//        }
//    }
//
//}
