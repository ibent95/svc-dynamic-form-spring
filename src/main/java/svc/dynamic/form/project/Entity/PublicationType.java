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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "publication_type", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "PublicationType.findAll", query = "SELECT p FROM PublicationType p"),
    @NamedQuery(name = "PublicationType.findById", query = "SELECT p FROM PublicationType p WHERE p.id = :id"),
    @NamedQuery(name = "PublicationType.findByPublicationTypeName", query = "SELECT p FROM PublicationType p WHERE p.publicationTypeName = :publicationTypeName"),
    @NamedQuery(name = "PublicationType.findByPublicationTypeCode", query = "SELECT p FROM PublicationType p WHERE p.publicationTypeCode = :publicationTypeCode"),
    @NamedQuery(name = "PublicationType.findByFlagActive", query = "SELECT p FROM PublicationType p WHERE p.flagActive = :flagActive"),
    @NamedQuery(name = "PublicationType.findByCreateUser", query = "SELECT p FROM PublicationType p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "PublicationType.findByCreatedAt", query = "SELECT p FROM PublicationType p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PublicationType.findByUpdateUser", query = "SELECT p FROM PublicationType p WHERE p.updateUser = :updateUser"),
    @NamedQuery(name = "PublicationType.findByUpdatedAt", query = "SELECT p FROM PublicationType p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PublicationType.findByUuid", query = "SELECT p FROM PublicationType p WHERE p.uuid = :uuid")
})
public class PublicationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "id_publication_general_type", insertable = false, updatable = false)
    private Long idPublicationGeneralType;

    @Column(name = "publication_type_name", nullable = false)
    private String publicationTypeName;

    @Column(name = "publication_type_code", length = 50, nullable = false)
    private String publicationTypeCode;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationType")
    private Collection<Publication> publicationCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationType")
    private Collection<PublicationFormVersion> publicationFormVersionCollection;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publication_general_type", referencedColumnName = "id")
    private PublicationGeneralType publicationGeneralType;

    public PublicationType() {
    }

    public PublicationType(Long id) {
        this.id = id;
    }

    public PublicationType(Long id, String publicationTypeName, String publicationTypeCode, boolean flagActive, LocalDateTime createdAt, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.publicationTypeName = publicationTypeName;
        this.publicationTypeCode = publicationTypeCode;
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

    public Long getIdPublicationGeneralType() {
        return idPublicationGeneralType;
    }

    public void setIdPublicationGeneralType(Long idPublicationGeneralType) {
        this.idPublicationGeneralType = idPublicationGeneralType;
    }

    public String getPublicationTypeName() {
        return publicationTypeName;
    }

    public void setPublicationTypeName(String publicationTypeName) {
        this.publicationTypeName = publicationTypeName;
    }

    public String getPublicationTypeCode() {
        return publicationTypeCode;
    }

    public void setPublicationTypeCode(String publicationTypeCode) {
        this.publicationTypeCode = publicationTypeCode;
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

    public Collection<Publication> getPublicationCollection() {
        return publicationCollection;
    }

    public void setPublicationCollection(Collection<Publication> publicationCollection) {
        this.publicationCollection = publicationCollection;
    }

    public Collection<PublicationFormVersion> getPublicationFormVersionCollection() {
        return publicationFormVersionCollection;
    }

    public void setPublicationFormVersionCollection(Collection<PublicationFormVersion> publicationFormVersionCollection) {
        this.publicationFormVersionCollection = publicationFormVersionCollection;
    }

    public PublicationGeneralType getPublicationGeneralType() {
        return publicationGeneralType;
    }

    public void setPublicationGeneralType(PublicationGeneralType publicationGeneralType) {
        this.publicationGeneralType = publicationGeneralType;
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
        if (!(object instanceof PublicationType)) {
            return false;
        }
        PublicationType other = (PublicationType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "svc.dynamic.form.project.Entity.PublicationType[ id=" + id + " ]";
    }
    
}
