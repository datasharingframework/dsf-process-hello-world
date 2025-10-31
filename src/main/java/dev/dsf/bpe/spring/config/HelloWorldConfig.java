package dev.dsf.bpe.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.dsf.bpe.listener.ProcessPluginDeploymentListenerImpl;
import dev.dsf.bpe.service.HelloWorld;
import dev.dsf.bpe.service.LogUserTaskResponse;
import dev.dsf.bpe.v2.ProcessPluginDeploymentListener;
import dev.dsf.bpe.v2.spring.ActivityPrototypeBeanCreator;

@Configuration
public class HelloWorldConfig
{
	@Bean
	public static ActivityPrototypeBeanCreator activityPrototypeBeanCreator()
	{
		return new ActivityPrototypeBeanCreator(HelloWorld.class, LogUserTaskResponse.class);
	}

	@Bean
	public ProcessPluginDeploymentListener deploymentListener()
	{
		return new ProcessPluginDeploymentListenerImpl();
	}
}
