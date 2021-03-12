package com.square.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Blogpost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Column
    //private Long blogger_uid;

    @OneToOne(fetch = FetchType.LAZY, targetEntity= Users.class)
    @JoinColumn(name = "blogger_uid", nullable = false)
    private Users blogger_uid;

    
    @Column
    @NotBlank
	@Size(max=500)
    private String title;
    
    @Column
    @NotBlank
	@Size(max=3000)
    private String body;
    
    @Column
    private Date creation_time;
    
    @Column
    private Integer status; //0=not approved, 1=approved
    
    //@Column
    //private Long approved_by;

    @OneToOne(fetch = FetchType.LAZY, targetEntity= Users.class)
    @JoinColumn(name = "approved_by", nullable = true)
    private Users approved_by;

    
    @Column
    private Date approve_time;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getBlogger_uid() {
		return this.blogger_uid;
	}

	public void setBlogger_uid(Users blogger_uid) {
		this.blogger_uid = blogger_uid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreation_time() {
		return this.creation_time;
	}

	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Users getApproved_by() {
		return this.approved_by;
	}

	public void setApproved_by(Users approved_by) {
		this.approved_by = approved_by;
	}

	public Date getApprove_time() {
		return this.approve_time;
	}

	public void setApprove_time(Date approve_time) {
		this.approve_time = approve_time;
	}
	
	public Blogpost() {}

}