package com.shev.hibernate_helloworld_annotations;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


public class HelloWorld {
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));//

		// First unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Message message = new Message("Hello World");
		Long msgId = (Long)session.save(message);
		tx.commit();
		session.close();
		
		Session newSession = HibernateUtil.getSessionFactory().openSession();
		Transaction newTx = newSession.beginTransaction();
		List messages = newSession.createQuery("from Message m order by m.text asc").list();
		System.out.println(messages.size() + " message(s) found:");
		
		for (Iterator it = messages.iterator(); it.hasNext(); ) {
			Message msg = (Message)it.next();
			System.out.println(msg.getText());
		}
		
		newTx.commit();
		newSession.close();
		HibernateUtil.shutdown();
	}
}