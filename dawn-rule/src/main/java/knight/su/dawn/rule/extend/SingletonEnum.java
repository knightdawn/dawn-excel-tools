package knight.su.dawn.rule.extend;

/**
 *
 * Date: 2019年1月16日<br/>
 * 
 * @author sugengbin
 */
public enum SingletonEnum {

	CONDITIONS;
	private Conditions instance;

	private SingletonEnum() {
		instance = new Conditions();
	}

	public Conditions getInstance() {
		return instance;
	}

}
