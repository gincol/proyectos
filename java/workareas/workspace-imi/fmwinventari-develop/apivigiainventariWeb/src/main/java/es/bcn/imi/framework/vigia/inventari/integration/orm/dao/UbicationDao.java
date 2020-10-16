package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Ubication;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface UbicationDao extends MyBatisDao {
	
	public abstract void insert(Ubication ubication);

	public abstract void update(Ubication ubication);

	public abstract void delete(Ubication ubication);
	
	public abstract Ubication select(Ubication ubication);

	public abstract List<Ubication> selectUbication(Map<String, Object> map);
}
