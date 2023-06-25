package dev.dsf.fhir.profile;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.Task.TaskIntent;
import org.hl7.fhir.r4.model.Task.TaskStatus;
import org.hl7.fhir.r4.model.TimeType;
import org.hl7.fhir.r4.model.UriType;
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
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-bpmn-message-1.0.0.xml", "dsf-hello-world.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-bpmn-message-1.0.0.xml", "dsf-hello-world.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testMinimalTaskHelloWorldValid()
	{
		Task task = createValidTaskHelloWorld();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	@Test
	public void testMaximalTaskHelloWorldValid()
	{
		Task task = createValidTaskHelloWorld();

		task.addInput().setValue(new StringType("string-value")).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("string-example"));
		task.addInput().setValue(new IntegerType(1)).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("integer-example"));
		task.addInput().setValue(new DecimalType(1.1)).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("decimal-example"));
		task.addInput().setValue(new BooleanType(true)).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("boolean-example"));
		task.addInput().setValue(new DateType(new Date())).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("date-example"));
		task.addInput().setValue(new TimeType("10:00:00")).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("time-example"));
		task.addInput().setValue(new DateTimeType(new Date())).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("date-time-example"));
		task.addInput().setValue(new InstantType(new Date())).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("instant-example"));
		task.addInput().setValue(new UriType("http://dsf.dev/fhir/CodeSystem/hello-world")).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("uri-example"));
		task.addInput().setValue(new Reference("http://hl7.org/fhir/Patient/example-id")).getType().addCoding(
				new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("reference-example"));
		task.addInput()
				.setValue(new Reference().setType("Patient")
						.setIdentifier(new Identifier().setSystem("http://dsf.dev/sid/hello-world-identifier")
								.setValue("identifier-value")))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("reference-identifier-example"));
		task.addInput()
				.setValue(new Identifier().setSystem("http://dsf.dev/sid/hello-world-identifier")
						.setValue("identifier-value"))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("identifier-example"));
		task.addInput()
				.setValue(
						new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("coding-example"))
				.getType().addCoding(
						new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("coding-example"));

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
