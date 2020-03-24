package com.spring.common.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import org.imgscalr.Scalr;

public class FileUploadUtil {
	private static Logger log = LoggerFactory.getLogger(FileUploadUtil.class);
	
	
	private static Logger = LoggerFactory.getLogger(ChartMake.class);
	
	// 파일 Thumbnail 생성 메서드
	public static String makeThumbnail(String fileName, HttpServletRequest request)
	throws Exception{
		String dirName = fileName.substring(0, fileName.indexOf("_")); 
		
		// 이미지가 존재하는 폴더 추출
		String imgPath = request.getSession().getServletContext().getRealPath("/uploadStorage/" + dirName);
		// 추출된 풀더의 실제 경로(물리적 위치)
		File fileAdd = new File(imgPath, fileName);
		log.info("원본 이미지 파일(fileAdd) : " + fileAdd);
		
		BufferedImage sourceImg = ImageIO.read(fileAdd);
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 133);
		// resize(대상 [BufferedImage 타입], 원본비율, 높이 또는 너비, 크기)
		
		String thumbnailName = "thumbnail_"+ fileName;
		String docRoot = imgPath + "/thumbnail";
		makeDir(docRoot);
		
		File newFile = new File(docRoot, thumbnailName);
		log.info("업로드 할 파일(newFile) : " + newFile);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		log.info("확장자(formatName) : " + formatName);
		
		ImageIO.write(destImg, formatName, newFile);
		return thumbnailName;
	}
}
