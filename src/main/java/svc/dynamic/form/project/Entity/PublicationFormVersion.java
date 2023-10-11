package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

/**
 *
 * @author ibent95
 */
@Entity
@Table(name = "publication_form_version", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
	@NamedQuery(name = "PublicationFormVersion.findAll", query = "SELECT p FROM PublicationFormVersion p"),
	@NamedQuery(name = "PublicationFormVersion.findById", query = "SELECT p FROM PublicationFormVersion p WHERE p.id = :id"),
	@NamedQuery(name = "PublicationFormVersion.findByPublicationFormVersionName", query = "SELECT p FROM PublicationFormVersion p WHERE p.publicationFormVersionName = :publicationFormVersionName"),
	@NamedQuery(name = "PublicationFormVersion.findByPublicationFormVersionCode", query = "SELECT p FROM PublicationFormVersion p WHERE p.publicationFormVersionCode = :publicationFormVersionCode"),
	@NamedQuery(name = "PublicationFormVersion.findByFlagActive", query = "SELECT p FROM PublicationFormVersion p WHERE p.flagActive = :flagActive"),
	@NamedQuery(name = "PublicationFormVersion.findByCreateUser", query = "SELECT p FROM PublicationFormVersion p WHERE p.createUser = :createUser"),
	@NamedQuery(name = "PublicationFormVersion.findByCreatedAt", query = "SELECT p FROM PublicationFormVersion p WHERE p.createdAt = :createdAt"),
	@NamedQuery(name = "PublicationFormVersion.findByUpdateUser", query = "SELECT p FROM PublicationFormVersion p WHERE p.updateUser = :updateUser"),
	@NamedQuery(name = "PublicationFormVersion.findByUpdatedAt", query = "SELECT p FROM PublicationFormVersion p WHERE p.updatedAt = :updatedAt"),
	@NamedQuery(name = "PublicationFormVersion.findByUuid", query = "SELECT p FROM PublicationFormVersion p WHERE p.uuid = :uuid")
})
public class PublicationFormVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
	private Long id;

    @Column(name = "id_publication_type", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Long idPublicationType;

    @Column(name = "publication_form_version_name", nullable = false)
	private String publicationFormVersionName;

    @Column(name = "publication_form_version_code", nullable = false)
	private String publicationFormVersionCode;

	@Lob
    @Column(name = "grid_system", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
	private Map<String, Object> gridSystem;

    @Column(name = "flag_active", nullable = false, columnDefinition = "tiny default 1")
    @JsonIgnore
	private boolean flagActive;

	@Column(name = "create_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
	private String createUser = "systems";

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private LocalDateTime createdAt;

	@Column(name = "update_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
	private String updateUser = "systems";

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private LocalDateTime updatedAt;

	@Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
	private String uuid;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "formVersion", fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<PublicationMeta> publicationMetaCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationFormVersion", fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<Publication> publicationCollection;

    @ManyToOne(optional = false)
	@JoinColumn(name = "id_publication_type", referencedColumnName = "id")
	@JsonIgnore
	private PublicationType publicationType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "formVersion", fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<PublicationForm> publicationFormCollection;

	public PublicationFormVersion() {
	}

	public PublicationFormVersion(Long id) {
		this.id = id;
	}

	public PublicationFormVersion(Long id, String publicationFormVersionName, String publicationFormVersionCode, boolean flagActive, LocalDateTime createdAt, LocalDateTime updatedAt, String uuid) {
		this.id = id;
		this.publicationFormVersionName = publicationFormVersionName;
		this.publicationFormVersionCode = publicationFormVersionCode;
		this.flagActive = flagActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.uuid = uuid;
	}

    @PrePersist
    public void onPrePersist() {
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPublicationType() {
		return idPublicationType;
	}

	public void setIdPublicationType(Long idPublicationType) {
		this.idPublicationType = idPublicationType;
	}

	public String getPublicationFormVersionName() {
		return publicationFormVersionName;
	}

	public void setPublicationFormVersionName(String publicationFormVersionName) {
		this.publicationFormVersionName = publicationFormVersionName;
	}

	public String getPublicationFormVersionCode() {
		return publicationFormVersionCode;
	}

	public void setPublicationFormVersionCode(String publicationFormVersionCode) {
		this.publicationFormVersionCode = publicationFormVersionCode;
	}

	public Map getGridSystem() {
		return gridSystem;
	}

	public void setGridSystem(HashMap gridSystem) {
		this.gridSystem = gridSystem;
	}

	public boolean isFlagActive() {
		return flagActive;
	}

	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Collection<PublicationMeta> getPublicationMetaCollection() {
		return publicationMetaCollection;
	}

	public void setPublicationMetaCollection(Collection<PublicationMeta> publicationMetaCollection) {
		this.publicationMetaCollection = publicationMetaCollection;
	}

	public Collection<Publication> getPublicationCollection() {
		return publicationCollection;
	}

	public void setPublicationCollection(Collection<Publication> publicationCollection) {
		this.publicationCollection = publicationCollection;
	}

	public PublicationType getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
	}

	public Collection<PublicationForm> getPublicationFormCollection() {
		return publicationFormCollection;
	}

	public void setPublicationFormCollection(Collection<PublicationForm> publicationFormCollection) {
		this.publicationFormCollection = publicationFormCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof PublicationFormVersion)) {
			return false;
		}
		PublicationFormVersion other = (PublicationFormVersion) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "svc.dynamic.form.project.Entity.PublicationFormVersion[ id=" + id + " ]";
	}
	
}
