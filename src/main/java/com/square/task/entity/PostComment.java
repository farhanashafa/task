package com.square.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String comment;
    
    //@Column
    //private Long blogpost_id;

    @OneToOne(fetch = FetchType.LAZY, targetEntity= Blogpost.class)
    @JoinColumn(name = "blogpost_id", nullable = false)
    private Blogpost blogpost_id;
    
    //@Column
    //private Long commented_by;

    @OneToOne(fetch = FetchType.LAZY, targetEntity= Users.class)
    @JoinColumn(name = "commented_by", nullable = false)
    private Users commented_by;
    
    @Column
    private Date comment_time;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Blogpost getBlogpost_id() {
		return this.blogpost_id;
	}

	public void setBlogpost_id(Blogpost blogpost_id) {
		this.blogpost_id = blogpost_id;
	}

	public Users getCommented_by() {
		return this.commented_by;
	}

	public void setCommented_by(Users commented_by) {
		this.commented_by = commented_by;
	}

	public Date getComment_time() {
		return this.comment_time;
	}

	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}

}