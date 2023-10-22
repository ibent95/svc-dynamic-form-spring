package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.Publication;

/**
 *
 * @author ibent95
 */
public interface PublicationRepository extends CrudRepository<Publication, Id> {

    Long countByFlagActive(Boolean flagActive);
	
    @Query(value = "SELECT p.* FROM publication p WHERE p.flag_active = ?1 ORDER BY p.id ASC LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<Publication> findAllWithFlagActiveLimitOffset(Boolean flagActive, Integer limit, Integer offset);

    Publication findByUuid(String uuid);

}
