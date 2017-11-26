package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Action entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ICT_DEVICE", catalog = "MOLAB")
public class ICtDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private String server;
	private Integer port;
	private String sn;
	private String os;
	private String imei;
	private Integer uses;
	private Integer state;
	
	// Classes mapping
	private Model model;
	
	// Constructors
	
	/** default constructor */
	public ICtDevice() {
	}

	/** minimal constructor */
	public ICtDevice(String server, Integer port, String sn) {
		this.server = server;
		this.port = port;
		this.sn = sn;
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

	@Column(name = "SERVER", nullable = false)
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Column(name = "PORT", nullable = false)
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "SN", nullable = false)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "OS")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "IMEI")
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "USES", nullable = false)
	public Integer getUses() {
		return uses;
	}

	public void setUses(Integer uses) {
		this.uses = uses;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODEL_ID")
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}