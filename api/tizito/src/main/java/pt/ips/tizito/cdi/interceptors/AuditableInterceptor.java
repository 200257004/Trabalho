package pt.ips.tizito.cdi.interceptors;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import pt.ips.tizito.cdi.annotations.Auditable;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Info;
import pt.ips.tizito.rs.managers.RequestManager;

@Interceptor
@Auditable
@Priority(value = Interceptor.Priority.LIBRARY_BEFORE)
public class AuditableInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private final RequestManager requestManager;

	private final Logger logger;

	@Inject
	public AuditableInterceptor(RequestManager requestManager, Logger logger) {
		this.requestManager = requestManager;
		this.logger = logger;
	}

	@AroundInvoke
	public Object intercept(InvocationContext context) {
		User user = this.requestManager.getUser();
		Calendar calendar = Calendar.getInstance();
		String klass = context.getTarget().getClass().getName();
		String method = context.getMethod().getName();

		this.logger.info(String.format(Info.AUDITABLE_INTERCEPTOR, user.getEmail(), calendar.getTime(), klass, method));

		try {
			return context.proceed();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
