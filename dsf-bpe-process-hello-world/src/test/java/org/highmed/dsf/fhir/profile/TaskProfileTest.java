package org.highmed.dsf.fhir.profile;

import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsBase.ORGANIZATION_IDENTIFIER_SYSTEM;
import static org.highmed.dsf.bpe.ConstantsHelloWorld.HELLO_WORLD_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsHelloWorld.HELLO_WORLD_PROCESS_URI_AND_LATEST_VERSION;
import static org.highmed.dsf.bpe.ConstantsHelloWorld.HELLO_WORLD_TASK_PROFILE;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.highmed.dsf.fhir.validation.ResourceValidator;
import org.highmed.dsf.fhir.validation.ResourceValidatorImpl;
import org.highmed.dsf.fhir.validation.ValidationSupportRule;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.Task.TaskIntent;
import org.hl7.fhir.r4.model.Task.TaskStatus;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;

public class TaskProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(TaskProfileTest.class);

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(
			Arrays.asList("highmed-task-base-0.4.0.xml", "highmed-task-hello-world.xml"),
			Arrays.asList("authorization-role-0.4.0.xml", "bpmn-message-0.4.0.xml"),
			Arrays.asList("authorization-role-0.4.0.xml", "bpmn-message-0.4.0.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testTaskHelloWorldValid() throws Exception
	{
		Task task = createValidTaskHelloWorld();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskHelloWorld()
	{
		Task task = new Task();
		task.getMeta().addProfile(HELLO_WORLD_TASK_PROFILE);
		task.setInstantiatesUri(HELLO_WORLD_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ORGANIZATION_IDENTIFIER_SYSTEM).setValue("MeDIC 1");
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ORGANIZATION_IDENTIFIER_SYSTEM).setValue("MeDIC 1");

		task.addInput().setValue(new StringType(HELLO_WORLD_MESSAGE_NAME)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_BPMN).setCode(CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME);

		return task;
	}
}
