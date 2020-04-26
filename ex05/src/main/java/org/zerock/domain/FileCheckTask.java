package org.zerock.domain;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron = "0 0 2 * * *")
	public void checkFiles()throws Exception{
		
		log.warn("파일 체크 테스크 중..");
		log.warn(new Date());
		//파일 리스트 데이터베이스
		List<BoardAttachVo>fileList = attachMapper.getOldFiles();
		
		//데이터파일 리스트 파일 체크 준비
		List<Path>fileListPaths = fileList.stream()
				.map(vo->Paths.get("C:\\upload",vo.getUploadPath(),
						vo.getUuid()+"_"+vo.getFileName()))
				.collect(Collectors.toList());
		
		//이미지 파일은 썸네일 파일 가지고 있다
		fileList.stream().filter(vo->vo.isFileType()==true)
		.map(vo->Paths.get("c:\\upload",vo.getUploadPath(),"s_"+
		vo.getUuid()+"_"+vo.getFileName()))
		.forEach(p->fileListPaths.add(p));
		
		log.warn("==============================================");
		
		fileListPaths.forEach(p->log.warn(p));
		
		//어제 파일 디렉토리
		File targetDir = Paths.get("C:\\upload",getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file->fileListPaths.contains(file.toPath())== false);
		
		log.warn("==============================================");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
}
