package org.openmrs.module.sessionlogger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "session_logger")
public class SessionLogger extends BaseOpenmrsObject {
	
	public static final long serialVersionUID = 7654L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_logger_id")
	private Integer sessionLoggerId;
	
	@Column(name = "request_url")
	private String requestUrl;
	
	@Column(name = "request_body", length = 500)
	private String requestBody;
	
	public SessionLogger() {
	}
	
	public Integer getSessionLoggerId() {
		return sessionLoggerId;
	}
	
	public void setSessionLoggerId(Integer sessionLoggerId) {
		this.sessionLoggerId = sessionLoggerId;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public String getRequestBody() {
		return requestBody;
	}
	
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(Integer integer) {
		
	}
}
