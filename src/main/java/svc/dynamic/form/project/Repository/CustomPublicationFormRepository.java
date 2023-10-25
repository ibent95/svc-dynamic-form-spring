package svc.dynamic.form.project.Repository;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *
 * @author ibent95
 */
public interface CustomPublicationFormRepository {

    List<Map<String, Object>> findAllMasterData(String tableName, String orderDirection, Integer maxResult) throws JsonMappingException, JsonProcessingException ;
    List<Map<String, Object>> findAllTaxonomyTerms(String taxonomyName, String orderDirection, Integer maxResult);

}