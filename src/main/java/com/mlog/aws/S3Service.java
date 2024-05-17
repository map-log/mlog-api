package com.mlog.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadBase64Image(String base64Image) {
        // 데이터 URL 스키마 파싱
        String[] parts = base64Image.split(",");
        String metadataPart = parts[0];
        String base64DataPart = parts[1];

        // MIME 타입 추출
        String mimeType = metadataPart.split(";")[0].split(":")[1];
        String fileExtension = getFileExtension(mimeType);

        byte[] decodedBytes = Base64.getDecoder().decode(base64DataPart);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedBytes.length);
        metadata.setContentType(mimeType);

        amazonS3.putObject(bucket, uniqueFileName, inputStream, metadata);

        return amazonS3.getUrl(bucket, uniqueFileName).toString();
    }

    private String getFileExtension(String mimeType) {
        switch (mimeType) {
            case "image/jpeg":
                return ".jpeg";
            case "image/png":
                return ".png";
            case "image/gif":
                return ".gif";
            case "image/bmp":
                return ".bmp";
            case "image/webp":
                return ".webp";
            default:
                throw new IllegalArgumentException("Unsupported image format: " + mimeType);
        }
    }
}
