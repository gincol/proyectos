package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.GroupFurnitureGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.GroupFurnitureGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupFurnitureGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.GroupFurnitureGap;
import es.bcn.vigia.fmw.libutils.convert.GroupFurnitureGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_GROUP_FURNITURE_GAP)
public class GroupFurnitureGapServiceImpl implements GroupFurnitureGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<GroupFurnitureGapBDTO> getGroupsFurnitureGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<GroupFurnitureGap> groupsFurniture = myBatisTemplateGap.execute(GroupFurnitureGapDao.class,
				new MyBatisDaoCallbackGap<List<GroupFurnitureGap>>() {
				
					@Override
					public List<GroupFurnitureGap> execute(MyBatisDaoGap dao) {
						return ((GroupFurnitureGapDao) dao).getGroupsFurnitureGap(params);
					}

				});
		

		 return GroupFurnitureGapConvert.object2bdto(groupsFurniture);

	}

}