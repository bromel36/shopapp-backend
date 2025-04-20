package vn.ptithcm.shopapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptithcm.shopapp.error.StorageException;
import vn.ptithcm.shopapp.model.response.FileResponseDTO;
import vn.ptithcm.shopapp.service.IFileService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "File")
public class FileController {

    @Value("${bromel.upload-file.base-uri}")
    private String baseUri;

    @Value("${bromel.upload-file.remote-folder}")
    private String remoteFolder;

    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

        @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Upload a file", description = "Uploads a file to the specified folder.")
        @ApiMessage("Upload a file")
        public ResponseEntity<FileResponseDTO> upload(
                @RequestParam(value = "file", required = true) MultipartFile file,
                @RequestParam(value = "folder", required = true) String folder)  {

                String fullDirectory = remoteFolder + folder;
                return ResponseEntity.ok().body(fileService.uploadImage(file, fullDirectory));

        }

//    @Operation(summary = "Upload a file", description = "Uploads a file to the specified folder.")
//    @ApiMessage("Upload a file")
//    @PostMapping("/files")
//    public ResponseEntity<FileResponseDTO> upload(
//            @RequestParam(value = "file", required = false) MultipartFile file,
//            @RequestParam("folder") String folder) throws URISyntaxException, IOException {
//        String fullDirectoryPath = baseUri + folder;
//
//        if (file == null || file.isEmpty()) {
//            throw new StorageException("File is empty!!!");
//        }
//
//        String fileName = file.getOriginalFilename();
//        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
//
//        boolean isValid = allowedExtensions.stream().anyMatch(it -> fileName.toLowerCase().endsWith("." + it));
//
//        if (!isValid) {
//            throw new StorageException("File extension is not valid!!!. Only accept " + allowedExtensions.toString());
//        }
//
//        this.fileService.createUpLoadFolder(fullDirectoryPath);
//
//        FileResponseDTO responseDTO = this.fileService.storeFile(file, fullDirectoryPath);
//
//        return ResponseEntity.ok().body(responseDTO);
//    }

    @Operation(summary = "Download a file", description = "Downloads a file from the specified folder.")
    @GetMapping("/files")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("file") String file,
            @RequestParam("folder") String folder) throws URISyntaxException, FileNotFoundException {

        String fullDirectoryPath = baseUri + folder;

        if (file.isBlank() || folder.isBlank()) {
            throw new StorageException("File or folder is empty!!!");
        }

        long fileLength = this.fileService.getFileLength(file, fullDirectoryPath);

        if (fileLength == 0) {
            throw new StorageException("File name with " + file + " not found");
        }
        InputStreamResource resource = this.fileService.getResource(file, fullDirectoryPath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
