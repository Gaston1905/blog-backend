package blog.backend.post.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public class FileServiceImpl implements FileService {

  @Override
  public String savedFileName(MultipartFile multipartFile, String path) throws IOException {
    Path uploadPath = Paths.get(path);
    String fileName = multipartFile.getOriginalFilename();
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    String fileCode = RandomStringUtils.randomAlphanumeric(8);
    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
      fileName = fileCode + "-" + fileName;
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (java.io.IOException ioe) {
      throw new IOException("Archivo no salvado!!");
    }
    return fileName;
  }

  @Override
  public InputStream getImage(String path, String filename) throws FileNotFoundException {
    String fullPath = path + File.separator + filename;
    InputStream is = new FileInputStream(fullPath);
    return is;
  }

  @Override
  public String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws java.io.IOException {
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try (InputStream inputStream = multipartFile.getInputStream()) {
      String fileCode = RandomStringUtils.randomAlphanumeric(8);
      fileName = fileCode + "-" + fileName;
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (java.io.IOException ioe) {
      throw new IOException("Imagen no salvada!!");
    }
  }

}
