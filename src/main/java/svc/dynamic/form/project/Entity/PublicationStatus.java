package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author ibent95
 */
@Entity
@Table(name = "publication_status", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "PublicationStatus.findAll", query = "SELECT p FROM PublicationStatus p"),
    @NamedQuery(name = "PublicationStatus.findById", query = "SELECT p FROM PublicationStatus p WHERE p.id = :id"),
    @NamedQuery(name = "PublicationStatus.findByPublicationStatusName", query = "SELECT p FROM PublicationStatus p WHERE p.publicationStatusName = :publicationStatusName"),
    @NamedQuery(name = "PublicationStatus.findByPublicationStatusCode", query = "SELECT p FROM PublicationStatus p WHERE p.publicationStatusCode = :publicationStatusCode"),
    @NamedQuery(name = "PublicationStatus.findByFlagActive", query = "SELECT p FROM PublicationStatus p WHERE p.flagActive = :flagActive"),
    @NamedQuery(name = "PublicationStatus.findByCreateUser", query = "SELECT p FROM PublicationStatus p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "PublicationStatus.findByCreatedAt", query = "SELECT p FROM PublicationStatus p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PublicationStatus.findByUpdateUser", query = "SELECT p FROM PublicationStatus p WHERE p.updateUser = :updateUser"),
    @NamedQuery(name = "PublicationStatus.findByUpdatedAt", query = "SELECT p FROM PublicationStatus p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PublicationStatus.findByUuid", query = "SELECT p FROM PublicationStatus p WHERE p.uuid = :uuid")
})
public class PublicationStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "publication_status_name", nullable = false)
    private String publicationStatusName;

    @Column(name = "publication_status_code", nullable = false)
    private String publicationStatusCode;

    @Column(name = "flag_active", nullable = false, columnDefinition = "TINYINT default 1")
    private boolean flagActive = true;

    @Column(name = "create_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    private String createUser = "systems";

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "update_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    private String updateUser = "systems";

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
    private String uuid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationStatus")
    private Collection<Publication> publicationCollection;

    public PublicationStatus() {
    }

    public PublicationStatus(Long id) {
        this.id = id;
    }

    public PublicationStatus(Long id, String publicationStatusName, String publicationStatusCode, boolean flagActive, LocalDateTime createdAt, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.publicationStatusName = publicationStatusName;
        this.publicationStatusCode = publicationStatusCode;
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

    public String getPublicationStatusName() {
        return publicationStatusName;
    }

    public void setPublicationStatusName(String publicationStatusName) {
        this.publicationStatusName = publicationStatusName;
    }

    public String getPublicationStatusCode() {
        return publicationStatusCode;
    }

    public void setPublicationStatusCode(String publicationStatusCode) {
        this.publicationStatusCode = publicationStatusCode;
    }

    public boolean getFlagActive() {
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

    public Collection<Publication> getPublicationCollection() {
        return publicationCollection;
    }

    public void setPublicationCollection(Collection<Publication> publicationCollection) {
        this.publicationCollection = publicationCollection;
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
        if (!(object instanceof PublicationStatus)) {
            return false;
        }
        PublicationStatus other = (PublicationStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "svc.dynamic.form.project.Entity.PublicationStatus[ id=" + id + " ]";
    }
    
}
