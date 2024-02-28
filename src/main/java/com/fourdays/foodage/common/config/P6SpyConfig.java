package com.fourdays.foodage.common.config;

import java.util.Locale;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import jakarta.annotation.PostConstruct;

@Configuration
public class P6SpyConfig implements MessageFormattingStrategy {

	private static final String CREATE = "create";
	private static final String ALTER = "alter";
	private static final String COMMENT = "comment";

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
	}

	@Override
	public String formatMessage(int connectionId, String now, long elapsed,
		String category, String prepared, String sql,
		String url) {

		sql = formatSql(category, sql);
		return String.format("[%s] | %d ms | %s", category, elapsed, sql);
	}

	private String formatSql(String category, String sql) {

		if (sql == null && sql.trim().isEmpty()) {
			return sql;
		}

		if (isStatementAndStartsWithDDL(category, sql)) {
			sql = FormatStyle.DDL.getFormatter().format(sql);
		} else {
			sql = FormatStyle.BASIC.getFormatter().format(sql);
		}

		return sql;
	}

	private boolean isStatementAndStartsWithDDL(String category, String sql) {

		String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);

		boolean isStatement = category.equals(Category.STATEMENT.getName());
		boolean isStartsWithDDL =
			trimmedSQL.startsWith(CREATE) || trimmedSQL.startsWith(ALTER) || trimmedSQL.startsWith(COMMENT);

		return isStatement && isStartsWithDDL;
	}
}
