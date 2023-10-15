package svc.dynamic.form.project.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "publication_meta", catalog = "app_dynamic_form", schema = "")
@NamedQueries({
	@NamedQuery(name = "PublicationMeta.findAll", query = "SELECT p FROM PublicationMeta p"),
	@NamedQuery(name = "PublicationMeta.findById", query = "SELECT p FROM PublicationMeta p WHERE p.id = :id"),
	@NamedQuery(name = "PublicationMeta.findByIdFormParent", query = "SELECT p FROM PublicationMeta p WHERE p.idFormParent = :idFormParent"),
	@NamedQuery(name = "PublicationMeta.findByFieldLabel", query = "SELECT p FROM PublicationMeta p WHERE p.fieldLabel = :fieldLabel"),
	@NamedQuery(name = "PublicationMeta.findByFieldType", query = "SELECT p FROM PublicationMeta p WHERE p.fieldType = :fieldType"),
	@NamedQuery(name = "PublicationMeta.findByFieldName", query = "SELECT p FROM PublicationMeta p WHERE p.fieldName = :fieldName"),
	@NamedQuery(name = "PublicationMeta.findByFieldId", query = "SELECT p FROM PublicationMeta p WHERE p.fieldId = :fieldId"),
	@NamedQuery(name = "PublicationMeta.findByFieldClass", query = "SELECT p FROM PublicationMeta p WHERE p.fieldClass = :fieldClass"),
	@NamedQuery(name = "PublicationMeta.findByFieldPlaceholder", query = "SELECT p FROM PublicationMeta p WHERE p.fieldPlaceholder = :fieldPlaceholder"),
	@NamedQuery(name = "PublicationMeta.findByFieldOptions", query = "SELECT p FROM PublicationMeta p WHERE p.fieldOptions = :fieldOptions"),
	@NamedQuery(name = "PublicationMeta.findByDescription", query = "SELECT p FROM PublicationMeta p WHERE p.description = :description"),
	@NamedQuery(name = "PublicationMeta.findByOrderPosition", query = "SELECT p FROM PublicationMeta p WHERE p.orderPosition = :orderPosition"),
	@NamedQuery(name = "PublicationMeta.findByErrorMessage", query = "SELECT p FROM PublicationMeta p WHERE p.errorMessage = :errorMessage"),
	@NamedQuery(name = "PublicationMeta.findByFlagRequired", query = "SELECT p FROM PublicationMeta p WHERE p.flagRequired = :flagRequired"),
	@NamedQuery(name = "PublicationMeta.findByFlagFieldFormType", query = "SELECT p FROM PublicationMeta p WHERE p.flagFieldFormType = :flagFieldFormType"),
	@NamedQuery(name = "PublicationMeta.findByFlagFieldTitle", query = "SELECT p FROM PublicationMeta p WHERE p.flagFieldTitle = :flagFieldTitle"),
	@NamedQuery(name = "PublicationMeta.findByFlagFieldPublishDate", query = "SELECT p FROM PublicationMeta p WHERE p.flagFieldPublishDate = :flagFieldPublishDate"),
	@NamedQuery(name = "PublicationMeta.findByFlagActive", query = "SELECT p FROM PublicationMeta p WHERE p.flagActive = :flagActive"),
	@NamedQuery(name = "PublicationMeta.findByCreateUser", query = "SELECT p FROM PublicationMeta p WHERE p.createUser = :createUser"),
	@NamedQuery(name = "PublicationMeta.findByCreatedAt", query = "SELECT p FROM PublicationMeta p WHERE p.createdAt = :createdAt"),
	@NamedQuery(name = "PublicationMeta.findByUpdateUser", query = "SELECT p FROM PublicationMeta p WHERE p.updateUser = :updateUser"),
	@NamedQuery(name = "PublicationMeta.findByUpdatedAt", query = "SELECT p FROM PublicationMeta p WHERE p.updatedAt = :updatedAt"),
	@NamedQuery(name = "PublicationMeta.findByUuid", query = "SELECT p FROM PublicationMeta p WHERE p.uuid = :uuid")
})
public class PublicationMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	private Long id;

	@Column(name = "id_form", nullable = false, insertable = false, updatable = false)
	private Long idForm;

	@Column(name = "id_publication", nullable = false, insertable = false, updatable = false)
	private Long idPublication;

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
	private Map<String, Object> fieldConfigs;

	private String description;

	@Column(name = "order_position")
	private Integer orderPosition;

	@Lob
    @Column(name = "validation_configs")
	private String validationConfigs;

	@Column(name = "error_message")
	private String errorMessage;

	@Lob
	@Column(name = "dependency_child", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
	private Map<String, Object> dependencyChild;

	@Lob
	@Column(name = "dependency_parent", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
	private Map<String, Object> dependencyParent;

	@Column(name = "flag_required", nullable = false, columnDefinition = "TINYINT default 1")
	private boolean flagRequired;

	@Column(name = "flag_field_form_type", nullable = false, columnDefinition = "TINYINT default 1")
	private boolean flagFieldFormType;

	@Column(name = "flag_field_title", nullable = false, columnDefinition = "TINYINT default 1")
	private boolean flagFieldTitle;

	@Column(name = "flag_field_publish_date", nullable = false, columnDefinition = "TINYINT default 0")
	private boolean flagFieldPublishDate;

	@Lob
	private String value;

	@Lob
	@Column(name = "other_value", columnDefinition = "LONGTEXT")
	@Type(JsonStringType.class)
	private HashMap<String, Object> otherValue;

	@Column(name = "flag_active", nullable = false, columnDefinition = "TINYINT default 1")
    @JsonIgnore
	private boolean flagActive;

	@Column(name = "create_user", nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
	private String createUser;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private LocalDateTime createdAt;

	@Column(name = "update_user", nullable = false, columnDefinition = "VARCHAR(50) default 'systems'")
    @JsonIgnore
	private String updateUser;

	@Column(name = "updated_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private LocalDateTime updatedAt;

	@Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
	private String uuid;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_form_version", referencedColumnName = "id")
	private PublicationFormVersion formVersion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_publication", referencedColumnName = "id")
	private Publication publication;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_form", referencedColumnName = "id")
	private PublicationForm form;

	public PublicationMeta() {
	}

	public PublicationMeta(Long id) {
		this.id = id;
	}

	public PublicationMeta(Long id, Long idForm, Long idPublication, Long idFormVersion, Long idFormParent,
			String fieldLabel, String fieldType, String fieldName, String fieldId, String fieldClass,
			String fieldPlaceholder, String fieldOptions, HashMap fieldConfigs, String description,
			Integer orderPosition, String validationConfigs, String errorMessage, HashMap dependencyChild,
			HashMap dependencyParent, boolean flagRequired, boolean flagFieldFormType, boolean flagFieldTitle,
			boolean flagFieldPublishDate, String value, HashMap otherValue, boolean flagActive, String createUser,
			LocalDateTime createdAt, String updateUser, LocalDateTime updatedAt, String uuid) {
		this.id = id;
		this.idForm = idForm;
		this.idPublication = idPublication;
		this.idFormVersion = idFormVersion;
		this.idFormParent = idFormParent;
		this.fieldLabel = fieldLabel;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
		this.fieldClass = fieldClass;
		this.fieldPlaceholder = fieldPlaceholder;
		this.fieldOptions = fieldOptions;
		this.fieldConfigs = fieldConfigs;
		this.description = description;
		this.orderPosition = orderPosition;
		this.validationConfigs = validationConfigs;
		this.errorMessage = errorMessage;
		this.dependencyChild = dependencyChild;
		this.dependencyParent = dependencyParent;
		this.flagRequired = flagRequired;
		this.flagFieldFormType = flagFieldFormType;
		this.flagFieldTitle = flagFieldTitle;
		this.flagFieldPublishDate = flagFieldPublishDate;
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
        this.setCreateUser("system");
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdateUser("system");
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdateUser("system");
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdForm() {
		return idForm;
	}

	public void setIdForm(Long idForm) {
		this.idForm = idForm;
	}

	public Long getIdPublication() {
		return idPublication;
	}

	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
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

	public Map getDependencyChild() {
		return dependencyChild;
	}

	public void setDependencyChild(HashMap dependencyChild) {
		this.dependencyChild = dependencyChild;
	}

	public Map getDependencyParent() {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public HashMap getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(HashMap otherValue) {
		this.otherValue = otherValue;
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

	public PublicationFormVersion getFormVersion() {
		return formVersion;
	}

	public void setFormVersion(PublicationFormVersion formVersion) {
		this.formVersion = formVersion;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public PublicationForm getForm() {
		return form;
	}

	public void setForm(PublicationForm form) {
		this.form = form;
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
		if (!(object instanceof PublicationMeta)) {
			return false;
		}
		PublicationMeta other = (PublicationMeta) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "svc.dynamic.form.project.Entity.PublicationMeta[ id=" + id + " ]";
	}
	
}
