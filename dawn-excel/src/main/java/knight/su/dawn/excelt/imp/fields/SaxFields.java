package knight.su.dawn.excelt.imp.fields;

import knight.su.dawn.excelt.common.InvertEnum;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by sugengbin 2019/04/04
 */
public class SaxFields {

	private Map<String, Field> fieldsMap;
	private Map<String, InvertEnum> invertMap;
	private List<Field> fieldsNoAnnotation;

	public SaxFields(Map<String, Field> fieldsMap, List<Field> fieldsNoAnnotation, Map<String, InvertEnum> invertMap) {
		this.fieldsMap = fieldsMap;
		this.fieldsNoAnnotation = fieldsNoAnnotation;
		this.invertMap = invertMap;
	}

	public Map<String, Field> getFieldsMap() {
		return fieldsMap;
	}

	public void setFieldsMap(Map<String, Field> fieldsMap) {
		this.fieldsMap = fieldsMap;
	}

	public Map<String, InvertEnum> getInvertMap() {
		return invertMap;
	}

	public void setInvertMap(Map<String, InvertEnum> invertMap) {
		this.invertMap = invertMap;
	}

	public List<Field> getFieldsNoAnnotation() {
		return fieldsNoAnnotation;
	}

	public void setFieldsNoAnnotation(List<Field> fieldsNoAnnotation) {
		this.fieldsNoAnnotation = fieldsNoAnnotation;
	}

}
