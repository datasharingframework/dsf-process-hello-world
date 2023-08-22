package dev.dsf.bpe.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hl7.fhir.r4.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.v1.ProcessPluginApi;
import dev.dsf.bpe.v1.activity.AbstractServiceDelegate;
import dev.dsf.bpe.v1.variables.Variables;

public class HelloWorld extends AbstractServiceDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	public HelloWorld(ProcessPluginApi api)
	{
		super(api);
	}

	@Override
	protected void doExecute(DelegateExecution execution, Variables variables)
	{
		Task task = variables.getStartTask();
		logger.info("Hello World from organization with identifier '{}'",
				task.getRequester().getIdentifier().getValue());
	}
}
