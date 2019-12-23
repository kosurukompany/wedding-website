package com.kosuru.kompany.satyawedschelsea.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "content_id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSessExpireTime() {
		return sessExpireTime;
	}

	public void setSessExpireTime(int sessExpireTime) {
		this.sessExpireTime = sessExpireTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getHeaderColor() {
		return headerColor;
	}

	public void setHeaderColor(String headerColor) {
		this.headerColor = headerColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getSmtpEmail() {
		return smtpEmail;
	}

	public void setSmtpEmail(String smtpEmail) {
		this.smtpEmail = smtpEmail;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getRsvpSubject() {
		return rsvpSubject;
	}

	public void setRsvpSubject(String rsvpSubject) {
		this.rsvpSubject = rsvpSubject;
	}

	public String getRsvpBody() {
		return rsvpBody;
	}

	public void setRsvpBody(String rsvpBody) {
		this.rsvpBody = rsvpBody;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MultipartFile getPicture1() {
		return picture1;
	}

	public void setPicture1(MultipartFile picture1) {
		this.picture1 = picture1;
	}

	@Column(name = "name")
	private String name;

	@Column(name = "sessionExpireTime")
	private int sessExpireTime;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	@Column(name = "header", columnDefinition = "TEXT")
	private String header;

	@Column(name = "url")
	private String url;

	@Column(name = "image1")
	private String image1;

	@Column(name = "header_color")
	private String headerColor;

	@Column(name = "bg_color")
	private String bgColor;

	@Column(name = "smtp_email")
	private String smtpEmail;

	@Column(name = "smtp_password")
	private String smtpPassword;

	@Column(name = "smtp_host")
	private String smtpHost;

	@Column(name = "smtp_port")
	private int smtpPort;

	@Column(name = "rsvp_subject")
	private String rsvpSubject;

	@Column(name = "rsvp_body", columnDefinition = "TEXT")
	private String rsvpBody;

	@Column(name = "support")
	private String support;

	@Column(name = "created_By")
	private String createdBy;

	@Column(name = "updated_By")
	private String updatedBy;

	@Column(name = "created_On")
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "updated_On")
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	@Column(name = "type")
	private String type;

	@Transient
	private MultipartFile picture1;

}
