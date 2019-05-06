package knight.su.dawn.rule.el.aviator;

import knight.su.dawn.rule.api.Rule;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FIXME: 替换yaml为更简洁的方式
 * 
 * Date: 2019年1月18日<br/>
 * 
 * @author sugengbin
 */
public class AviatorRuleDefinitionReader {

	private Yaml yaml = new Yaml();

	@SuppressWarnings("unchecked")
	AviatorRuleDefinition read(Reader reader) {
		Object object = yaml.load(reader);
		Map<String, Object> map = (Map<String, Object>) object;
		return createRuleDefinitionFrom(map);
	}

	@SuppressWarnings("unchecked")
	List<AviatorRuleDefinition> readAll(Reader reader) {
		List<AviatorRuleDefinition> ruleDefinitions = new ArrayList<>();
		Iterable<Object> rules = yaml.loadAll(reader);
		for (Object rule : rules) {
			Map<String, Object> map = (Map<String, Object>) rule;
			ruleDefinitions.add(createRuleDefinitionFrom(map));
		}
		return ruleDefinitions;
	}

	@SuppressWarnings("unchecked")
	private static AviatorRuleDefinition createRuleDefinitionFrom(Map<String, Object> map) {
		AviatorRuleDefinition ruleDefinition = new AviatorRuleDefinition();

		String name = String.valueOf(map.get("name") == null ? "" : map.get("name"));
		ruleDefinition.setName(name != null && !name.equals("") ? name : Rule.DEFAULT_NAME);

		String description = (String) map.get("description");
		ruleDefinition.setDescription(description != null ? description : Rule.DEFAULT_DESCRIPTION);

		Integer priority = (Integer) map.get("priority");
		ruleDefinition.setPriority(priority != null ? priority : Rule.DEFAULT_PRIORITY);

		String condition = (String) map.get("when");
		if (condition == null) {
			throw new IllegalArgumentException("The rule condition(when) must be specified");
		}
		ruleDefinition.setCondition(condition);

		List<String> actions = (List<String>) map.get("then");
		if (actions == null || actions.isEmpty()) {
			throw new IllegalArgumentException("The rule action(s)(then) must be specified");
		}
		ruleDefinition.setActions(actions);

		return ruleDefinition;
	}
}
