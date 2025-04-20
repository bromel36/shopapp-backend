package vn.ptithcm.shopapp.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptithcm.shopapp.model.response.FileResponseDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


public interface IFileService {
    void createUpLoadFolder(String fullDirectoryPath) throws URISyntaxException;

    FileResponseDTO storeFile(MultipartFile file, String fullDirectoryPath) throws IOException, URISyntaxException;

    long getFileLength(String file, String fullDirectoryPath) throws URISyntaxException;

    InputStreamResource getResource(String file, String fullDirectoryPath) throws URISyntaxException, FileNotFoundException;

    FileResponseDTO uploadImage(MultipartFile file, String folderName);
}
