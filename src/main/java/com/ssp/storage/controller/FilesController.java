package com.ssp.storage.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssp.storage.Beans.FileBean;
import com.ssp.storage.service.IFilesService;
import com.ssp.storage.web.ResponseEntity;

@RestController
@RequestMapping("/api/files")
public class FilesController {

	Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	IFilesService fileService;

	@PostMapping("/addFile")
	public ResponseEntity<?> addFile(@RequestBody MultipartFile file, @RequestHeader String parent,
			@RequestHeader String userName) {
		return new ResponseEntity<>(fileService.addFile(file, parent, userName), HttpStatus.OK);
	}

	@GetMapping("/getFile")
	public void getFile(@RequestHeader String folder, @RequestHeader String parentFolder,
			@RequestHeader String userName, @RequestHeader String fileName, HttpServletResponse response, @RequestHeader String tracePath) {
		FileBean file = fileService.getFile(folder, parentFolder, userName, fileName, tracePath);
		/*
		 * java.io.File file1 = java.io.File.createTempFile(file.getFileName(), "tmp",
		 * new java.io.File("/")); try { FileOutputStream fos = new
		 * FileOutputStream(file1); fos.write(file.getFile()); fos.close(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		response.setContentType("application");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getFile().getFileName());
		try {
			ServletOutputStream sos = response.getOutputStream();
			sos.write(file.getFile().getFile());
			sos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
