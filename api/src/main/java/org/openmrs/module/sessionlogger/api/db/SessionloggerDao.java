package org.openmrs.module.sessionlogger.api.db;

import java.util.List;

import org.openmrs.module.sessionlogger.SessionLogger;

public interface SessionloggerDao {
	
	List<SessionLogger> getSessionLogs();
	
	SessionLogger getSessionLog(Integer sessionLoggerId);
	
	SessionLogger saveSessionLog(SessionLogger sessionLogger);
	
	void purgeSessionLog(SessionLogger sessionLogger);
}
