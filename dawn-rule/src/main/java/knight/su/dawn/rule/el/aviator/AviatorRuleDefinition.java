package knight.su.dawn.rule.el.aviator;

import knight.su.dawn.rule.api.Rule;

import java.util.List;

/** 
 *
 * Date:     2019年1月18日<br/> 
 * @author   sugengbin 
 */
public class AviatorRuleDefinition {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;
    private String condition;
    private List<String> actions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    AviatorRule create() {
    	AviatorRule aviatorRule = new AviatorRule()
                .name(name)
                .description(description)
                .priority(priority)
                .when(condition);
    	int index = 0;
        for (String action : actions) {
            aviatorRule.then(action, index++);
        }
        return aviatorRule;
    }
}
