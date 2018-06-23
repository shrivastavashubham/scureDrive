package com.ssp.storage.domain;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "files")
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.file_seq")
	@SequenceGenerator(sequenceName = "public.file_seq", allocationSize = 1, name = "public.file_seq")
	private long id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "folderId", referencedColumnName = "id")
	@JsonIgnore
	private Folder folderId;
	
	
	private  byte[] file;
	
	private String absolutePath;
	
	private String fileName;
	
	
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Folder getFolderId() {
		return folderId;
	}

	public void setFolderId(Folder folderId) {
		this.folderId = folderId;
	}



	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", folderId=" + folderId + ", file=" + file + ", absolutePath=" + absolutePath
				+ ", fileName=" + fileName + "]";
	}


	
}
