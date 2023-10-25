package svc.dynamic.form.project.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import svc.dynamic.form.project.Entity.PublicationStatus;

/**
 *
 * @author ibent95
 */
@Repository
public class CustomPublicationFormRepositoryImpl implements CustomPublicationFormRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String orderDirection   = "ASC";
    private Integer maxResult       = null;
    private String sqlQuery;
    private String jqlQuery;
    private Query query;
    private List<Map<String, Object>> results;

    @Override
    public List<Map<String, Object>> findAllMasterData(String tableName, String orderDirection, Integer maxResult)
    throws JsonMappingException, JsonProcessingException {
        this.orderDirection = (orderDirection != null) ? orderDirection : this.orderDirection ;
        this.maxResult = (maxResult != null) ? maxResult : this.maxResult ;
        this.sqlQuery = "SELECT ";
        String camelTableName = CaseUtils.toCamelCase(tableName, true, '_');

        // Proccess to get data for specifict table
        switch (tableName) {
 
            // For publication`s status
            case "publication_status":
                this.sqlQuery
                    += "t." + tableName + "_name, "
                     + "t." + tableName + "_name AS value, "
                     + "t.uuid "
                     + "FROM " + tableName + " AS t ";
                break;

            default:
                this.sqlQuery
                    += "t." + tableName + "_name, "
                     + "t." + tableName + "_name AS value, "
                     + "t.uuid "
                     + "FROM " + tableName + " AS t ";
               break;

        }

        // Add order query
        this.sqlQuery += "ORDER BY t." + tableName + "_name " + this.orderDirection + " ";

        // Set max results if maxresult is set
        if (maxResult != null) {
            this.sqlQuery += "LIMIT " + this.maxResult;
        }

        // Get query result
        this.results = this.jdbcTemplate.queryForList(sqlQuery);

        return this.results;
    }

    // NEED RECONFIGURE LATER!!!
    @Override
    public List<Map<String, Object>> findAllTaxonomyTerms(String taxonomyName, String orderDirection, Integer maxResult) {
        this.orderDirection = (orderDirection != null) ? orderDirection : this.orderDirection ;
        this.maxResult = (maxResult != null) ? maxResult : this.maxResult ;
        this.sqlQuery = "SELECT ";
        String camelTableName = CaseUtils.toCamelCase(taxonomyName, true, '_');

        // Proccess to get data for specifict table
        switch (taxonomyName) {
 
            // For publication`s status
            case "publication_status":
                this.sqlQuery
                    += "t." + taxonomyName + "_name, "
                     + "t." + taxonomyName + "_name AS value, "
                     + "t.uuid "
                     + "FROM " + taxonomyName + " AS t ";
                break;

            default:
                this.sqlQuery
                    += "t." + taxonomyName + "_name, "
                     + "t." + taxonomyName + "_name AS value, "
                     + "t.uuid "
                     + "FROM " + taxonomyName + " AS t ";
               break;

        }

        // Add order query
        this.sqlQuery += "ORDER BY t." + taxonomyName + "_name " + this.orderDirection + " ";

        // Set max results if maxresult is set
        if (maxResult != null) {
            this.sqlQuery += "LIMIT " + this.maxResult;
        }

        // Get query result
        this.results = this.jdbcTemplate.queryForList(sqlQuery);

        return this.results;
    }

}
