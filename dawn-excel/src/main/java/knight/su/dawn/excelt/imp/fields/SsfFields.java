package knight.su.dawn.excelt.imp.fields;

import knight.su.dawn.excelt.common.FieldMappingType;
import knight.su.dawn.excelt.common.InvertEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by sugengbin 2019/04/17
 */
public class SsfFields {

	private Map<String, Integer> fieldMapColIdx;
	private Map<String, String> fieldMapColName;
	private Map<String, InvertEnum> invertMap;
	private FieldMappingType type;
	private List<String> fieldsNoAnnotation;

	public SsfFields(FieldMappingType type, Map<String, Integer> fieldMapColIdx, List<String> fieldsNoAnnotation) {
		this.fieldMapColIdx = fieldMapColIdx;
		this.type = type;
		this.fieldsNoAnnotation = fieldsNoAnnotation;
	}

	public Map<String, Integer> getFieldMapColIdx() {
		return fieldMapColIdx;
	}

	public void setFieldMapColIdx(Map<String, Integer> fieldMapColIdx) {
		this.fieldMapColIdx = fieldMapColIdx;
	}

	public Map<String, String> getFieldMapColName() {
		return fieldMapColName;
	}

	public void setFieldMapColName(Map<String, String> fieldMapColName) {
		this.fieldMapColName = fieldMapColName;
	}

	public FieldMappingType getType() {
		return type;
	}

	public void setType(FieldMappingType type) {
		this.type = type;
	}

	public List<String> getFieldsNoAnnotation() {
		return fieldsNoAnnotation;
	}

	public void setFieldsNoAnnotation(List<String> fieldsNoAnnotation) {
		this.fieldsNoAnnotation = fieldsNoAnnotation;
	}

	public Map<String, InvertEnum> getInvertMap() {
		return invertMap;
	}

	public void setInvertMap(Map<String, InvertEnum> invertMap) {
		this.invertMap = invertMap;
	}

}
