package com.ssp.storage.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "folder")
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.folder_seq")
	@SequenceGenerator(sequenceName = "public.folder_seq", allocationSize = 1, name = "public.folder_seq")
	private long id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENTFODERID", referencedColumnName = "id")
	@JsonIgnore
	private Folder parent;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENTFOLDERID")
	private List<Folder> children;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private List<File> files;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonIgnore
	private User userId;

	private boolean root;

	private String folderName;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public List<Folder> getChildren() {
		return children;
	}

	public void setChildren(List<Folder> children) {
		this.children = children;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", parent=" + parent + ", children=" + children + ", files=" + files + ", userId="
				+ userId + ", root=" + root + "]";
	}

}
