package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.ValueListGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.ValueListGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ValueListGap;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libutils.convert.ValueListGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_VALUE_LIST_GAP)
public class ValueListGapServiceImpl implements ValueListGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override 
	public List<ValueListGapBDTO> getValueListGap(final Entity entity) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		List<ValueListGap> valueLists = myBatisTemplateGap.execute(ValueListGapDao.class,
				new MyBatisDaoCallbackGap<List<ValueListGap>>() {
				
					@Override
					public List<ValueListGap> execute(MyBatisDaoGap dao) {
						return ((ValueListGapDao) dao).getValueListsGap(entity.getValue());
					}

				});

		return ValueListGapConvert.object2bdto(valueLists);
	}

		
}