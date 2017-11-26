package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CrDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CR_DEVICE", catalog = "MOLAB")
public class CrDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private String server;
	private Integer port;
	private String sn;
	private String os;
	private Integer width;
	private Integer height;
	private Integer state;

	// Constructors

	/** default constructor */
	public CrDevice() {
	}
	
	/** minimal constructor */
	public CrDevice(String server, Integer port, String sn) {
		this.server = server;
		this.port = port;
		this.sn = sn;
	}

	/** minimal constructor */
	public CrDevice(String server, Integer port, String sn, Integer width,
			Integer height, Integer state) {
		this.server = server;
		this.port = port;
		this.sn = sn;
		this.width = width;
		this.height = height;
		this.state = state;
	}

	/** full constructor */
	public CrDevice(String server, Integer port, String sn, String os,
			Integer width, Integer height, Integer state) {
		this.server = server;
		this.port = port;
		this.sn = sn;
		this.os = os;
		this.width = width;
		this.height = height;
		this.state = state;
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

	@Column(name = "SERVER", nullable = false, length = 50)
	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Column(name = "PORT", nullable = false)
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "SN", nullable = false, length = 100)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "OS", length = 20)
	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "WIDTH", nullable = false)
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "HEIGHT", nullable = false)
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}