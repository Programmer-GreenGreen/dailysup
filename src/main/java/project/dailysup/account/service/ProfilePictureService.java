package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.exception.ProfileNotFoundException;
import project.dailysup.common.exception.FailedFileSaveException;
import project.dailysup.common.file.S3Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProfilePictureService {

    private static final String PROFILE_PATH = "profile/";

    private final S3Service s3Service;

    @PostConstruct
    public void informProfilePictureServiceInjection(){
        log.info(s3Service.getClass().getName());
    }

    public String upload(MultipartFile profilePicture) {
        return s3Service.upload(PROFILE_PATH,profilePicture)
                .orElseThrow(()->new FailedFileSaveException("프로필 사진 저장에 실패했습니다."));
    }

    public byte[] download(String filepath) {
        return s3Service.download(filepath)
                .orElseThrow(()->new ProfileNotFoundException("프로필 사진 찾기 실패"));
    }

    public void delete(String filepath){
        if(!s3Service.delete(filepath)){
            throw new ProfileNotFoundException("프로필 사진 찾기 실패");
        }
    }
}
