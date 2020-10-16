package es.bcn.imi.framework.vigia.inventari.persistence.template.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;

public class MyBatisTemplateGapImpl implements MyBatisTemplateGap {

	private SqlSessionFactory sqlSessionFactory;

	public MyBatisTemplateGapImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public <T> T execute(Class<?> daoClass, MyBatisDaoCallbackGap<T> callback) {
		SqlSession session = sqlSessionFactory.openSession();
		T t = null;
		try {
			MyBatisDaoGap dao = (MyBatisDaoGap) session.getMapper(daoClass);
			t = callback.execute(dao);
		} finally {
			session.close();
		}
		return t;
	}

}
