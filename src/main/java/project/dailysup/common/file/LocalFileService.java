package project.dailysup.common.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LocalFileService implements FileService {
    @Override
    public Optional<String> save(MultipartFile file) {
        String filename = UUID.randomUUID().toString();
        String savePath = System.getProperty("user.dir") + "/files";
        try {
            if (!new File(savePath).exists()) {
                new File(savePath).mkdir();
            }
            String filePath = savePath + "/" + filename;
            file.transferTo(new File(filePath));
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(filename);
    }

    @Override
    public Optional<File> get(String filName) {
        String savePath = System.getProperty("user.dir") + "/files";
        File file = new File(savePath + "/" + filName);

        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(file);
    }
}
