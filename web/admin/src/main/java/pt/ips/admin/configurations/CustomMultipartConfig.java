package pt.ips.admin.configurations;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import br.com.caelum.vraptor.observer.upload.DefaultMultipartConfig;

@Specializes
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

	@Override
	public long getFileSizeLimit() {
		return 4 * 1024 * 1024;
	}

	@Override
	public long getSizeLimit() {
		return 4 * 1024 * 1024;
	}

}
