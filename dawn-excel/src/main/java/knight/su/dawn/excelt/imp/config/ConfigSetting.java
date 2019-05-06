package knight.su.dawn.excelt.imp.config;

/**
 * Created by sugengbin 2019/04/18
 */
public final class ConfigSetting<T> {

	private Condition<T> filter;
	private ParserOption option;
	private Validation validation;

	public static <T> ConfigSetting<T> defaultSetting() {
		return new ConfigSetting<T>();
	}

	public ConfigSetting() {
		this.option = ParserOption.defaultSetting();
		this.validation = Validation.defaultSetting();
	}

	public ConfigSetting(ParserOption option, Validation validation) {
		this.option = option;
		this.validation = validation;
	}

	public ConfigSetting(Condition<T> filter, ParserOption option, Validation validation) {
		this.filter = filter;
		this.option = option;
		this.validation = validation;
	}

	public Condition<T> filter() {
		return filter;
	}

	public void setFilter(Condition<T> filter) {
		this.filter = filter;
	}

	public ParserOption parserOption() {
		return option;
	}

	public void setOption(ParserOption option) {
		this.option = option;
	}

	public Validation validation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

}
