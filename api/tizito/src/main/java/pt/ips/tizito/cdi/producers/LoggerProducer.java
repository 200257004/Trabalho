package pt.ips.tizito.cdi.producers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.log4j.Logger;

public class LoggerProducer {

	private final InjectionPoint point;

	@Inject
	public LoggerProducer(InjectionPoint point) {
		this.point = point;
	}

	@Produces
	public Logger createLogger() {
		return Logger.getLogger(this.point.getMember().getDeclaringClass());
	}

}
