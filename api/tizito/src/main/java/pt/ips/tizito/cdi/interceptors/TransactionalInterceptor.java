package pt.ips.tizito.cdi.interceptors;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import pt.ips.tizito.cdi.annotations.Transactional;

@Interceptor
@Transactional
@Priority(value = Interceptor.Priority.APPLICATION)
public class TransactionalInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager entityManager;

	@Inject
	public TransactionalInterceptor(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@AroundInvoke
	public Object intercept(InvocationContext context) {
		this.entityManager.getTransaction().begin();

		try {
			Object result = context.proceed();

			this.entityManager.getTransaction().commit();

			return result;
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();

			throw new RuntimeException(e);
		}
	}

}
