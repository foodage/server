package com.fourdays.foodage.review.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.review.exception.S3Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class S3ImageService {

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    public S3ImageService(AmazonS3Client amazonS3Client, AmazonS3 amazonS3) {
        this.amazonS3Client = amazonS3Client;
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /** 빈 파일 검증 **/
    public String upload(MultipartFile image) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(ResultCode.ERR_S3_EMPTY_FILE_EXCEPTION);
        }

        return this.uploadImage(image);
    }

    /** S3에 파일 업로드 **/
    private String uploadImage(MultipartFile image) {
        this.validateFileExtention(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new S3Exception(ResultCode.ERR_S3_IO_EXCEPTION_ON_FILE_UPLOAD);
        }
    }

    /** 실제 S3로 파일을 보내는 로직 **/
    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalImageName = image.getOriginalFilename();   // 원본 파일명
        String extention = originalImageName.substring(originalImageName.lastIndexOf("."));   // 확장자 명
        String s3ImageName = UUID.randomUUID().toString().substring(0, 10) + originalImageName;   // S3에 올릴 파일명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); // 파일을 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); // metadata 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);    // S3 요청할 때 사용할 byteInputStream 생성

        try {
            // S3로 pubObject할 때 사용할 요청 객체
            // 생성자 : bucket이름, 파일명, byteInputStream, metadata
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName, s3ImageName, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new S3Exception(ResultCode.ERR_S3_PUT_OBJECT_EXCEPTION);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3ImageName).toString();
    }

    /** 파일 확장자 검증 **/
    private void validateFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new S3Exception(ResultCode.ERR_S3_NO_FILE_EXTENTION);
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new S3Exception(ResultCode.ERR_S3_INVALID_FILE_EXTENTION);
        }
    }

    /** S3 이미지 삭제 **/
    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (Exception e) {
            throw new S3Exception(ResultCode.ERR_S3_IMAGE_DELETE_EXCEPTION);
        }
    }

    /** 이미지 주소 가져오기 **/
    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1);
        } catch (MalformedURLException | UnsupportedEncodingException exception) {
            throw new S3Exception(ResultCode.ERR_S3_IMAGE_DELETE_EXCEPTION);
        }
    }
}
