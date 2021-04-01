package project.dailysup.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.file.ImageNotFoundException;
import project.dailysup.common.exception.FailedFileSaveException;
import project.dailysup.file.S3Repository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemPictureService {
    //Item picture directory path in bucket
    private static final String PROFILE_PATH = "item/";

    //Item Profile Picture metadata would be included.

    private final S3Repository s3Repository;


    public String upload(MultipartFile profilePicture) {
        return s3Repository.upload(PROFILE_PATH,profilePicture)
                .orElseThrow(()->new FailedFileSaveException("아이템 사진 저장에 실패했습니다."));
    }

    public byte[] download(String filepath) {
        return s3Repository.download(filepath)
                .orElseThrow(()->new ImageNotFoundException("아이템 사진 찾기 실패"));
    }

    public void modify(String filepath, MultipartFile profilePicture){
        if(!s3Repository.modify(filepath, profilePicture)){
               throw new FailedFileSaveException("아이템 사진 수정에 실패했습니다.");
        }
    }

    public void delete(String filepath){
        if(!s3Repository.delete(filepath)){
            throw new ImageNotFoundException("아이템 사진 찾기 실패");
        }
    }
}
