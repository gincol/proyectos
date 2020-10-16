package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.MaterialResourceGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.MaterialResourceGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.MaterialResourceGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MaterialResourceGap;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_MATERIAL_RESOURCE_GAP)
public class MaterialResourceGapServiceImpl implements MaterialResourceGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<MaterialResourceGapBDTO> getMaterialResourcesGap(String codeRRMM) throws ImiException {
		
		logger.info(LogsConstants.LOG_START); 	
		
		List<MaterialResourceGap> materialResourceModels = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<MaterialResourceGap>>() {
					
						@Override
						public List<MaterialResourceGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).getMaterialResourcesGap(codeRRMM);
						}

					});
			

		 return MaterialResourceGapConvert.object2bdto(materialResourceModels);		

	}

	
}