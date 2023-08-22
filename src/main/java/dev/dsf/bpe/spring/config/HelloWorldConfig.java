package dev.dsf.bpe.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.bpe.service.HelloWorld;
import dev.dsf.bpe.service.LogUserTaskResponse;
import dev.dsf.bpe.v1.ProcessPluginApi;

@Configuration
public class HelloWorldConfig
{
	@Autowired
	private ProcessPluginApi api;

	@Autowired
	private FhirContext fhirContext;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public HelloWorld helloWorld()
	{
		return new HelloWorld(api);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public LogUserTaskResponse logUserTaskResponse()
	{
		return new LogUserTaskResponse(api, fhirContext);
	}
}
