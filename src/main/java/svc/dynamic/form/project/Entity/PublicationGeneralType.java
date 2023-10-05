package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

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
@Table(name = "publication_general_type", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "PublicationGeneralType.findAll", query = "SELECT p FROM PublicationGeneralType p"),
    @NamedQuery(name = "PublicationGeneralType.findById", query = "SELECT p FROM PublicationGeneralType p WHERE p.id = :id"),
    @NamedQuery(name = "PublicationGeneralType.findByPublicationGeneralTypeName", query = "SELECT p FROM PublicationGeneralType p WHERE p.publicationGeneralTypeName = :publicationGeneralTypeName"),
    @NamedQuery(name = "PublicationGeneralType.findByPublicationGeneralTypeCode", query = "SELECT p FROM PublicationGeneralType p WHERE p.publicationGeneralTypeCode = :publicationGeneralTypeCode"),
    @NamedQuery(name = "PublicationGeneralType.findByFlagActive", query = "SELECT p FROM PublicationGeneralType p WHERE p.flagActive = :flagActive"),
    @NamedQuery(name = "PublicationGeneralType.findByCreateUser", query = "SELECT p FROM PublicationGeneralType p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "PublicationGeneralType.findByCreatedAt", query = "SELECT p FROM PublicationGeneralType p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PublicationGeneralType.findByUpdateUser", query = "SELECT p FROM PublicationGeneralType p WHERE p.updateUser = :updateUser"),
    @NamedQuery(name = "PublicationGeneralType.findByUpdatedAt", query = "SELECT p FROM PublicationGeneralType p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PublicationGeneralType.findByUuid", query = "SELECT p FROM PublicationGeneralType p WHERE p.uuid = :uuid")
})
public class PublicationGeneralType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "publication_general_type_name", nullable = false)
    private String publicationGeneralTypeName;

    @Column(name = "publication_general_type_code", nullable = false)
    private String publicationGeneralTypeCode;

    @Column(name = "flag_active", nullable = false, columnDefinition = "TINYINT default 1")
    private boolean flagActive;

    @Column(name = "create_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    private String createUser;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "update_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    private String updateUser;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
    private String uuid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationGeneralType")
    private Collection<Publication> publicationCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationGeneralType")
    private Collection<PublicationType> publicationTypeCollection;

    public PublicationGeneralType() {
    }

    public PublicationGeneralType(Long id) {
        this.id = id;
    }

    public PublicationGeneralType(Long id, String publicationGeneralTypeName, String publicationGeneralTypeCode, boolean flagActive, LocalDateTime createdAt, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.publicationGeneralTypeName = publicationGeneralTypeName;
        this.publicationGeneralTypeCode = publicationGeneralTypeCode;
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

    public String getPublicationGeneralTypeName() {
        return publicationGeneralTypeName;
    }

    public void setPublicationGeneralTypeName(String publicationGeneralTypeName) {
        this.publicationGeneralTypeName = publicationGeneralTypeName;
    }

    public String getPublicationGeneralTypeCode() {
        return publicationGeneralTypeCode;
    }

    public void setPublicationGeneralTypeCode(String publicationGeneralTypeCode) {
        this.publicationGeneralTypeCode = publicationGeneralTypeCode;
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

    public Collection<PublicationType> getPublicationTypeCollection() {
        return publicationTypeCollection;
    }

    public void setPublicationTypeCollection(Collection<PublicationType> publicationTypeCollection) {
        this.publicationTypeCollection = publicationTypeCollection;
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
        if (!(object instanceof PublicationGeneralType)) {
            return false;
        }
        PublicationGeneralType other = (PublicationGeneralType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "svc.dynamic.form.project.Entity.PublicationGeneralType[ id=" + id + " ]";
    }
    
}
