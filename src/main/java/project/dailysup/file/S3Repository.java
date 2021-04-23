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
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

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

        byte[] contents = null;
        try {
             contents = s3Client
                            .getObject(bucket, filepath)
                            .getObjectContent()
                            .readAllBytes();
        } catch (Exception e) {
            log.info(LogFactory.create(LogCode.S3_DOWN_FAIL, filepath,e.getMessage()));
            return Optional.empty();
        }
        log.info(LogFactory.create(LogCode.S3_DOWN_SUC, filepath));
        return Optional.of(contents);
    }

    public Optional<String> upload(String path, MultipartFile file) {
        String filepath = path + UUID.randomUUID().toString();
        try {
            // withCannedAcl(~.PublicRead) : 해당 파일에 public read 권한 주기.
            s3Client.putObject(new PutObjectRequest(bucket, filepath, file.getInputStream(), null)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.info(LogFactory.create(LogCode.S3_UP_FAIL, filepath, e.getMessage()));
            return Optional.empty();
        }

        log.info(LogFactory.create(LogCode.S3_UP_SUC, filepath));
        return Optional.of(filepath);
    }

    public boolean modify(String filepath, MultipartFile file){
        try {
            //withCannedAcl(~.PublicRead) : 해당 파일에 public read 권한 주기.
            s3Client.putObject(new PutObjectRequest(bucket, filepath, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.info(LogFactory.create(LogCode.S3_MOD_FAIL, filepath,e.getMessage()));
            return false;
        }
        log.info(LogFactory.create(LogCode.S3_MOD_SUC, filepath));
        return true;
    }

    public boolean delete(String filepath){
        try {
            s3Client.deleteObject(bucket,filepath);
        } catch (Exception e) {
            log.info(LogFactory.create(LogCode.S3_DEL_FAIL, filepath,e.getMessage()));
            return false;
        }
        log.info(LogFactory.create(LogCode.S3_DEL_SUC, filepath));
        return true;
    }
}