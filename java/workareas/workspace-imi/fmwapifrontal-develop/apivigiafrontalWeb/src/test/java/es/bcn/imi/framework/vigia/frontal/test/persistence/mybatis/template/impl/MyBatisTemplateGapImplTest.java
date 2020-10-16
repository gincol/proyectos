package es.bcn.imi.framework.vigia.frontal.test.persistence.mybatis.template.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.ValueListGapDao;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.impl.MyBatisTemplateGapImpl;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class MyBatisTemplateGapImplTest {

	@InjectMocks
	private MyBatisTemplateGapImpl mybatis;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = NullPointerException.class)
	public <T> void caseOK() {
		mybatis.execute(ValueListGapDao.class, new MyBatisDaoCallbackGap<T>() {

			@Override
			public T execute(MyBatisDaoGap dao) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
