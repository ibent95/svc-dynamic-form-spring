package svc.dynamic.form.project.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import svc.dynamic.form.project.Entity.Publication;
import svc.dynamic.form.project.Entity.PublicationForm;
import svc.dynamic.form.project.Entity.PublicationFormVersion;
import svc.dynamic.form.project.Entity.PublicationGeneralType;
import svc.dynamic.form.project.Entity.PublicationMeta;
import svc.dynamic.form.project.Entity.PublicationStatus;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Entity.TemporaryFileUpload;
import svc.dynamic.form.project.Exception.FileStorageException;
import svc.dynamic.form.project.Repository.PublicationStatusRepository;
import svc.dynamic.form.project.Repository.TemporaryFileUploadRepository;

@Service
@Transactional
public class PublicationService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonSvc;
    @Autowired
    private PublicationStatusRepository publicationStatusRepo;
    @Autowired
    private TemporaryFileUploadRepository temporaryFileUploadRepo;

	HashMap<String, Object> hashMapResults;
	Collection<HashMap<String, Object>> collectionResults;
	CopyOnWriteArrayList<HashMap<String, Object>> arrayListResults;
	List<Object> results;
	Object result;

    public PublicationService() { /** code */ }

    public PublicationFormVersion getActiveFormVersionData(
        Collection<PublicationFormVersion> formVersionCollections
    ) {
        return formVersionCollections
            .stream()
            .filter(
                item -> item.isFlagActive()
            )
            .findFirst()
            .get();
    }

    public PublicationMeta getPublicationMetaDataBy(
        Collection<PublicationMeta> sourceData1,
        String uuid
    ) {
        Optional<PublicationMeta> results = sourceData1
            .stream()
            .filter(
                item -> item.getUuid().equals(uuid)
            )
            .findFirst();
        return (results.isPresent()) ? results.get() : null;
    }

    private JsonNode getRequestMetaDataByUuid(
        Collection<JsonNode> sourceData2,
        String uuid
    ) {
        Optional<JsonNode> results = sourceData2
            .stream()
            .filter(item
                -> item.get("uuid").asText().equals(uuid)
            )
            .findFirst();
        return (results.isPresent()) ? results.get() : null;
    }

    private JsonNode getRequestMetaDataByFieldName(
        Collection<JsonNode> sourceData3,
        String fieldName
    ) {
        Optional<JsonNode> results = sourceData3
            .stream()
            .filter(item
                -> item.get("field_name").asText().equals(fieldName)
            )
            .findFirst();
        return (results.isPresent()) ? results.get() : null;
    }

    private Collection<PublicationForm> getFormConfigsByParentId(
        Collection<PublicationForm> sourceData4,
        Long parentId
    ) {
        return sourceData4.stream().filter(item -> item.getIdFormParent() == parentId).toList();
    }

    /**
	 * This function response to get Main and Meta Data of input from Dynamic Form
     * @throws IOException
     * @throws FileStorageException
	 */
    public Publication setDataByDynamicForm(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws FileStorageException, IOException {
        Publication mainData    = this.setMainData(requestParam, requestFilesParam, request, formVersion, publication);
        Publication results     = this.setMetaData(requestParam, requestFilesParam, request, formVersion, mainData);

        return results;
    }

	/**
	 * This function response to organize Main Data of input from Dynamic Form
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	private Publication setMainData(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws JsonMappingException, JsonProcessingException {

        /**
         *  Initial value
         *  results is an instance of Publication entity or publication
         *  If publication is send than UPDATE mechanism is running, default is CREATE.
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
	 * @throws IOException
	 * @throws FileStorageException
	 */
	private Publication setMetaData(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication
    ) throws FileStorageException, IOException {
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

        // Organize the new Meta Data (create or update)
        if (request.getMethod().equals("POST")) {
            results    = this.updateMetaData(requestParam, requestFilesParam, request, formVersion, publication, null);
        }

        if (request.getMethod().equals("PUT")) {
            results    = this.updateMetaData(requestParam, requestFilesParam, request, formVersion, publication, null);
        }

		return results;
	}

    private Publication updateMetaData(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request,
        PublicationFormVersion formVersion,
        Publication publication,
        PublicationMeta parentMetaDataConfig
    ) throws FileStorageException, IOException {

        Map<String, Object> requestData        = requestParam;
        List<MultipartFile> requestFiles       = requestFilesParam;
        Collection<JsonNode> requestMetadataCollection = this.commonSvc.convertIteratorToCollection(
            this.objectMapper.readValue(
                requestData.get("meta_data").toString(), JsonNode.class
            ).elements()
        );

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
                ? metaData.get("uuid").asText()
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
                    this.updateMetaData(requestParam, requestFilesParam, request, formVersion, results, metaDataConfig);
                    break;

                case "multiple_select":
                case "multiple_autoselect":
                case "multiple_autocomplete":
                    break;

                case "select":
                case "autoselect":
                case "autocomplete":
                    metaDataConfig.setValue(
                        (metaData != null && metaData.get("value") != null) ? metaData.get("value").asText() : null
                    );
                    metaDataConfig.setOtherValue(
                        (metaData != null && metaData.get("other_value") != null) ? this.objectMapper.convertValue(metaData.get("other_value"), HashMap.class) : null
                    );
                    break;

                case "file":
                case "image":
                    /** Check if file exist.
                     *  Another way is (isset(requestFiles) && isset(requestFiles[metaData["index"]])) */
                    MultipartFile file = (metaData != null && requestFiles.get(metaData.get("file_index").asInt()) != null)
                        ? requestFiles.get(
                            metaData.get("file_index").asInt()
                        )
                        : null;
                    HashMap<String, Object> uploadedFile = (file != null)
                        ? this.commonSvc.uploadFile(
                            file,
                            "publications_directory",
                            "api/v1/files/publications"
                        )
                        : null;

                    if (uploadedFile != null) {
                        metaDataConfig.setValue(
                            (uploadedFile.get("original_name") != null) ? uploadedFile.get("original_name").toString() : null
                        );
                        metaDataConfig.setOtherValue(uploadedFile);
                    }
                    break;

                case "file-upload":
                case "image-upload":
                    /** Get temporary file meta data */
                    TemporaryFileUpload temporaryFileUpload = (metaData != null && metaData.get("value") != null)
                        ? this.temporaryFileUploadRepo.findByUuid(metaData.get("value").asText())
                        : null;

                    if (temporaryFileUpload != null) {
                        metaDataConfig.setValue(
                            (temporaryFileUpload != null) ? temporaryFileUpload.getValue() : null
                        );
                        metaDataConfig.setOtherValue(
                            (temporaryFileUpload != null) ? temporaryFileUpload.getOtherValue() : null
                        );
                    }
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
                    metaDataConfig.setValue(
                        (metaData != null && metaData.get("value") != null) ? metaData.get("value").asText() : null
                    );
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
                    metaDataConfig.setValue(
                        (metaData != null && metaData.get("value") != null) ? metaData.get("value").asText() : null
                    );
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

    public HashMap<String, Object> setGetFormMetaData(PublicationFormVersion formVersion, Collection<HashMap<String, Object>> forms) throws JsonMappingException, JsonProcessingException {
        this.hashMapResults = new HashMap<String, Object>();

        String jsonData = this.objectMapper.writeValueAsString(formVersion);
        
        this.hashMapResults = this.objectMapper.readValue(jsonData, HashMap.class);

        this.hashMapResults.put("forms", forms);

        return this.hashMapResults;
    }

    public CopyOnWriteArrayList<HashMap<String, Object>> getFormMetaDataByPublicationFormCollection(Collection<PublicationForm> forms)
    throws JsonMappingException, JsonProcessingException {
        this.arrayListResults = new CopyOnWriteArrayList<>();

        Collection<PublicationForm> formsCollection = forms
            .stream()
            .filter(
                item -> item.isFlagActive()
            ).toList();

        String jsonData = this.objectMapper.writeValueAsString(formsCollection);

        this.arrayListResults = this.objectMapper.readValue(jsonData, CopyOnWriteArrayList.class);

        return this.arrayListResults;
    }

    public CopyOnWriteArrayList<HashMap<String, Object>> getFormMetaDataByPublicationMetaCollection(Collection<PublicationMeta> forms)
    throws JsonMappingException, JsonProcessingException {
        this.arrayListResults = new CopyOnWriteArrayList<>();

        String jsonData = this.objectMapper.writeValueAsString(forms);

        this.arrayListResults = this.objectMapper.readValue(jsonData, CopyOnWriteArrayList.class);

        return this.arrayListResults;
    }

}
