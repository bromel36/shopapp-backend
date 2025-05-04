package vn.ptithcm.shopapp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.response.FileResponseDTO;
import vn.ptithcm.shopapp.service.IFileService;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Map;

@Service
public class FileService implements IFileService {
    private final Cloudinary cloudinary;

    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public FileResponseDTO uploadImage(MultipartFile file, String folderName)  {
        String originalFilename = file.getOriginalFilename(); // ví dụ: "avatar.png"

        if(originalFilename == null){
            throw new IdInvalidException("File is not contain name");
        }
        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File is not an image");
        }

        String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + System.currentTimeMillis();;

        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folderName,
                    "public_id", fileNameWithoutExt, // 👈 giữ tên gốc (không có .png, .jpg)
                    "overwrite", true // nếu trùng thì ghi đè
            ));
        } catch (IOException e) {
            throw new IdInvalidException("Failed to upload file");
        }

        String url = (String) uploadResult.get("secure_url");
        return FileResponseDTO.builder()
                .fileLink(url)
                .fileName(fileNameWithoutExt)
                .uploadedTime(Instant.now())
                .build();
    }
    @Override
    public void createUpLoadFolder(String fullDirectoryPath) throws URISyntaxException {
        URI uri = new URI(fullDirectoryPath);
        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());
        if(!tmpDir.isDirectory()){
            try{
                Files.createDirectory(tmpDir.toPath());
                System.out.println(">>>>> created new directory successful "+ tmpDir.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println(">>>>>skip making, directory already exists");
        }
    }

    @Override
    public FileResponseDTO storeFile(MultipartFile file, String fullDirectoryPath) throws IOException, URISyntaxException {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()); //handle file name contains space character

        URI uri = new URI(fullDirectoryPath+ "/" + encodedFileName);

        Path path = Paths.get(uri);

        try(InputStream in = file.getInputStream()){
            Files.copy(in,path, StandardCopyOption.REPLACE_EXISTING);
        }
        return FileResponseDTO.builder()
                .fileName(encodedFileName)
                .uploadedTime(Instant.now())
                .build();
    }

    @Override
    public long getFileLength(String file, String fullDirectoryPath) throws URISyntaxException {
        URI uri = new URI( fullDirectoryPath + "/" + file);

        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());

        if(tmpDir.isDirectory() || !tmpDir.exists()){
            return 0;
        }
        return tmpDir.length();
    }

    @Override
    public InputStreamResource getResource(String file, String fullDirectoryPath) throws URISyntaxException, FileNotFoundException {
        URI uri = new URI(fullDirectoryPath + "/" + file);

        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());

        return new InputStreamResource(new FileInputStream(tmpDir));
    }
}
