package dev.dsf.fhir.profile;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

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
import dev.dsf.bpe.ConstantsHelloWorld;
import dev.dsf.bpe.HelloWorldProcessPluginDefinition;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.constants.NamingSystems;
import dev.dsf.fhir.validation.ResourceValidator;
import dev.dsf.fhir.validation.ResourceValidatorImpl;
import dev.dsf.fhir.validation.ValidationSupportRule;

public class TaskProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(TaskProfileTest.class);

	private static final HelloWorldProcessPluginDefinition def = new HelloWorldProcessPluginDefinition();

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(def.getResourceVersion(),
			def.getReleaseDate(),
			Arrays.asList("dsf-task-base-1.0.0.xml", "dsf-task-hello-world.xml", "dsf-task-hello-user.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-bpmn-message-1.0.0.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-bpmn-message-1.0.0.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testTaskHelloWorldValid()
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
		task.getMeta().addProfile(ConstantsHelloWorld.PROFILE_DSF_TASK_TASK_HELLO_WORLD);
		task.setInstantiatesCanonical(
				ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_WORLD_PROCESS_URI + "|" + def.getVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));

		task.addInput().setValue(new StringType(ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_WORLD_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}

	@Test
	public void testTaskHelloUserValid()
	{
		Task task = createValidTaskHelloUser();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskHelloUser()
	{
		Task task = new Task();
		task.getMeta().addProfile(ConstantsHelloWorld.PROFILE_DSF_TASK_TASK_HELLO_USER);
		task.setInstantiatesCanonical(
				ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_USER_PROCESS_URI + "|" + def.getVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));

		task.addInput().setValue(new StringType(ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_USER_MESSAGE_NAME)).getType()
				.addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}
}
