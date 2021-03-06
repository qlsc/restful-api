package com.tuotuogroup.dao.impl;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.tuotuogroup.core.hibernate.BaseHibernateDao;
import com.tuotuogroup.core.hibernate.Page;
import com.tuotuogroup.core.hibernate.PageRequest;
import com.tuotuogroup.dao.SaleDao;
import com.tuotuogroup.entity.SaleDetail;

@Repository(value = "saleDao")
public class SaleDaoImpl extends BaseHibernateDao<SaleDetail, String> implements
		SaleDao {
	/**
	 * 设置SessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Page<SaleDetail> queryDictionaryByPage(PageRequest pageRequest) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Dictionary.class);
		return findPage(pageRequest, criteria);
	}
	/**
	 * 
	 */
	@Override
	public Map getSaleDetailList(PageRequest pageRequest) {
		String sql = "select * from tb_saledetail where status = '1'";
		Query query = getSession().createSQLQuery(sql).
		setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map map = new HashMap();
		map.put("listSize", query.list().size());
		if (pageRequest != null) {
			query.setFirstResult(pageRequest.getOffset());
			query.setMaxResults(pageRequest.getPageSize());
		}
		map.put("list", query.list());
		return map;
	}
	/**
	 * 
	 */
	@Override
	public List<SaleDetail> queryAllSaleDetailData(String mallname, String searchkeyword) {
		StringBuilder sql = new StringBuilder();
		sql.append("from  SaleDetail ");
		sql.append("where status = '0' and mallname = :mallname ");
		if (searchkeyword != null && !searchkeyword.isEmpty()) {
			sql.append("	and (");
			sql.append("	 source like :source");
			sql.append("	 or title like :title");
			sql.append("	)");
		}
		sql.append(" order by createTime desc");
		Query query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		query.setString("mallname", mallname);
		if (searchkeyword != null && !searchkeyword.isEmpty()) {
			query.setString("source", "%" + searchkeyword + "%")
					.setString("title", "%" + searchkeyword + "%");
		}
		return query.list();
	}
	/**
	 * 
	 */
	@Override
	public SaleDetail getOneSaleDetail() {
		StringBuilder hql = new StringBuilder();
		hql.append("from SaleDetail ");
		hql.append("where status = 0");
		List resultList = sessionFactory.getCurrentSession().createQuery(hql.toString()).setMaxResults(1).list();
		Object result = (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
		return result == null ? null : (SaleDetail) result;
	}
	/**
	 * 
	 */
	@Override
	public SaleDetail getSaleDetailByID(String id) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from SaleDetail ");
		hql.append("where id= :id");
		Object result = sessionFactory.getCurrentSession().createQuery(hql.toString())
				.setString("id", id).uniqueResult();
		return result == null ? null : (SaleDetail) result;
	}
	/**
	 * 
	 */
	@Override
	public void updateSaleDetail(String id, String url) {
		StringBuilder hql = new StringBuilder("");
		hql.append("update SaleDetail set url=:url,status =:status ");
		hql.append("where id =:id");
		sessionFactory.getCurrentSession().createQuery(hql.toString())
			.setString("url", url)
			.setInteger("status", 1)
			.setString("id", id)
			.executeUpdate();
	}
	/**
	 * 
	 */
	@Override
	public void updateErrorSaleUrl(String id) {
		StringBuilder hql = new StringBuilder("");
		hql.append("update SaleDetail set status =:status ");
		hql.append("where id =:id");
		sessionFactory.getCurrentSession().createQuery(hql.toString())
			.setInteger("status", -1)
			.setString("id", id)
			.executeUpdate();
	}


}
