package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.CommerceGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.CommerceGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.CommerceGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceGap;
import es.bcn.vigia.fmw.libutils.convert.CommerceGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_COMMERCE_GAP)
public class CommerceGapServiceImpl implements CommerceGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;


	@Override
	public List<CommerceGapBDTO> getCommercesGap(Map<String, Object> params) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		 List<CommerceGap> commerces = myBatisTemplateGap.execute(CommerceGapDao.class,
				new MyBatisDaoCallbackGap<List<CommerceGap>>() {
				
					@Override
					public List<CommerceGap> execute(MyBatisDaoGap dao) {
						return ((CommerceGapDao) dao).getCommercesGap(params);
					}

				});
		

		 return CommerceGapConvert.object2bdto(commerces);

	}

}