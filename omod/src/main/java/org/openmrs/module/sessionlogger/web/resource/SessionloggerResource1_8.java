package org.openmrs.module.sessionlogger.web.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.sessionlogger.SessionLogger;
import org.openmrs.module.sessionlogger.api.SessionloggerService;
import org.openmrs.module.sessionlogger.api.impl.SessionloggerServiceImpl;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;

@Resource(name = RestConstants.VERSION_1 + "/sessionlogger/logs", supportedClass = SessionLogger.class, supportedOpenmrsVersions = {
        "2.4.*", "2.5.*", "2.6.*" })
public class SessionloggerResource1_8 extends DelegatingCrudResource<SessionLogger> { //BaseOpenmrsObject

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("requestUrl");
			description.addProperty("requestBody");
			return description;
		}
		return null;
	}
	
	@Override
	public Model getGETModel(Representation rep) {
		return ((ModelImpl) super.getGETModel(rep)).property("uuid", new StringProperty())
		        .property("requestUrl", new StringProperty()).property("requestBody", new StringProperty());
	}
	
	@Override
	public Model getCREATEModel(Representation rep) {
		return new ModelImpl().property("requestUrl", new StringProperty()).property("requestBody", new StringProperty());
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("display");
		description.addProperty("requestUrl");
		description.addProperty("requestBody");
		return description;
	}
	
	@Override
	protected NeedsPaging<SessionLogger> doGetAll(RequestContext context) throws ResponseException {
		//		return new NeedsPaging<SessionLogger>(Context.getService(SessionloggerService.class).getSessionLogs(), context);
		return doGetAll(context, Context.getService(SessionloggerService.class).getSessionLogs());
	}
	
	protected NeedsPaging<SessionLogger> doGetAll(RequestContext context, List<SessionLogger> sessionLoggers) {
		if (context.getIncludeAll()) {
			return new NeedsPaging<SessionLogger>(sessionLoggers, context);
		}
		List<SessionLogger> unretiredSessions = new ArrayList<SessionLogger>();
		for (SessionLogger sessionLogger : sessionLoggers) {
			unretiredSessions.add(sessionLogger);
		}
		return new NeedsPaging<SessionLogger>(unretiredSessions, context);
	}
	
	@Override
	protected NeedsPaging doSearch(RequestContext context) {
		return doSearch(context, Context.getService(SessionloggerService.class).getSessionLogs());
	}
	
	protected NeedsPaging doSearch(RequestContext context, List<SessionLogger> sessionLoggers) {
		for (Iterator<SessionLogger> iterator = sessionLoggers.iterator(); iterator.hasNext();) {
			SessionLogger logger = iterator.next();
			if (!Pattern.compile(Pattern.quote(context.getParameter("q")), Pattern.CASE_INSENSITIVE)
			        .matcher(logger.getRequestUrl()).find()
			        || (!context.getIncludeAll())) {
				iterator.remove();
			}
		}
		return new NeedsPaging<SessionLogger>(sessionLoggers, context);
	}
	
	@Override
	public SessionLogger getByUniqueId(String uuid) {
		SessionLogger logger = Context.getService(SessionloggerService.class).getItemByUuid(uuid);
		if (logger != null)
			return logger;
		return null;
	}
	
	@Override
	protected void delete(SessionLogger sessionLogger, String s, RequestContext requestContext) throws ResponseException {
		
	}
	
	@Override
	public SessionLogger newDelegate() {
		return new SessionLogger();
	}
	
	@Override
	public SessionLogger save(SessionLogger sessionLogger) {
		return Context.getService(SessionloggerService.class).saveSessionLog(sessionLogger);
	}
	
	@Override
	public void purge(SessionLogger sessionLogger, RequestContext requestContext) throws ResponseException {
		if (sessionLogger == null)
			return;
		Context.getService(SessionloggerService.class).purgeSessionLog(sessionLogger);
	}
}
