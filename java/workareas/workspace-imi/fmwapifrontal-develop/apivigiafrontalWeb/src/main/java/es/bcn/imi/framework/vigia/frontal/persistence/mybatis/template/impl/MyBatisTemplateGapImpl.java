package es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;

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
