package org.openmrs.module.sessionlogger.web.converted;

import org.openmrs.module.sessionlogger.SessionLogger;

import org.openmrs.api.context.Context;
import org.openmrs.module.sessionlogger.api.SessionloggerService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingConverter;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionloggerConverter extends BaseDelegatingConverter<SessionLogger> {
	
	@Autowired
	SessionloggerService sessionloggerService;
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (representation instanceof DefaultRepresentation) {
			description.addProperty("requestUrl", Representation.REF);
			description.addProperty("requestBody", Representation.REF);
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("requestUrl", Representation.DEFAULT);
			description.addProperty("requestBody", Representation.DEFAULT);
		}
		return description;
	}
	
	@Override
	public SessionLogger newInstance(String s) {
		return new SessionLogger();
	}
	
	@Override
	public SessionLogger getByUniqueId(String s) {
		return null;
	}
	
	@Override
	public SimpleObject asRepresentation(SessionLogger delegate, Representation rep) throws ConversionException {
		SimpleObject simpleObject = new SimpleObject();
		simpleObject.add("requestUrl", delegate.getRequestUrl());
		simpleObject.add("requestBody", delegate.getRequestBody());
		return simpleObject;
	}
}
