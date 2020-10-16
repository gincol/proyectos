package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.ValueListDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.ValueListService;
import es.bcn.vigia.fmw.libcommons.business.dto.ValueListBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.ValueList;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libutils.convert.ValueListConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_VALUE_LIST)
public class ValueListServiceImpl implements ValueListService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Override
	public List<ValueListBDTO> getValueList(final Type type) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		List<ValueList> valueLists = myBatisTemplate.execute(ValueListDao.class,
				new MyBatisDaoCallback<List<ValueList>>() {
				
					@Override
					public List<ValueList> execute(MyBatisDao dao) {
						return ((ValueListDao) dao).getValueLists(type.getValue());
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return ValueListConvert.object2bdto(valueLists);
		
	}
}