package com.ssp.storage.service.impl;

import java.sql.Blob;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssp.storage.domain.File;
import com.ssp.storage.domain.Folder;
import com.ssp.storage.repository.FileRepository;
import com.ssp.storage.repository.FolderRepository;
import com.ssp.storage.service.IFilesService;

@Service
public class FilesService implements IFilesService {

	@Autowired
	FolderRepository folderRepository;

	@Autowired
	FileRepository fileRepository;

	@Override
	public boolean addFile(MultipartFile file, String parent, String userName) {
		Folder parentFolder = folderRepository.findByUserIdUsernameAndFolderName(userName, parent);

		String name = file.getName();
		byte[] bytes = null;
		Blob blob = null;
		try {
			bytes = file.getBytes();
			blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < bytes.length; i++)
			System.out.println(bytes[i]);

		File Dfile = new File();
		Dfile.setFileName(name);
		Dfile.setFile(bytes);
		Dfile.setFolderId(parentFolder);
		List<File> childFiles = parentFolder.getFiles();
		childFiles.add(Dfile);
		if (Objects.nonNull(folderRepository.save(parentFolder)))
			return true;
		return false;
	}

	@Override
	public File getFile(String folder, String parentFolder, String userName, String fileName) {
		Folder parentFolderFile = folderRepository.findByUserIdUsernameAndFolderNameAndParentFolderName(userName,
				folder, parentFolder);
		File file = fileRepository.findByFileNameAndFolderIdId(fileName, parentFolderFile.getId());
		byte[] byts = file.getFile();
		for (int i = 0; i < file.getFile().length; i++)
			System.out.println(byts[i]);

		return file;
	}

}
