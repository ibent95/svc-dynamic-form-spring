package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
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
@Table(name = "publication_form", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
    @NamedQuery(name = "PublicationForm.findAll", query = "SELECT p FROM PublicationForm p"),
    @NamedQuery(name = "PublicationForm.findById", query = "SELECT p FROM PublicationForm p WHERE p.id = :id"),
    @NamedQuery(name = "PublicationForm.findByIdFormParent", query = "SELECT p FROM PublicationForm p WHERE p.idFormParent = :idFormParent"),
    @NamedQuery(name = "PublicationForm.findByFieldLabel", query = "SELECT p FROM PublicationForm p WHERE p.fieldLabel = :fieldLabel"),
    @NamedQuery(name = "PublicationForm.findByFieldType", query = "SELECT p FROM PublicationForm p WHERE p.fieldType = :fieldType"),
    @NamedQuery(name = "PublicationForm.findByFieldName", query = "SELECT p FROM PublicationForm p WHERE p.fieldName = :fieldName"),
    @NamedQuery(name = "PublicationForm.findByFieldId", query = "SELECT p FROM PublicationForm p WHERE p.fieldId = :fieldId"),
    @NamedQuery(name = "PublicationForm.findByFieldClass", query = "SELECT p FROM PublicationForm p WHERE p.fieldClass = :fieldClass"),
    @NamedQuery(name = "PublicationForm.findByFieldPlaceholder", query = "SELECT p FROM PublicationForm p WHERE p.fieldPlaceholder = :fieldPlaceholder"),
    @NamedQuery(name = "PublicationForm.findByFieldOptions", query = "SELECT p FROM PublicationForm p WHERE p.fieldOptions = :fieldOptions"),
    @NamedQuery(name = "PublicationForm.findByDescription", query = "SELECT p FROM PublicationForm p WHERE p.description = :description"),
    @NamedQuery(name = "PublicationForm.findByOrderPosition", query = "SELECT p FROM PublicationForm p WHERE p.orderPosition = :orderPosition"),
    @NamedQuery(name = "PublicationForm.findByErrorMessage", query = "SELECT p FROM PublicationForm p WHERE p.errorMessage = :errorMessage"),
    @NamedQuery(name = "PublicationForm.findByFlagRequired", query = "SELECT p FROM PublicationForm p WHERE p.flagRequired = :flagRequired"),
    @NamedQuery(name = "PublicationForm.findByFlagFieldFormType", query = "SELECT p FROM PublicationForm p WHERE p.flagFieldFormType = :flagFieldFormType"),
    @NamedQuery(name = "PublicationForm.findByFlagFieldTitle", query = "SELECT p FROM PublicationForm p WHERE p.flagFieldTitle = :flagFieldTitle"),
    @NamedQuery(name = "PublicationForm.findByFlagFieldPublishDate", query = "SELECT p FROM PublicationForm p WHERE p.flagFieldPublishDate = :flagFieldPublishDate"),
    @NamedQuery(name = "PublicationForm.findByFlagActive", query = "SELECT p FROM PublicationForm p WHERE p.flagActive = :flagActive"),
    @NamedQuery(name = "PublicationForm.findByCreateUser", query = "SELECT p FROM PublicationForm p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "PublicationForm.findByCreatedAt", query = "SELECT p FROM PublicationForm p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PublicationForm.findByUpdateUser", query = "SELECT p FROM PublicationForm p WHERE p.updateUser = :updateUser"),
    @NamedQuery(name = "PublicationForm.findByUpdatedAt", query = "SELECT p FROM PublicationForm p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PublicationForm.findByUuid", query = "SELECT p FROM PublicationForm p WHERE p.uuid = :uuid")
})
public class PublicationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

	@Column(name = "id_form_version", nullable = false, insertable = false, updatable = false)
	private Long idFormVersion;

    @Column(name = "id_form_parent", insertable = true, updatable = true)
    private Long idFormParent;

    @Column(name = "field_label")
    private String fieldLabel;

    @Column(name = "field_type", nullable = false)
    private String fieldType;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_id")
    private String fieldId;

    @Column(name = "field_class")
    private String fieldClass;

    @Column(name = "field_placeholder")
    private String fieldPlaceholder;

    @Column(name = "field_options")
    private String fieldOptions;

    @Lob
    @Column(name = "field_configs", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
    private HashMap<String, Object> fieldConfigs;

    private String description;

    @Column(name = "order_position")
    private Integer orderPosition;

    @Lob
    @Column(name = "validation_configs", columnDefinition = "LONGTEXT")
    private String validationConfigs;

    @Column(name = "error_message")
    private String errorMessage;

    @Lob
    @Column(name = "dependency_child", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
    private HashMap<String, Object> dependencyChild;

    @Lob
    @Column(name = "dependency_parent", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
    private HashMap<String, Object> dependencyParent;

    @Column(name = "flag_required", nullable = false, columnDefinition = "TINYINT default 0")
    private boolean flagRequired;

    @Column(name = "flag_field_form_type", nullable = false, columnDefinition = "TINYINT default 0")
    private boolean flagFieldFormType;

    @Column(name = "flag_field_title", nullable = false, columnDefinition = "TINYINT default 0")
    private boolean flagFieldTitle;

    @Column(name = "flag_field_publish_date", nullable = false, columnDefinition = "TINYINT default 0")
    private boolean flagFieldPublishDate;

    @Column(name = "flag_active", nullable = false, columnDefinition = "TINYINT default 1")
    @JsonIgnore
    private boolean flagActive;

    @Column(name = "create_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
    private String createUser;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "update_user", length = 50, nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
    private String updateUser;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
    private String uuid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    private Collection<PublicationMeta> publicationMetaCollection;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_form_version", referencedColumnName = "id")
    @JsonIgnore
    @JsonBackReference
    private PublicationFormVersion formVersion;

    public PublicationForm() {
    }

    public PublicationForm(Long id) {
        this.id = id;
    }

    public PublicationForm(Long id, String fieldType, String fieldId, boolean flagRequired, boolean flagFieldFormType, boolean flagFieldTitle, boolean flagFieldPublishDate, boolean flagActive, LocalDateTime createdAt, LocalDateTime updatedAt, String uuid) {
        this.id = id;
        this.fieldType = fieldType;
        this.fieldId = fieldId;
        this.flagRequired = flagRequired;
        this.flagFieldFormType = flagFieldFormType;
        this.flagFieldTitle = flagFieldTitle;
        this.flagFieldPublishDate = flagFieldPublishDate;
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

    public Long getIdFormVersion() {
        return idFormVersion;
    }

    public void setIdFormVersion(Long idFormVersion) {
        this.idFormVersion = idFormVersion;
    }

    public Long getIdFormParent() {
        return idFormParent;
    }

    public void setIdFormParent(Long idFormParent) {
        this.idFormParent = idFormParent;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getFieldPlaceholder() {
        return fieldPlaceholder;
    }

    public void setFieldPlaceholder(String fieldPlaceholder) {
        this.fieldPlaceholder = fieldPlaceholder;
    }

    public String getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(String fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public HashMap getFieldConfigs() {
        return (HashMap) fieldConfigs;
    }

    public void setFieldConfigs(HashMap fieldConfigs) {
        this.fieldConfigs = fieldConfigs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getValidationConfigs() {
        return validationConfigs;
    }

    public void setValidationConfigs(String validationConfigs) {
        this.validationConfigs = validationConfigs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HashMap getDependencyChild() {
        return dependencyChild;
    }

    public void setDependencyChild(HashMap dependencyChild) {
        this.dependencyChild = dependencyChild;
    }

    public HashMap getDependencyParent() {
        return dependencyParent;
    }

    public void setDependencyParent(HashMap dependencyParent) {
        this.dependencyParent = dependencyParent;
    }

    public boolean isFlagRequired() {
        return flagRequired;
    }

    public void setFlagRequired(boolean flagRequired) {
        this.flagRequired = flagRequired;
    }

    public boolean isFlagFieldFormType() {
        return flagFieldFormType;
    }

    public void setFlagFieldFormType(boolean flagFieldFormType) {
        this.flagFieldFormType = flagFieldFormType;
    }

    public boolean isFlagFieldTitle() {
        return flagFieldTitle;
    }

    public void setFlagFieldTitle(boolean flagFieldTitle) {
        this.flagFieldTitle = flagFieldTitle;
    }

    public boolean isFlagFieldPublishDate() {
        return flagFieldPublishDate;
    }

    public void setFlagFieldPublishDate(boolean flagFieldPublishDate) {
        this.flagFieldPublishDate = flagFieldPublishDate;
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

    public PublicationFormVersion getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(PublicationFormVersion formVersion) {
        this.formVersion = formVersion;
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
        if (!(object instanceof PublicationForm)) {
            return false;
        }
        PublicationForm other = (PublicationForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    // @Override
    // public String toString() {
    //     return "svc.dynamic.form.project.Entity.PublicationForm[ id=" + id + ", uuid=" + uuid + " ]";
    // }
    
}
