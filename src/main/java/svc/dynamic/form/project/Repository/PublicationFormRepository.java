package svc.dynamic.form.project.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.Id;
import svc.dynamic.form.project.Entity.PublicationForm;

/**
 *
 * @author ibent95
 */
@EnableJpaRepositories
public interface PublicationFormRepository extends CrudRepository<PublicationForm, Id>, CustomPublicationFormRepository {

    List<Map<String, Object>> findAllMasterData(String tableName, String orderDirection, Integer maxResult);
    List<Map<String, Object>> findAllTaxonomyTerms(String taxonomyName, String orderDirection, Integer maxResult);

}
