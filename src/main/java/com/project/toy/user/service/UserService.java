package com.project.toy.user.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.gson.JsonObject;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.project.toy.common.utils.ScriptUtils;
import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.Role;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Value("${cloud.aws.s3.bucket}")
    public String bucket;


	private final UserMapper userMapper;
	private final SecurityMapper securityMapper;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AmazonS3 amazonS3;
	private final HttpSession session;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public UserDTO saveUser(UserDTO userDTO) {
        if(securityMapper.findByUserId(userDTO.getUserId()) != null){
            log.info("General: 이미 가입된 유저입니다.");
            log.info("General: " + userDTO.getUserId());
            userDTO = securityMapper.findByUserId(userDTO.getUserId());
        } else {
        	String rawPwd = userDTO.getUserPwd();
        	String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        	userDTO.setUserPwd(encPwd);
        	userDTO.setProvider("general");
        	userDTO.setRole(Role.GUEST);
        	securityMapper.saveUser(userDTO);
            
            log.info("General: 정상적으로 가입 완료되었습니다.");
            log.info("General: " + userDTO.getUserId());
            userDTO = securityMapper.findByUserId(userDTO.getUserId());
        }

        return userDTO;
	}
	
	public UserDTO selectUser(UserDTO params) {
		return securityMapper.findByUserId(params.getUserId());
	}
	
	public LockUserDTO selectLockUser(UserDTO params) {
		return userMapper.findByLockUser(params.getUserId());
	}

	public void updateFailLogin(LockUserDTO params) {
		userMapper.updateFailLogin(params);
	}
	
	public UserDTO findByUserId(String userEmail) {
		return userMapper.findByUserId(userEmail);
	}
	
	public UserDTO findByUserPwd(String userId, String userEmail) {
		return userMapper.findByUserPwd(userId, userEmail);
	}
	
	@Transactional
	public boolean updateProfile(UpdateUserDTO params) {
		int queryResult = 0;
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userMapper.findByUserId(sessionUser.getUserEmail());
		
		if(user.getUserId() != null) {
			queryResult = userMapper.updateProfile(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	public String upload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest upload) throws Exception {
    	JsonObject jsonObj = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = upload.getFile("upload");
		if(file != null) {
			if(file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if(file.getContentType().toLowerCase().startsWith("image/")) {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = request.getServletContext().getRealPath("/img");
						File uploadFile = new File(uploadPath);
						if(!uploadFile.exists()) {
							uploadFile.mkdirs();
						}
						fileName = getUuid();
						uploadPath = uploadPath + "/" + fileName;
						out = new FileOutputStream(new File(uploadPath));
						
						out.write(bytes);
						
						File files = new File(System.getProperty("user.dir") + "/" + fileName);
						file.transferTo(files);
				    	uploadOnS3(fileName, files);
						
						printWriter = response.getWriter();
						response.setContentType("text/html");
						String fileUrl = "https://toy-webservice.s3.ap-northeast-2.amazonaws.com/images/" + getUserId() + "/" + fileName;
						userMapper.updateProfileImg(fileUrl, getUserId());
						
						jsonObj.addProperty("uploaded", 1);
						jsonObj.addProperty("fileName", fileName);
						jsonObj.addProperty("url", fileUrl);
						
						printWriter.print(jsonObj);
						files.delete();
						
						ScriptUtils.backPage(response);
					} catch(IOException e) {
						e.printStackTrace();
					} finally {
						if(out != null) {
							out.close();
						}
						if(printWriter != null) {
							printWriter.close();
						}
					}
				}
			}
		}
		return null;
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
        final PutObjectRequest request = new PutObjectRequest(bucket + "/images/" + getUserId(), findName, file);
        
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException | InterruptedException amazonClientException) {
            amazonClientException.printStackTrace();
        }
    }
    
    public String getUserId() {
    	SessionUser user = (SessionUser) session.getAttribute("user");
    	String result = user.getUserId();
    	
    	return result;
    }
}
