package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "temporary_file_upload", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "TemporaryFileUpload.findAll", query = "SELECT t FROM TemporaryFileUpload t"),
    @NamedQuery(name = "TemporaryFileUpload.findById", query = "SELECT t FROM TemporaryFileUpload t WHERE t.id = :id"),
    @NamedQuery(name = "TemporaryFileUpload.findByIdParrentService", query = "SELECT t FROM TemporaryFileUpload t WHERE t.idParrentService = :idParrentService"),
    @NamedQuery(name = "TemporaryFileUpload.findByUploadedDatetime", query = "SELECT t FROM TemporaryFileUpload t WHERE t.uploadedDatetime = :uploadedDatetime"),
    @NamedQuery(name = "TemporaryFileUpload.findByFlagActive", query = "SELECT t FROM TemporaryFileUpload t WHERE t.flagActive = :flagActive"),
    @NamedQuery(name = "TemporaryFileUpload.findByCreateUser", query = "SELECT t FROM TemporaryFileUpload t WHERE t.createUser = :createUser"),
    @NamedQuery(name = "TemporaryFileUpload.findByCreatedAt", query = "SELECT t FROM TemporaryFileUpload t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "TemporaryFileUpload.findByUpdateUser", query = "SELECT t FROM TemporaryFileUpload t WHERE t.updateUser = :updateUser"),
    @NamedQuery(name = "TemporaryFileUpload.findByUpdatedAt", query = "SELECT t FROM TemporaryFileUpload t WHERE t.updatedAt = :updatedAt"),
    @NamedQuery(name = "TemporaryFileUpload.findByUuid", query = "SELECT t FROM TemporaryFileUpload t WHERE t.uuid = :uuid")
})
public class TemporaryFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "id_parrent_service")
    private Long idParrentService;

    @Column(name = "uploaded_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime uploadedDatetime;

    @Column
    @Lob
    private String value;

    @Lob
    @Column(name = "other_value", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
    private String otherValue;

    @Column(name = "flag_active", nullable = false, columnDefinition = "TINYINT default 1")
    private Boolean flagActive = true;

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

    public TemporaryFileUpload() {
    }

    public TemporaryFileUpload(Long id) {
        this.id = id;
    }

    public TemporaryFileUpload(Long id, LocalDateTime uploadedDatetime, String value, String otherValue, Boolean flagActive, String createUser, LocalDateTime createdAt, String updateUser, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.uploadedDatetime = uploadedDatetime;
        this.value = value;
        this.otherValue = otherValue;
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

    public Long getIdParrentService() {
        return idParrentService;
    }

    public void setIdParrentService(Long idParrentService) {
        this.idParrentService = idParrentService;
    }

    public LocalDateTime getUploadedDatetime() {
        return uploadedDatetime;
    }

    public void setUploadedDatetime(LocalDateTime uploadedDatetime) {
        this.uploadedDatetime = uploadedDatetime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

    public Boolean getFlagActive() {
        return flagActive;
    }

    public void setFlagActive(Boolean flagActive) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemporaryFileUpload)) {
            return false;
        }
        TemporaryFileUpload other = (TemporaryFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "svc.dynamic.form.project.Entity.TemporaryFileUpload[ id=" + id + " ]";
    }
    
}
