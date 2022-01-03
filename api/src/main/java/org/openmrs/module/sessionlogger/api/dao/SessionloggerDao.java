/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.sessionlogger.api.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.sessionlogger.Item;
import org.openmrs.module.sessionlogger.SessionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("sessionlogger.SessionloggerDao")
public class SessionloggerDao implements org.openmrs.module.sessionlogger.api.db.SessionloggerDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public SessionLogger getItemByUuid(String uuid) {
		return (SessionLogger) getSession().createCriteria(SessionLogger.class).add(Restrictions.eq("uuid", uuid))
		        .uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	@Override
	public List<SessionLogger> getSessionLogs() {
		return sessionFactory.getCurrentSession().createCriteria(SessionLogger.class).list();
	}
	
	@Override
	public SessionLogger getSessionLog(Integer sessionLoggerId) {
		return (SessionLogger) sessionFactory.getCurrentSession().get(SessionLogger.class, sessionLoggerId);
	}
	
	@Override
	public SessionLogger saveSessionLog(SessionLogger sessionLogger) {
		sessionFactory.getCurrentSession().save(sessionLogger);
		return sessionLogger;
	}
	
	@Override
	public void purgeSessionLog(SessionLogger sessionLogger) {
		sessionFactory.getCurrentSession().delete(sessionLogger);
	}
}
