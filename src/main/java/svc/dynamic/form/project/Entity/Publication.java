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
@Table(name = "publication", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "Publication.findAll", query = "SELECT p FROM Publication p"),
    @NamedQuery(name = "Publication.findById", query = "SELECT p FROM Publication p WHERE p.id = :id"),
    @NamedQuery(name = "Publication.findByTitle", query = "SELECT p FROM Publication p WHERE p.title = :title"),
    @NamedQuery(name = "Publication.findByPublicationDate", query = "SELECT p FROM Publication p WHERE p.publicationDate = :publicationDate"),
    @NamedQuery(name = "Publication.findByFlagActive", query = "SELECT p FROM Publication p WHERE p.flagActive = :flagActive"),
    @NamedQuery(name = "Publication.findByCreateUser", query = "SELECT p FROM Publication p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "Publication.findByCreatedAt", query = "SELECT p FROM Publication p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "Publication.findByUpdateUser", query = "SELECT p FROM Publication p WHERE p.updateUser = :updateUser"),
    @NamedQuery(name = "Publication.findByUpdatedAt", query = "SELECT p FROM Publication p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "Publication.findByUuid", query = "SELECT p FROM Publication p WHERE p.uuid = :uuid")
})
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "id_publication_general_type", nullable = false, insertable = false, updatable = false)
    private Long idPublicationGeneralType;

    @Column(name = "id_publication_type", nullable = false, insertable = false, updatable = false)
    private Long idPublicationType;

    @Column(name = "id_publication_form_version", nullable = false, insertable = false, updatable = false)
    private Long idPublicationFormVersion;

    @Column(name = "id_publication_status", nullable = false, insertable = false, updatable = false)
    private Long idPublicationStatus;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    private Collection<PublicationMeta> publicationMetaCollection;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publication_status", referencedColumnName = "id")
    private PublicationStatus publicationStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publication_general_type", referencedColumnName = "id")
    private PublicationGeneralType publicationGeneralType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publication_type", referencedColumnName = "id")
    private PublicationType publicationType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publication_form_version", referencedColumnName = "id")
    private PublicationFormVersion publicationFormVersion;

    public Publication() {
    }

    public Publication(Long id) {
        this.id = id;
    }

    public Publication(Long id, boolean flagActive, String createUser, LocalDateTime createdAt, String updateUser, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.flagActive = flagActive;
        this.createUser = createUser;
        this.createdAt = createdAt;
        this.updateUser = updateUser;
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

    public Long getIdPublicationType() {
        return idPublicationType;
    }

    public void setIdPublicationType(Long idPublicationType) {
        this.idPublicationType = idPublicationType;
    }

    public Long getIdPublicationFormVersion() {
        return idPublicationFormVersion;
    }

    public void setIdPublicationFormVersion(Long idPublicationFormVersion) {
        this.idPublicationFormVersion = idPublicationFormVersion;
    }

    public Long getIdPublicationStatus() {
        return idPublicationStatus;
    }

    public void setIdPublicationStatus(Long idPublicationStatus) {
        this.idPublicationStatus = idPublicationStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
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

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public PublicationGeneralType getPublicationGeneralType() {
        return publicationGeneralType;
    }

    public void setPublicationGeneralType(PublicationGeneralType publicationGeneralType) {
        this.publicationGeneralType = publicationGeneralType;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public PublicationFormVersion getPublicationFormVersion() {
        return publicationFormVersion;
    }

    public void setPublicationFormVersion(PublicationFormVersion publicationFormVersion) {
        this.publicationFormVersion = publicationFormVersion;
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
        if (!(object instanceof Publication)) {
            return false;
        }
        Publication other = (Publication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "svc.dynamic.form.project.Entity.Publication[ id=" + id + " ]";
    }
    
}
