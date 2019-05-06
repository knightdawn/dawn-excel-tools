package knight.su.dawn.excelt.imp.config;

import knight.su.dawn.excelt.common.LogicalOp;
import knight.su.dawn.rule.basic.Rules;
import knight.su.dawn.rule.el.aviator.AviatorRuleFactory;
import knight.su.dawn.rule.el.mvel.MVELRuleFactory;
import knight.su.dawn.rule.extend.YamlStringReader;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import static knight.su.dawn.excelt.exp.validate.ValidateAssert.*;

/**
 * Created by sugengbin 2019/1/4
 */
public class Validation {

    /**
     * <pre>
     * 记录限制,默认-1时,不做限制 (忽略表头行，只包含读取的非空记录行)
     * </pre>
     */
    private int limit = -1;

    /**
     * <pre>
     * 校验规则(yaml格式)，为空时跳过不校验
     * </pre>
     */
    private String validateRule;

    /**
     * <pre>
     * 初始化后的校验规则
     * </pre>
     */
    private Rules rules;

    /**
     * <pre>
     * 验证的sheet名称，用来确认模板是否正确，如果为空则不进行校验
     * </pre>
     */
    private String validateSheetName = "";

    /**
     * <pre>
     * 验证的表头名，用来确认模板是否正确，如果为空则不进行校验
     * </pre>
     */
    private String[] validateTitles = null;

    /**
     * <pre>
     * 0：不校验，
     * 1：只校验shee名称，
     * 2：只校验表头名
     * 3：12都校验
     * </pre>
     */
    private int validateTemplateFlag = 0;
    
    /**
	  * 校验模板（暂时支持sax模式）：
	 * validate template field
	 * false: 老方式校验，校验表头每一列
	 * true: 新方式校验，校验映射字段的列表头
	 */
	private boolean vtf = false;
	
	public Validation validateTemplateWithField() {
		this.vtf = true;
		return this;
	}
	
	public boolean isVtf() {
		return vtf;
	}
    
    public int getValidateTemplateFlag() {
		return validateTemplateFlag;
	}

	/**
     * <pre>
     * validateSheetName 和 validateTitles 同时存在，则其中一项校验成功既可
     * </pre>
     */
    private LogicalOp validateFileLogical = LogicalOp.OR;

    public LogicalOp getValidateFileLogical() {
        return validateFileLogical;
    }

    public Validation setValidateFileLogical(LogicalOp validateFileLogical) {
        notNull(validateFileLogical, "LogicalOp null");
        this.validateFileLogical = validateFileLogical;
        return this;
    }

    public String[] getValidateTitles() {
        return validateTitles;
    }

    public Validation validateTitles(String... titles) {
        notNull(titles, "titles null");
        this.validateTitles = titles;
		if (StringUtils.isBlank(this.validateSheetName)) {
			this.validateTemplateFlag = 2;
		} else {
			this.validateTemplateFlag = 3;
		}
        return this;
    }

    public Validation validateTemplate(List<String> titles) {
        notEmpty(titles, "title empty");
        return this.validateTitles(titles.toArray(new String[titles.size()]));
    }

    public String getValidateSheetName() {
        return validateSheetName;
    }

    /**
     * @param sheetName
     * @return
     */
    public Validation validateSheet(String sheetName) {
        notBlank(sheetName, "sheetName blank");
        this.validateSheetName = sheetName;
        if (Objects.isNull(this.validateTitles)) {
			this.validateTemplateFlag = 1;
		} else {
			this.validateTemplateFlag = 3;
		}
        return this;
    }

    /**
     * @return
     */
    public static Validation defaultSetting() {
        return new Validation();
    }

    public String getValidateRule() {
        return validateRule;
    }

    public Validation validate(String validateRule) {
        notBlank(validateRule, "validateRule blank");
        this.validateRule = validateRule;
        buildValiddateRules(validateRule);
        return this;
    }

    public Validation validateAviator(String validateRule) {
        notBlank(validateRule, "validateRule blank");
        this.validateRule = validateRule;
        buildValiddateRulesAviator(validateRule);
        return this;
    }
    
    /**
     * init validate rule for engine
     *
     * @param validateRule
     */
    private void buildValiddateRulesAviator(String validateRule) {
        notBlank(validateRule, "validateRule blank");
        YamlStringReader reader = new YamlStringReader(validateRule);
        this.rules = AviatorRuleFactory.createRulesFrom(reader.getStringReader());
    }
    
    /**
     * init validate rule for engine
     *
     * @param validateRule
     */
    private void buildValiddateRules(String validateRule) {
        notBlank(validateRule, "validateRule blank");
        YamlStringReader reader = new YamlStringReader(validateRule);
        this.rules = MVELRuleFactory.createRulesFrom(reader.getStringReader());
    }

    public Rules rules() {
        return this.rules;
    }

    public int getLimit() {
        return limit;
    }

    public Validation limit(int limit) {
        isPositive(limit, "limit not positive");
        this.limit = limit;
        return this;
    }
}
