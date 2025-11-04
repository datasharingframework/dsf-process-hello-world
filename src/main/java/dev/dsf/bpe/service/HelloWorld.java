package dev.dsf.bpe.service;

import org.hl7.fhir.r4.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.v2.ProcessPluginApi;
import dev.dsf.bpe.v2.activity.ServiceTask;
import dev.dsf.bpe.v2.error.ErrorBoundaryEvent;
import dev.dsf.bpe.v2.variables.Variables;

public class HelloWorld implements ServiceTask
{
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	@Override
	public void execute(ProcessPluginApi api, Variables variables) throws ErrorBoundaryEvent, Exception
	{
		Task task = variables.getStartTask();
		logger.info("Hello World from organization with identifier '{}'",
				task.getRequester().getIdentifier().getValue());
	}
}
