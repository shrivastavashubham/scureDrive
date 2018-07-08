package com.ssp.storage.beans;

import java.util.List;

import com.ssp.storage.domain.File;
import com.ssp.storage.domain.Question;

public class FileBean {

	private File file;
	
	private List<Question> listOfQuestions;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<Question> getListOfQuestions() {
		return listOfQuestions;
	}

	public void setListOfQuestions(List<Question> listOfQuestions) {
		this.listOfQuestions = listOfQuestions;
	}

	@Override
	public String toString() {
		return "FileBean [file=" + file + ", listOfQuestions=" + listOfQuestions + "]";
	}
	
	
	
}
