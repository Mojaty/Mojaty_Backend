package com.motivation.mojaty.global.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;

    public static final String IMAGE_URL = "https://bssm-re-code.s3.ap-northeast-2.amazonaws.com/";

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String imgPath = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        amazonS3.putObject(new PutObjectRequest(bucket, imgPath, multipartFile.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return String.valueOf(amazonS3Client.getUrl(bucket, imgPath));
    }

    public void deleteFile(String imgUrl) {
        String imgPath = imgUrl.substring(IMAGE_URL.length());
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imgPath));
    }
}
