package com.kosuru.kompany.satyawedschelsea.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

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
@Table(name = "Users")
public class Users {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "user_id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFamilyDetails() {
		return familyDetails;
	}

	public void setFamilyDetails(String familyDetails) {
		this.familyDetails = familyDetails;
	}

	public boolean isFamily() {
		return family;
	}

	public void setFamily(boolean family) {
		this.family = family;
	}

	public boolean isSendRsvp() {
		return sendRsvp;
	}

	public void setSendRsvp(boolean sendRsvp) {
		this.sendRsvp = sendRsvp;
	}

	public int getPlus() {
		return plus;
	}

	public void setPlus(int plus) {
		this.plus = plus;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getRsvpSentOn() {
		return rsvpSentOn;
	}

	public void setRsvpSentOn(LocalDateTime rsvpSentOn) {
		this.rsvpSentOn = rsvpSentOn;
	}

	public LocalDateTime getRsvpUpdatedOn() {
		return rsvpUpdatedOn;
	}

	public void setRsvpUpdatedOn(LocalDateTime rsvpUpdatedOn) {
		this.rsvpUpdatedOn = rsvpUpdatedOn;
	}

	public String getRsvpUpdatedBy() {
		return rsvpUpdatedBy;
	}

	public void setRsvpUpdatedBy(String rsvpUpdatedBy) {
		this.rsvpUpdatedBy = rsvpUpdatedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPlaylistSuggetion() {
		return playlistSuggetion;
	}

	public void setPlaylistSuggetion(String playlistSuggetion) {
		this.playlistSuggetion = playlistSuggetion;
	}

	public String getOtherSuggetion() {
		return otherSuggetion;
	}

	public void setOtherSuggetion(String otherSuggetion) {
		this.otherSuggetion = otherSuggetion;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
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

	public LocalDateTime getPasswordUpdatedOn() {
		return passwordUpdatedOn;
	}

	public void setPasswordUpdatedOn(LocalDateTime passwordUpdatedOn) {
		this.passwordUpdatedOn = passwordUpdatedOn;
	}

	public String getPasswordUpdatedBy() {
		return passwordUpdatedBy;
	}

	public void setPasswordUpdatedBy(String passwordUpdatedBy) {
		this.passwordUpdatedBy = passwordUpdatedBy;
	}

	public int getNoOfWrongPasswords() {
		return noOfWrongPasswords;
	}

	public void setNoOfWrongPasswords(int noOfWrongPasswords) {
		this.noOfWrongPasswords = noOfWrongPasswords;
	}

	public String getUserProfilePicUrl() {
		return userProfilePicUrl;
	}

	public void setUserProfilePicUrl(String userProfilePicUrl) {
		this.userProfilePicUrl = userProfilePicUrl;
	}

	public MultipartFile getUserProfilePicture() {
		return userProfilePicture;
	}

	public void setUserProfilePicture(MultipartFile userProfilePicture) {
		this.userProfilePicture = userProfilePicture;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "family_details", length = 500)
	private String familyDetails;

	@Column(name = "family")
	private boolean family;

	@Column(name = "send_rsvp")
	private boolean sendRsvp;

	@Column(name = "plus")
	private int plus;

	@Column(name = "contact_type")
	private String contactType;

	@Column(name = "contact")
	private String contact;

	@Column(name = "address")
	private String address;

	@Column(name = "rsvp_sent_on")
	private LocalDateTime rsvpSentOn;

	@Column(name = "rsvp_updated_on")
	private LocalDateTime rsvpUpdatedOn;

	@Column(name = "rsvp_updated_by")
	private String rsvpUpdatedBy;

	@Column(name = "comments", length = 1000)
	private String comments;

	@Column(name = "playlist_suggetion")
	private String playlistSuggetion;

	@Column(name = "other_suggetion")
	private String otherSuggetion;

	@Column(name = "role")
	private String role;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "secret", length = 500)
	private String secret;

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

	@Column(name = "user_profile_pic_url")
	private String userProfilePicUrl;

	@Column(name = "password_updated_on")
	private LocalDateTime passwordUpdatedOn;

	@Column(name = "password_updated_by")
	private String passwordUpdatedBy;

	@Column(name = "number_of_wrong_passwords")
	private int noOfWrongPasswords;

	@Transient
	private MultipartFile userProfilePicture;

	@Transient
	private String newPassword;

	@Transient
	private String confirmNewPassword;
}
