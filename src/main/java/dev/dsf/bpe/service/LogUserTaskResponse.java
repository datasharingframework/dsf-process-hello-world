package dev.dsf.bpe.service;

import java.util.Objects;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hl7.fhir.r4.model.QuestionnaireResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.bpe.v1.ProcessPluginApi;
import dev.dsf.bpe.v1.activity.AbstractServiceDelegate;
import dev.dsf.bpe.v1.variables.Variables;

public class LogUserTaskResponse extends AbstractServiceDelegate implements InitializingBean
{
	private static final Logger logger = LoggerFactory.getLogger(LogUserTaskResponse.class);

	private final FhirContext fhirContext;

	public LogUserTaskResponse(ProcessPluginApi api, FhirContext fhirContext)
	{
		super(api);
		this.fhirContext = fhirContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Objects.requireNonNull(fhirContext, "fhirContext");
	}

	@Override
	protected void doExecute(DelegateExecution execution, Variables variables)
	{
		QuestionnaireResponse questionnaireResponse = variables.getLatestReceivedQuestionnaireResponse();

		logger.info("Completed QuestionnaireResponse: {}",
				fhirContext.newXmlParser().setPrettyPrint(true).encodeResourceToString(questionnaireResponse));
	}
}
