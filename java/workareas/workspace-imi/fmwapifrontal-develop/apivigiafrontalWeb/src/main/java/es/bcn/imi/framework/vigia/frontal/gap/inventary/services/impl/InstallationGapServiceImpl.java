package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.InstallationGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.InstallationGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.InstallationGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.InstallationGap;
import es.bcn.vigia.fmw.libutils.convert.InstallationGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_INSTALLATION_GAP)
public class InstallationGapServiceImpl implements InstallationGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<InstallationGapBDTO> getInstallationsGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<InstallationGap> installations = myBatisTemplateGap.execute(InstallationGapDao.class,
				new MyBatisDaoCallbackGap<List<InstallationGap>>() {
				
					@Override
					public List<InstallationGap> execute(MyBatisDaoGap dao) {
						return ((InstallationGapDao) dao).getInstallationsGap(params);
					}

				});
		

		 return InstallationGapConvert.object2bdto(installations);

	}

}