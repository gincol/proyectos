package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.TypeSubtypeExpenseGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.TypeSubtypeExpenseGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.TypeSubtypeExpenseGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.TypeSubtypeExpenseGap;
import es.bcn.vigia.fmw.libutils.convert.TypeSubtypeExpenseGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_TYPE_SUBTYPE_EXPENSE_GAP)
public class TypeSubtypeExpenseGapServiceImpl implements TypeSubtypeExpenseGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	

	@Override
	public List<TypeSubtypeExpenseGapBDTO> getTypeSubtypesExpenseGap(String codeType) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		List<TypeSubtypeExpenseGap> typeSubtypes = myBatisTemplateGap.execute(TypeSubtypeExpenseGapDao.class,
				new MyBatisDaoCallbackGap<List<TypeSubtypeExpenseGap>>() {
				
					@Override
					public List<TypeSubtypeExpenseGap> execute(MyBatisDaoGap dao) {
						return ((TypeSubtypeExpenseGapDao) dao).getTypeSubtypesExpenseGap(codeType);
					}

				});

		return TypeSubtypeExpenseGapConvert.object2bdto(typeSubtypes);

	}


	
}