package molab.main.java.service;

import java.sql.SQLException;

import molab.main.java.dao.LogcatDao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

@Service
public class LogcatService {

	@Autowired
	private LogcatDao dao;
	
	public int count(final int dispatcherId, final int type) {
		final String sql = "select count(*) from Logcat where runnerId in (select id from CtRunner where dispatcherId=?) and type=?";
		Object object = dao.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(sql).setParameter(0, dispatcherId).setParameter(1, type).uniqueResult();
			}
			
		});
		if(object != null) {
			return (int) (long) object;
		}
		return 0;
	}
	
}
