package com.adminportal.controller.s3ctrl;

import com.adminportal.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping(value = "/s3")
public class AWSS3Ctrl {

    private AWSS3Service service;

    public AWSS3Ctrl(AWSS3Service service) {
        this.service = service;
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        String idPng = id + ".png";
        ByteArrayOutputStream downloadInputStream = service.downloadFile(idPng);

        return ResponseEntity.ok()
                .contentType(contentType(id))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + "\"")
                    .body(downloadInputStream.toByteArray());
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
