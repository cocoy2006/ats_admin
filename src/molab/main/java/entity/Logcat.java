package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Logcat entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOGCAT", catalog = "MOLAB")
public class Logcat implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer runnerId;
	private String tag;
	private String level;
	private Integer type;
	private String content;

	// Constructors

	/** default constructor */
	public Logcat() {
	}
	
	public Logcat(String tag, String level, String content) {
		this.tag = tag;
		this.level = level;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "RUNNER_ID", nullable = false)
	public Integer getRunnerId() {
		return this.runnerId;
	}

	public void setRunnerId(Integer runnerId) {
		this.runnerId = runnerId;
	}

	@Column(name = "TAG")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Column(name = "LEVEL", length = 10)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "CONTENT", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}