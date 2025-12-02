package dev.dsf.bpe.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.v2.ProcessPluginDeploymentListener;

public class ProcessPluginDeploymentListenerImpl implements ProcessPluginDeploymentListener
{
	private static final Logger logger = LoggerFactory.getLogger(ProcessPluginDeploymentListenerImpl.class);

	@Override
	public void onProcessesDeployed(List<String> processes)
	{
		logger.info("Deployed HelloWorld Processes: {}", processes);
	}
}
