/**
 * 
 */
package com.mts.springboot.file.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author dumingchun
 *
 */
@ApiModel(value = "文件信息")
public class FileInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "文件ID")
	private String fileId; // 文件ID
	@ApiModelProperty(value = "文件名称")
	private String fileName;// 文件名称
	@ApiModelProperty(value = "文件存储路径：http://${contextPath}/image/${storePath}")
	private String storePath; // 文件路径
	@ApiModelProperty(value = "文件url,暂不使用")
	private String href;// 文件url

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "FileInfo [fileId=" + fileId + ", fileName=" + fileName + ", storePath=" + storePath + ", href=" + href
				+ "]";
	}

}
