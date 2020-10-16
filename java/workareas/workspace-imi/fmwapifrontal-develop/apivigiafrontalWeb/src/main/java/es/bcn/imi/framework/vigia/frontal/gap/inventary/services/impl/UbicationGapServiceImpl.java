package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.UbicationGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.UbicationGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.UbicationGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.UbicationGap;
import es.bcn.vigia.fmw.libutils.convert.UbicationGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_UBICATION_GAP)
public class UbicationGapServiceImpl implements UbicationGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<UbicationGapBDTO> getUbicationsGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<UbicationGap> ubications = myBatisTemplateGap.execute(UbicationGapDao.class,
				new MyBatisDaoCallbackGap<List<UbicationGap>>() {
				
					@Override
					public List<UbicationGap> execute(MyBatisDaoGap dao) {
						return ((UbicationGapDao) dao).getUbicationsGap(params);
					}

				});
		
		 return UbicationGapConvert.object2bdto(ubications);
		

	}


	@Override
	public List<UbicationGapBDTO> getUbicationsGapByCodeType(String codeUbication, String codeUbicationType) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		params.put("code", codeUbication);
		params.put("type", codeUbicationType);
		List<UbicationGap> ubications = myBatisTemplateGap.execute(UbicationGapDao.class,
				new MyBatisDaoCallbackGap<List<UbicationGap>>() {

					@Override
					public List<UbicationGap> execute(MyBatisDaoGap dao) {
						return ((UbicationGapDao) dao).getUbicationsGapByCodeType(params);
					}

				});

		return UbicationGapConvert.object2bdto(ubications);

	}




}