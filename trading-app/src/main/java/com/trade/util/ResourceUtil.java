package com.trade.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

/**
 * Loads Resource File into memory
 * @author Sasi Rekha
 *
 */

@Component
public class ResourceUtil {
	
	public String getResourceAsString(final String resource) {
		ClassPathResource classPathResource = new ClassPathResource(resource);
		try (InputStream stream = classPathResource.getURL().openStream()) {
			return StreamUtils.copyToString(stream, Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Resource %s not available", resource), e);
		}
	}

}
