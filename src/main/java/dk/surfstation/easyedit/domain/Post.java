package dk.surfstation.easyedit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	//	@CreationTimestamp
	private long created;

	//	@UpdateTimestamp
	private long updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCreated() {
		return created;
	}

	public long getUpdated() {
		return updated;
	}

	@PrePersist
	protected void onCreate() {
		updated = created = getEpochMilli();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = getEpochMilli();
	}

	private long getEpochMilli() {
		return Instant.now().toEpochMilli();
	}
}
