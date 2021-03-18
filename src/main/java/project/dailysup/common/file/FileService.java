package project.dailysup.common.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public interface FileService {
    Optional<String> save(MultipartFile file);
    Optional<File> get(String filName);
}
