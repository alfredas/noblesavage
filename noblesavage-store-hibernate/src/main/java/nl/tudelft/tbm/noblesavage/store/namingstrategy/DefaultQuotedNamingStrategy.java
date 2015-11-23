package nl.tudelft.tbm.noblesavage.store.namingstrategy;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class DefaultQuotedNamingStrategy extends ImprovedNamingStrategy {

  private static final long serialVersionUID = 1L;

	@Override
	public String classToTableName(String className) {
		return addQuotes(super.classToTableName(className));
	}

	@Override
	public String tableName(String tableName) {
		return addQuotes(super.tableName(tableName));
	}

	private static String addQuotes(String input) {
		return new StringBuffer().append('`').append(input).append('`').toString();
	}
}