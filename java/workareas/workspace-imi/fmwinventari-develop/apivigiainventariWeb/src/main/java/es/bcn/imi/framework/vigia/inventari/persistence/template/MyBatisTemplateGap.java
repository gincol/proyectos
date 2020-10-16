package es.bcn.imi.framework.vigia.inventari.persistence.template;

public interface MyBatisTemplateGap {

	/**
	 * Executes ORM operations in the callback DAO
	 * 
	 * @param callback
	 * @return Type of SQL operation result
	 */
	public <T> T execute(Class<?> daoClass, MyBatisDaoCallbackGap<T> callback);
}
