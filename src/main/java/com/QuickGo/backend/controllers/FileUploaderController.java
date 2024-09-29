package com.QuickGo.backend.controllers;


import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.service.FileUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/fileUploader")
public class FileUploaderController {

    @Autowired
    private FileUploaderService fileUploaderService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> uploadImage(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        200,
                        "Success",
                        fileUploaderService.uploadFile(file)
                )
        );
    }

    @GetMapping()
    public ResponseEntity<byte[]> getImage(@RequestParam String fileName) {
        return fileUploaderService.getFile(fileName);
    }


}
