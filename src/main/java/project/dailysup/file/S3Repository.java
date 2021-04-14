package project.dailysup.file;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@NoArgsConstructor
public class S3Repository {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public Optional<byte[]> download(String filepath){

        log.info("download key: "+ filepath);

        try {
            byte[] contents = s3Client
                                    .getObject(bucket, filepath)
                                    .getObjectContent()
                                    .readAllBytes();
            return Optional.of(contents);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> upload(String path, MultipartFile file) {
        String fileName = path + UUID.randomUUID().toString();
        log.info("saved file name : " + fileName);
        try {
            // withCannedAcl(~.PublicRead) : 해당 파일에 public read 권한 주기.
            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(fileName);
    }

    public boolean modify(String filepath, MultipartFile file){
        try {
            //withCannedAcl(~.PublicRead) : 해당 파일에 public read 권한 주기.
            s3Client.putObject(new PutObjectRequest(bucket, filepath, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(String filepath){
        try {
            s3Client.deleteObject(bucket,filepath);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}