package svc.dynamic.form.project.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.hibernate.collection.spi.PersistentCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import svc.dynamic.form.project.Entity.Publication;
import svc.dynamic.form.project.Entity.PublicationForm;
import svc.dynamic.form.project.Entity.PublicationFormVersion;
import svc.dynamic.form.project.Entity.PublicationGeneralType;
import svc.dynamic.form.project.Entity.PublicationMeta;
import svc.dynamic.form.project.Entity.PublicationStatus;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Repository.PublicationStatusRepository;
import svc.dynamic.form.project.Repository.TemporaryFileUploadRepository;

@Service
@Transactional
public class PublicationService {

    private Object results;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonSvc;
    @Autowired
    private PublicationStatusRepository publicationStatusRepo;
    @Autowired
    private TemporaryFileUploadRepository temporaryFileUploadRepo;

    public PublicationService() { /** code */ }

    public PublicationMeta getPublicationMetaDataBy(
        Collection<PublicationMeta> sourceData1,
        String uuid
    ) {
        return sourceData1.stream().filter(item -> item.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    private JsonNode getRequestMetaDataByUuid(
        JsonNode sourceData2,
        String uuid
    ) {
        return sourceData2.get("uuid");
    }

    private JsonNode getRequestMetaDataByFieldName(
        JsonNode sourceData3,
        String fieldName
    ) {
        return sourceData3.get(fieldName) ;
    }

    private Collection<PublicationForm> getFormConfigsByParentId(
        Collection<PublicationForm> sourceData4,
        Long parentId
    ) {
        return sourceData4.stream().filter(item -> item.getIdFormParent() == parentId).toList();
    }

    /**
	 * This function response to get Main and Meta Data of input from Dynamic Form
     * @throws JsonProcessingException
     * @throws JsonMappingException
	 */
    public Publication setDataByDynamicForm(
        @RequestParam Map<String, Object> requestParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws JsonMappingException, JsonProcessingException {
        Publication mainData    = this.setMainData(requestParam, request, formVersion, publication);
        Publication results     = this.setMetaData(requestParam, request, formVersion, mainData);

        return results;
    }

	/**
	 * This function response to organize Main Data of input from Dynamic Form
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	private Publication setMainData(
        @RequestParam Map<String, Object> requestParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws JsonMappingException, JsonProcessingException {

        /**
         *  Initial value
         *  results is an instance of Publication entity or publication
         *  If publication is not send than UPDATE mechanism is running, default is CREATE.
         */
        Publication results 				    = (publication != null) ? publication : new Publication();
        Map<String, Object> requestData 		= requestParam;

        // Get Form Configs of Main Data (if exists)
        Collection<PublicationForm> formConfigs = formVersion.getPublicationFormCollection();
        PublicationForm formTypeFieldConfig 	= this.setFieldConfigFromFormConfigs(formConfigs, "flag_field_form_type", true);
        PublicationForm titleFieldConfig 		= this.setFieldConfigFromFormConfigs(formConfigs, "flag_field_title", true);
        PublicationForm publishDateFieldConfig  = this.setFieldConfigFromFormConfigs(formConfigs, "flag_field_publish_date", true);

        // Organize the Main Data
        PublicationGeneralType generalFormType  = formVersion.getPublicationType().getPublicationGeneralType();
        PublicationType formType    			= formVersion.getPublicationType();
        PublicationStatus formStatus 			= this.publicationStatusRepo.findByPublicationStatusCode(requestParam.get("publication_status_code").toString());
        String title 					        = null;
        LocalDateTime publishDate 			    = null;

        Integer metaDataIndex                   = 0;
        Iterator<JsonNode> metaDataRequestData  = this.objectMapper.readValue((String) requestData.get("meta_data"), JsonNode.class).elements();

        while (metaDataRequestData.hasNext()) {
            JsonNode metaData = metaDataRequestData.next();

            System.out.println(metaData);

            String titleCase = (titleFieldConfig == null)
                ? "title"
                : titleFieldConfig.getFieldName();

            String publishDateCase = (publishDateFieldConfig == null)
                ? "published_date"
                : publishDateFieldConfig.getFieldName();

            if (metaData.get("field_name").toString().equals(titleCase)) {
                title          = metaData.get("value").toString();
            } else if (metaData.get("field_name").toString().equals(publishDateCase)) {
                publishDate    = LocalDateTime.parse(metaData.get("value").toString());
            } else { /** code */ }

            metaDataIndex++;
        }

        // Wrapp the Main Data
        if (request.getMethod().equals("POST")) {
            results.setId(this.commonSvc.createUUIDShort());
            results.setUuid(this.commonSvc.createUUID());
        }
        results.setPublicationGeneralType(generalFormType);
        results.setPublicationType(formType);
        results.setPublicationFormVersion(formVersion);
        results.setPublicationStatus(formStatus);
        results.setTitle(title);
        results.setPublicationDate(publishDate);
        results.setFlagActive(true);

		return results;
	}

	/**
	 * This function response to organize Meta Data of input from Dynamic Form
	 */
	private Publication setMetaData(
        @RequestParam Map<String, Object> requestParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws JsonMappingException, JsonProcessingException {
        // Initial value
		Publication results        = publication;

        /**
         * Remove the old Meta Data if there is Meta Data.
         * There are two options:
         * 1. Set active flag to false (0)
         * 2. Remove existing data from PersistenceCollection 
         *    array (results.removePublicationMetas(metaDataConfig);) 
         */
        Collection<PublicationMeta> metaDataConfigs    = publication.getPublicationMeta();
        if (metaDataConfigs != null && metaDataConfigs.size() > 0) {
            Integer metaDataConfigIndex = 0;
            for (PublicationMeta metaDataConfig : metaDataConfigs) {
                metaDataConfig.setFlagActive(false);
            }
        }

        // this.logger.info("Publication ID - " . publication.getId());

        // Organize the new Meta Data (create or update)
        if (request.getMethod().equals("POST")) {
            results    = this.updateMetaData(requestParam, request, formVersion, publication, null);
        }

        if (request.getMethod().equals("PUT")) {
            results    = this.updateMetaData(requestParam, request, formVersion, publication, null);
        }

		return results;
	}

    private Publication updateMetaData(
        @RequestParam Map<String, Object> requestParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication,
        PublicationMeta parentMetaDataConfig
    ) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> requestData        = requestParam;
        JsonNode requestMetadataCollection     = this.objectMapper.readValue(requestData.get("meta_data").toString(), JsonNode.class);
        Object requestFiles       = request.getAttribute("meta_data");

        Publication results            = publication;
        List<PublicationMeta> metaDataConfigs    = (List<PublicationMeta>) publication.getPublicationMeta();
        List<PublicationForm> formConfigs        = (List<PublicationForm>) this.getFormConfigsByParentId(
            formVersion.getPublicationFormCollection(),
            (parentMetaDataConfig != null) ? parentMetaDataConfig.getIdForm() : null
        );

        // Organize data requestData["meta_data"]
        Integer fieldConfigIndex = 0;
        for (PublicationForm fieldConfig : formConfigs) {
            
            /**
             * Initial value:
             * If there is Meta Data in previous Publication Meta Data, then use it as initial value.
             * Other than that, set Meta Data by Form Configuration.
             */
            JsonNode metaData           = (
                    this.getRequestMetaDataByUuid(
                        requestMetadataCollection,
                        fieldConfig.getUuid()
                    ) != null
                )
                ? this.getRequestMetaDataByUuid(
                    requestMetadataCollection,
                    fieldConfig.getUuid()
                )
                : this.getRequestMetaDataByFieldName(
                    requestMetadataCollection,
                    fieldConfig.getFieldName()
                );

            String metaDataConfigFromUuidQueries = (metaData != null)
                ? metaData.get("data").get("uuid").toString()
                : null;
            Long metaDataConfigFromIdQueries = (metaData != null)
                ? fieldConfig.getId()
                : null;

            PublicationMeta metaDataConfig     = (
                    (metaDataConfigs != null) && this.getPublicationMetaDataBy(metaDataConfigs, metaDataConfigFromUuidQueries) != null
                )
                ? this.getPublicationMetaDataBy(metaDataConfigs, metaDataConfigFromUuidQueries)
                : this.setPublicationMetaDataByPublicationFormConfig(
                    new PublicationMeta(),
                    publication,
                    formVersion,
                    fieldConfig,
                    metaData
                ); // 4: this.getPublicationFormDataByUuid(formConfigs, metaData["uuid"])

            metaDataConfig.setFlagActive(true);

            // Specifict handling by type of field
			switch (metaDataConfig.getFieldType()) {
                case "multiple":
                    /**
                     * this.updateMetaData(
                     *   request,
                     *   formVersion,
                     *   results,
                     *   metaDataConfig
                     * );
                     * metaDataConfig.setValue(
                     *   metaData["data"]["value"] ?? null
                     * );
                     * // dd(metaDataConfig.getFieldType(), requestData, metaData);
                     */
                    break;

                case "well":
                case "accordion":
                case "panel":
                case "stepper":
                case "step":
                    this.updateMetaData(requestParam, request, formVersion, results, metaDataConfig);
                    break;

                case "multiple_select":
                case "multiple_autoselect":
                case "multiple_autocomplete":
                    break;

                case "select":
                case "autoselect":
                case "autocomplete":
                    // metaDataConfig.setValue(
                    //     (metaData["data"]["value"]) ? metaData["data"]["value"] : null
                    // );
                    // metaDataConfig.setOtherValue(
                    //     (metaData["data"]["other_value"]) ? metaData["data"]["other_value"] : null
                    // );
                    break;

                case "file":
                case "image":
                    /** Check if file exist.
                     *  Another way is (isset(requestFiles) && isset(requestFiles[metaData["index"]])) */ 
                    // file = (requestFiles[metaData["index"]]["value"]) ? requestFiles[metaData["index"]]["value"] : null;
                    // uploadedFile = (file)
                    //     ? this.commonSvc.uploadFile(
                    //         file,
                    //         "publications_directory",
                    //         "api/v1/files/publications"
                    //     )
                    //     : null;

                    // if (uploadedFile) {
                    //     metaDataConfig.setValue(
                    //         (uploadedFile["original_name"]) ? uploadedFile["original_name"] : null
                    //     );
                    //     metaDataConfig.setOtherValue(uploadedFile);
                    // }
                    break;

                case "file-upload":
                case "image-upload":
                    /** Get temporary file meta data */
                    // temporaryFileUpload = (metaData && isset(metaData["data"]["value"]))
                    //     ? this.temporaryFileUploadRepo.findByUuid(metaData["data"]["value"])
                    //     : null;

                    // if (temporaryFileUpload) {
                    //     metaDataConfig.setValue(
                    //         (temporaryFileUpload) ? temporaryFileUpload.getValue() : null
                    //     );
                    //     metaDataConfig.setOtherValue(
                    //         (temporaryFileUpload) ? temporaryFileUpload.getOtherValue()[0] : null
                    //     );
                    // }
                    break;

                case "date":
                case "month":
                case "year":
                case "time":
                case "datetime":
                case "owl-date":
                case "owl-month":
                case "owl-year":
                case "owl-time":
                case "owl-datetime":
                    // metaDataConfig.setValue(
                    //     (metaData["data"]["value"]) ? metaData["data"]["value"] : null
                    // );
                    // metaDataConfig.setOtherValue([
                    //     "value" => null,
                    //     "text" => null
                    // ]);
                    break;

                case "daterange":
                case "timerange":
                case "datetimerange":
                case "owl-daterange":
                case "owl-timerange":
                case "owl-datetimerange":
                    break;

                case "radio":
                case "checkbox":
                case "mask":
                case "mask_full_time":
                case "url":
                default:
                    // metaDataConfig.setValue(
                    //     (metaData["data"]["value"]) ? metaData["data"]["value"] : null
                    // );
                    break;
			}

            metaDataConfig.setFlagActive(true);

            /**
             *  If parent is exist, it`s indicate that cerrent metaDataConfig is child of a field.
             *  So, we have to set the parent`s id to map the child is children of parent field.
             */
            if (parentMetaDataConfig != null) {
                metaDataConfig.setIdFormParent(parentMetaDataConfig.getId());
            }

            // this.logger.info(
            //     "Metadatas (field_type . " . fieldConfig.getFieldType()
            //     . ", field_name . " . fieldConfig.getFieldName()
            //     . "); value : ",
            //     metaData["data"] ? metaData["data"] : []
            // );

            // Push the Meta Data to Main Data
            results.addPublicationMeta(metaDataConfig);

            fieldConfigIndex++;
		}

        return results;
    }

    private PublicationMeta setPublicationMetaDataByPublicationFormConfig(
        PublicationMeta publicationMeta,
        Publication publication,
        PublicationFormVersion formVersion,
        PublicationForm fieldConfig,
        JsonNode metaData
    ) {
        PublicationMeta results = publicationMeta;

        /**
         * Organize data
         */

        // Ids 
        results.setId(this.commonSvc.createUUIDShort());
        results.setUuid(this.commonSvc.createUUID());

        // Master data
        results.setIdForm(fieldConfig.getId());
        results.setForm(fieldConfig);
        results.setIdPublication(publication.getId());
        results.setPublication(publication);
        results.setIdFormVersion(formVersion.getId());
        results.setFormVersion(formVersion);

        // Field configs
        results.setFieldLabel(fieldConfig.getFieldLabel());
        results.setFieldType(fieldConfig.getFieldType());
        results.setFieldName(fieldConfig.getFieldName());
        results.setFieldId(fieldConfig.getFieldId());
        results.setFieldClass(fieldConfig.getFieldClass());
        results.setFieldPlaceholder(fieldConfig.getFieldPlaceholder());
        results.setFieldOptions(fieldConfig.getFieldOptions());
        results.setFieldConfigs(fieldConfig.getFieldConfigs());
        results.setDescription(fieldConfig.getDescription());
        results.setOrderPosition(fieldConfig.getOrderPosition());
        results.setValidationConfigs(fieldConfig.getValidationConfigs());
        results.setErrorMessage(fieldConfig.getErrorMessage());
        results.setDependencyChild(fieldConfig.getDependencyChild());
        results.setDependencyParent(fieldConfig.getDependencyParent());
        results.setFlagRequired(fieldConfig.isFlagRequired());
        results.setFlagFieldFormType(fieldConfig.isFlagFieldFormType());
        results.setFlagFieldTitle(fieldConfig.isFlagFieldTitle());
        results.setFlagFieldPublishDate(fieldConfig.isFlagFieldPublishDate());
        results.setFlagActive(true);
        results.setValue(null);
        results.setOtherValue(null);

        // Specifict handling by type of field
        switch (fieldConfig.getFieldType()) {
            case "multiple":
                break;

            case "well":
            case "accordion":
            case "panel":
            case "stepper":
            case "step":
                break;

            case "multiple_select":
            case "multiple_autoselect":
            case "multiple_autocomplete":
                break;

            case "select":
            case "autoselect":
            case "autocomplete":
                // results.setValue(
                //     (metaData["data"]["value"]) ? metaData["data"]["value"].toString() : null
                // );
                // results.setOtherValue(
                //     (metaData["data"]["other_value"]) ? metaData["data"]["other_value"].toString() : null
                // );
                break;

            case "file":
            case "image":
            case "file-upload":
            case "image-upload":
                // results.setValue(
                //     (metaData["data"]["value"]) ? metaData["data"]["value"] : null
                // );
                // results.setOtherValue([
                //     "original_name" => null,
                //     "name" => null,
                //     "path" => null,
                //     "url" => null
                // ]);
                break;

            case "date":
            case "month":
            case "year":
            case "time":
            case "datetime":
            case "owl-date":
            case "owl-month":
            case "owl-year":
            case "owl-time":
            case "owl-datetime":
                // results.setValue(
                //     metaData["data"]["value"] ? metaData["data"]["value"] : null
                // );
                // results.setOtherValue([
                //     "value" => null,
                //     "text" => null
                // ]);
                break;

            case "daterange":
            case "timerange":
            case "datetimerange":
            case "owl-daterange":
            case "owl-timerange":
            case "owl-datetimerange":
                break;

            case "radio":
            case "checkbox":
            case "mask":
            case "mask_full_time":
            case "url":
            default:
                // results.setValue(
                //     metaData["data"]["value"] ? metaData["data"]["value"] : null
                // );
                break;
        }

        return results;
    }

	private PublicationForm setFieldConfigFromFormConfigs(
        Collection<PublicationForm> formConfigs,
        String key,
        Object value
    ) {
        Stream<PublicationForm> results = formConfigs.stream();

        switch (key) {
            case "id":
                results = results.filter(form -> form.getId().equals(value));
                break;

            case "uuid":
                results = results.filter(form -> form.getUuid().equals(value));
                break;

            case "field_name":
                results = results.filter(form -> form.getFieldName().equals(value));
                break;

            case "field_type":
                results = results.filter(form -> form.getFieldName().equals(value));
                break;

            default:
                // code
                break;
        }

		return results.findFirst().orElse(null);
	}

}
