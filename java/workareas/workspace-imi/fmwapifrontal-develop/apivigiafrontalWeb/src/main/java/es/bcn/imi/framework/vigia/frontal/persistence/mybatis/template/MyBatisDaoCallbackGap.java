package es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template;

public abstract class MyBatisDaoCallbackGap<T> {
	
	/**
	 * Executes operations on given DAO
	 * 
	 * @param dao
	 *            DAO on execute operation
	 * @return Result of the operation
	 */
	public abstract T execute(MyBatisDaoGap dao);

}
