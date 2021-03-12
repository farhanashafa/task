package com.square.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Blogger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Column
    //private Long user_id;

    @Column
    private Integer status;//0=not approved; 1=approved & active; 2=inactive
    
    //@Column
    //private Long approved_by;
    
    @Column
    private Date approve_time;


    @OneToOne(fetch = FetchType.LAZY, targetEntity= Users.class)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user_id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Users.class)
    @JoinColumn(name = "approved_by", nullable = true)
    private Users approved_by;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Users getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Users user_id) {
		this.user_id = user_id;
	}

}