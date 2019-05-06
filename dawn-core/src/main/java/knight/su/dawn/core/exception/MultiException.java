package knight.su.dawn.core.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常容器，用来封装返回多个异常的场景
 * 比如某个场景可能抛出多种异常，而多种异常作为一个整体一起返回，这时候就可以使用异常容器封装多个异常再抛出。
 * @author Edward
 * @date 2019年3月31日
 * @version 1.0
 */
public class MultiException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<Throwable> causes = new ArrayList<>();
	
	public MultiException(List<Throwable> causes) {
		this.causes.addAll(causes);
	}
	
	public List<Throwable> getExceptions() {
		return causes;
	}
	
}
