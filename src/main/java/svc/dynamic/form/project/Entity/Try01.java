package svc.dynamic.form.project.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author ibent95
 */
@Entity
@Table(catalog = "app_dynamic_form", schema = "")
@NamedQueries({
	@NamedQuery(name = "Try01.findAll", query = "SELECT t FROM Try01 t"),
	@NamedQuery(name = "Try01.findById", query = "SELECT t FROM Try01 t WHERE t.id = :id"),
	@NamedQuery(name = "Try01.findByName", query = "SELECT t FROM Try01 t WHERE t.name = :name"),
	@NamedQuery(name = "Try01.findByUuid", query = "SELECT t FROM Try01 t WHERE t.uuid = :uuid")
})
public class Try01 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
	private Long id;

	@Column(nullable = false)
	private String name;

    @Column(length = 36, nullable = false, columnDefinition = "CHAR(36)")
	private String uuid;

	public Try01() {
	}

	public Try01(Long id) {
		this.id = id;
	}

	public Try01(Long id, String name, String uuid) {
		this.id = id;
		this.name = name;
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if (!(object instanceof Try01)) {
			return false;
		}
		Try01 other = (Try01) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "svc.dynamic.form.project.Entity.Try01[ id=" + id + " ]";
	}
	
}
