package knight.su.dawn.rule.el.aviator;

import knight.su.dawn.rule.basic.Rules;
import knight.su.dawn.rule.extend.YamlStringReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * Date: 2019年1月18日<br/>
 * 
 * @author sugengbin
 */
public class AviatorRuleFactory {

	private static AviatorRuleDefinitionReader reader = new AviatorRuleDefinitionReader();

	/**
	 * Create a new {@link AviatorRule} from a Reader.
	 *
	 * @param ruleDescriptor
	 *            as a Reader
	 * @return a new rule
	 */
	public static AviatorRule createRuleFrom(Reader ruleDescriptor) {
		AviatorRuleDefinition ruleDefinition = reader.read(ruleDescriptor);
		return ruleDefinition.create();
	}

	/**
	 * Create a set of {@link AviatorRule} from a Reader.
	 *
	 * @param rulesDescriptor
	 *            as a Reader
	 * @return a set of rules
	 */
	public static Rules createRulesFrom(Reader rulesDescriptor) {
		Rules rules = new Rules();
		List<AviatorRuleDefinition> ruleDefinition = reader.readAll(rulesDescriptor);
		for (AviatorRuleDefinition aviatorRuleDefinition : ruleDefinition) {
			rules.register(aviatorRuleDefinition.create());
		}
		return rules;
	}

	/**
	 * Create a set of {@link AviatorRule} from a file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Rules createRulesFrom(String filePath) throws IOException {
		YamlStringReader reader = new YamlStringReader(new String(Files.readAllBytes(Paths.get(filePath))));
		return createRulesFrom(reader.getStringReader());
	}
}
