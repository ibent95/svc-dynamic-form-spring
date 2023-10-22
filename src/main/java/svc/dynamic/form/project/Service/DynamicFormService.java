package svc.dynamic.form.project.Service;

import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DynamicFormService {

    @Autowired
    private ObjectMapper objectMapper;

	@Autowired
	private CommonService commonSvc;

	private final String UNIQUE_FIELD_NAME = "id";
	private final String PARENT_UNIQUE_FIELD_NAME = "id_form_parent";

	private final String[] GROUP_FIELDS = {
		"multiple", "stepper", "step", "accordion", "panel", "well"
	};

	private final String[] EXCLUDE_FIELDS = {
		"id", "id_publication", "id_form", "id_form_version", "id_form_parent", 
		"order", "flag_judul_publikasi", "flag_tgl_publikasi", 
		"flag_peran"
	};

	private Collection<HashMap<String, Object>> formFields = new ArrayList<>();

	/**
	 * Function to set grid system, organize array recursive data from flat array data, also clean the array recursive data
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public Collection<HashMap<String, Object>> setFields(
		CopyOnWriteArrayList<HashMap<String, Object>> flatArray,
		Map<String, Object> gridSystem
	) throws JsonMappingException, JsonProcessingException {

		this.formFields = flatArray;

		// Set the array from flat to recursive
		Collection<HashMap<String, Object>> recursiveData = this.setRecursiveFields(UNIQUE_FIELD_NAME, PARENT_UNIQUE_FIELD_NAME, this.GROUP_FIELDS, flatArray, null, gridSystem);

		// Return clean property in recursive data
		return this.cleanFields(this.EXCLUDE_FIELDS, recursiveData);
	}

	/**
	 * Recursive function to set array recursive data from flat array data
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public Collection<HashMap<String, Object>> setRecursiveFields(
		String uniqueFieldName, String parentUniqueFieldName,
		String[] groupFields, CopyOnWriteArrayList<HashMap<String, Object>> elements,
		Long parentId, Map<String, Object> gridSystem
	) throws JsonMappingException, JsonProcessingException {

        Collection<HashMap<String, Object>> results = new ArrayList<>();

		int elementIndex = 0;
		Iterator<HashMap<String, Object>> elementsIterator = elements.stream().iterator();
		while (elementsIterator.hasNext()) {
            HashMap<String, Object> element = elementsIterator.next();
			
			/**
			 * Main command in this recursive function.
			 * If the id of current element equal to the id of parent element,
			 * then run recursive function and other command.
			 */
			if (
				(element.get(parentUniqueFieldName) == null && parentId == null)
				|| (
					element.get(parentUniqueFieldName) != null
					&& parentId != null
					&& Long.parseLong(element.get(parentUniqueFieldName).toString()) == parentId
				)
			) {	
				
				// Remove current element from collection to avoid infinte loop
				elements.remove(element);

				// Set new temporary element and children element
				HashMap<String, Object> tempElement = new HashMap<>(element);
				Collection<HashMap<String, Object>> tempElementChildren = new ArrayList<>();
	
				// Set grid config
				tempElement.put("field_configs", this.setGridConfig(tempElement, gridSystem));
				tempElement.putIfAbsent("children", tempElementChildren);

				// Run recursive search
				Collection<HashMap<String, Object>> children = this.setRecursiveFields(
					uniqueFieldName, parentUniqueFieldName, groupFields, elements,
					Long.parseLong(
						tempElement.get(uniqueFieldName).toString()
					),
					gridSystem
				);

				this.commonSvc.setLogger(INFO, "Debug2 - dynamicFormSvc9");
                tempElement.put("children", children);

				// Define flag_multiple_field set it`s value to true if multiple field found in children data
				tempElement.put("flag_multiple_field", (
					(
                        tempElement.get("field_type").toString().equals("wizard") || tempElement.get("field_type").toString().equals("stepper")
                    ) &&
					children != null
				));

				results.add(tempElement);
			}

			elementIndex++;
		}

		return results;
	}

	/**
	 * Recursive function to remove unwanted property in array recursive data
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	private Collection<HashMap<String, Object>> cleanFields(String[] excludeFields, Collection<HashMap<String, Object>> recursiveData) throws JsonMappingException, JsonProcessingException {
		// Initial result variable
		Collection<HashMap<String, Object>> results = new ArrayList<>();

		// Loop of elements or data (forms metadata)
		for (HashMap<String, Object> element : recursiveData) {

			// Define filtered property of element variable

            // Procceed to filter element property
            for (String field : excludeFields) {
                if (element.containsKey(field)) {
                    element.remove(field);
                }
            }

			// Search for children data to filter it recursively
			if (element.get("children") != null) {
                String jsonData = this.objectMapper.writeValueAsString(element.get("children"));
				Collection<HashMap<String, Object>> children = this.cleanFields(
                    excludeFields,
                    this.objectMapper.readValue(jsonData, Collection.class)
                );
				element.put("children", children);
			}

			results.add(element);
		}

		return results;
	}

	/**
	 * Set grid config (grid system)
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	private HashMap<String, Object> setGridConfig(Map<String, Object> element, Map<String, Object> gridSystem) throws JsonMappingException, JsonProcessingException {
		// Initial result data
		HashMap<String, Object> results = new HashMap<>();

        // Merge current element`s or field`s config with grid system config
        if (element.get("field_configs") != null) {
            String fieldConfigsJsonData   = this.objectMapper.writeValueAsString(element.get("field_configs"));
            JsonNode fieldConfigsJsonNode = this.objectMapper.readValue(fieldConfigsJsonData, JsonNode.class);
            HashMap<String, Object> fieldConfigs = this.objectMapper.readValue(
                fieldConfigsJsonNode.toString(),
                HashMap.class
            );

            results.putAll(fieldConfigs);
        }

        // Cast gridSystem to JSON data and merge grid system config to results
        if (gridSystem != null) {
            String gridSystemJsonData   = this.objectMapper.writeValueAsString(gridSystem);
            JsonNode gridSystemJsonNode = this.objectMapper.readValue(gridSystemJsonData, JsonNode.class);

            if (gridSystemJsonNode != null && gridSystemJsonNode
                .get("config")
                .get(
                    element.get("field_id").toString()
                ) != null
            ) {
                HashMap<String, Object> elementGridConfig = this.objectMapper.readValue(
                    gridSystemJsonNode
                        .get("config")
                        .get(
                            element.get("field_id").toString()
                        )
                        .toString(),
                    HashMap.class
                );

                results.putAll(elementGridConfig);
            }
        }

		return results;
	}

}
