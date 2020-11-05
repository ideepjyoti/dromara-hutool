package cn.hutool.extra.expression;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 表达式异常
 * 
 * @author independenter
 */
public class ExpressionException extends RuntimeException {
	private static final long serialVersionUID = 7034897190745766867L;

	public ExpressionException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}

	public ExpressionException(String message) {
		super(message);
	}

	public ExpressionException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}

	public ExpressionException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ExpressionException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
