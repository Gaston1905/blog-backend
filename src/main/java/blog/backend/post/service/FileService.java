package blog.backend.post.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  String savedFileName(MultipartFile multipartFile, String path) throws IOException;

  InputStream getImage(String path, String filename) throws FileNotFoundException;

  String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException;
}
