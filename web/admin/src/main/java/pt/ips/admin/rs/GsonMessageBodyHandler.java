package pt.ips.admin.rs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GsonMessageBodyHandler implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private static final String UTF_8 = "UTF-8";

	private Gson gson;

	private Gson getGson() {
		if (this.gson == null) {
			this.gson = new GsonBuilder().setPrettyPrinting().create();
		}

		return this.gson;
	}

	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);

		try {
			Type jsonType;

			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}

			return getGson().fromJson(streamReader, jsonType);
		} finally {
			streamReader.close();
		}
	}

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);

		try {
			Type jsonType;

			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}

			getGson().toJson(object, jsonType, writer);
		} finally {
			writer.close();
		}
	}

}
