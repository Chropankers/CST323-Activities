package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.example.demo.models.FileMetadata;
import com.example.demo.repository.FileMetadataRepository;

@Service
public class WasabiStorageService {

    private final AmazonS3 s3Client;
    private final FileMetadataRepository fileMetadataRepository;
    @Value("${wasabi.bucketName}")
    private String bucketName;

    @Autowired
    public WasabiStorageService(FileMetadataRepository fileMetadataRepository,
                                @Value("${wasabi.accessKey}") String accessKey,
                                @Value("${wasabi.secretKey}") String secretKey,
                                @Value("${wasabi.endpoint}") String endpoint,
                                @Value("${wasabi.region}") String region) {
        this.fileMetadataRepository = fileMetadataRepository;
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    public String uploadFile(MultipartFile file, Long userId, Long serverId) throws IOException 
    {
        String fileKey = "server/" + serverId + "/user/" + userId + "/" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(new PutObjectRequest(bucketName, fileKey, file.getInputStream(), metadata));

        // Save metadata to database
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setUserId(userId);
        fileMetadata.setServerId(serverId);
        fileMetadata.setFileKey(fileKey);
        fileMetadataRepository.save(fileMetadata);

        return fileKey;
    }


    public S3Object downloadFile(Long userId, String fileName) {
        Optional<FileMetadata> fileMetadataOpt = fileMetadataRepository.findByUserIdAndFileName(userId, fileName);
        if (fileMetadataOpt.isPresent()) {
            FileMetadata fileMetadata = fileMetadataOpt.get();
            return s3Client.getObject(new GetObjectRequest(fileMetadata.getBucketName(), fileMetadata.getFileKey()));
        }
        throw new RuntimeException("File not found");
    }

    public String getFileUrl(String fileKey) {
        return s3Client.getUrl(bucketName, fileKey).toString();
    }

    public void testWasabiConnection() {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new RuntimeException("Bucket " + bucketName + " does not exist or cannot be accessed.");
        }
    }
    
}
