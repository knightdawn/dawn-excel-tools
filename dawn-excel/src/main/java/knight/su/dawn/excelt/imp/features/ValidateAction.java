package knight.su.dawn.excelt.imp.features;

import knight.su.dawn.excelt.common.ExceltConstants;
import knight.su.dawn.excelt.common.ExceltError;
import knight.su.dawn.excelt.exception.ValidateException;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.rule.basic.Facts;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by sugengbin 2019/04/04
 */
public interface ValidateAction {

	/**
	 * 
	 * @param instance
	 * @param validation
	 * @param errorConsumer
	 * @param row
	 * @return
	 */
	default <T> boolean validate(T instance, Validation validation, Consumer<CellError> errorConsumer, int row,
                                 ValidateContext vc) {
		if (Objects.isNull(validation) || Objects.isNull(validation.rules())) {
			return Boolean.TRUE;
		}
		vc.resetListener();
		Facts facts = vc.getFacts();
		facts.put(ExceltConstants.RULE_INSTANCE, instance);
		vc.getRulesEngine().fire(validation.rules(), facts, result -> {
			String error = result.get(ExceltConstants.RULE_RESULT_0);
			if (StringUtils.isNotBlank(error)) {
				errorConsumer.accept(new CellError(row, error));
			}
		});
		return vc.getListener().isPass();
	}

	/**
	 * 
	 *
	 * @Date 2019/1/11
	 * @Param: count
	 * @return
	 */
	default <T> void validateEmpty(int count) {
		if (count == 0) {
			throw new ValidateException(ExceltError.EMPTY_FILE_CODE, ExceltError.EMPTY_FILE);
		}
	}

	/**
	 *
	 * @Date 2019/1/11
	 * @Param: count
	 * @Param: validation
	 * @return
	 */
	default <T> void validateLimit(int count, Validation validation) {
		if (validation.getLimit() != -1 && count > validation.getLimit()) {
			throw new ValidateException(ExceltError.OVER_LIMIT_CODE,
					String.format(ExceltError.OVER_LIMIT, validation.getLimit()));
		}
	}

	/**
	 * 
	 * @param exceltitlesRow2
	 * @param validateTitles
	 * @param vtf
	 */
	default void validateTitles(List<String> exceltitlesRow2, String[] validateTitles, boolean vtf) {
		if (Objects.isNull(validateTitles)) {
			return;
		}
		int col = 0;
		String colValue = "";
		boolean pass = validateTitles.length == exceltitlesRow2.size();
		if (pass) {
			for (int i = 0; i < validateTitles.length; i++) {
				if (!validateTitles[i].equals(StringUtils.trimToEmpty(exceltitlesRow2.get(i)))) {
					pass = false;
					col = i + 1;
					colValue = validateTitles[i];
					break;
				}
			}
		} else {
			throw new ValidateException(ExceltError.WRONG_TEMPLATE_CODE, ExceltError.WRONG_TEMPLATE_COL_NUM);
		}
		if (!pass) {
			if (vtf) {
				throw new ValidateException(ExceltError.WRONG_TEMPLATE_CODE, ExceltError.WRONG_TEMPLATE);
			} else {
				throw new ValidateException(ExceltError.WRONG_TEMPLATE_CODE,
						String.format(ExceltError.WRONG_TEMPLATE_FORMAT, col, colValue));
			}
		}
	}
}
