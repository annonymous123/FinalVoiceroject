package org.raxa.database;

import org.hibernate.Query;
import org.hibernate.Session;

public class test {
	
	public void start(){
		String aid="9ca51430-82ec-48e8-bcb6-f81d10ab454d";
	Session session = HibernateUtil.getSessionFactory().openSession();
	session.beginTransaction();
	String hql="from Alert where aid=:aid and alertType=:alertType";
	Query query=session.createQuery(hql);
	query.setString("aid", aid);
	query.setInteger("alertType", 1);
	Alert alert = (Alert) query.list().get(0);
	int retryCount=alert.getretryCount()+1;
	alert.setretryCount(retryCount);
	session.update(alert);
	session.getTransaction().commit();
	session.close();
	}
	public static void main(String[] args) {
		new test().start();

	}

}
