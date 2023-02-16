package com.project.toy.file.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile uploadFile) throws IOException {
    	String origName = uploadFile.getOriginalFilename();
    	String url;
    	
		try {
			final String ext = origName.substring(origName.lastIndexOf('.'));
			
	        final String fileName = getUuid() + ext;
	    	File file = new File(System.getProperty("user.dir") + "/" + fileName);
	    	
	    	uploadFile.transferTo(file);
	    	uploadOnS3(fileName, file);
	    	
	    	url = bucket + "/image/" + file;
	    	
	    	file.delete();
		} catch (StringIndexOutOfBoundsException  e) {
			url = null;
		}
		
		return url;
    }
    
    private static String getUuid() {
    	int length = 10;
		String result = "";

		Random r = new Random();

		int intValue;
		char charValue;
		for (int i = 0; i < length / 2; i++) {
			intValue = (int) (Math.random() * 9) + 1;
			charValue = (char) (r.nextInt(26) + 65);

			result += (charValue + Integer.toString(intValue));
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result = sdf.format(new Date()) + "_" + result;
		return result;
    }
    
    private void uploadOnS3(final String findName, final File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = TransferManagerBuilder.standard()
                                                .withS3Client(this.amazonS3).build();
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket + "/image", "pickone_" + findName, file);
        
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException | InterruptedException amazonClientException) {
            amazonClientException.printStackTrace();
        }
    }
}
