package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.BrandModelGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.BrandModelGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.BrandModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.BrandModelGap;
import es.bcn.vigia.fmw.libutils.convert.BrandModelGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_BRAND_MODEL_GAP)
public class BrandModelGapServiceImpl implements BrandModelGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	

	@Override
	public List<BrandModelGapBDTO> getBrandModelsGap(String codeBrand) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		List<BrandModelGap> brandModels = myBatisTemplateGap.execute(BrandModelGapDao.class,
				new MyBatisDaoCallbackGap<List<BrandModelGap>>() {
				
					@Override
					public List<BrandModelGap> execute(MyBatisDaoGap dao) {
						return ((BrandModelGapDao) dao).getModelsBrandGap(codeBrand);
					}

				});

		return BrandModelGapConvert.object2bdto(brandModels);

	}


	
}