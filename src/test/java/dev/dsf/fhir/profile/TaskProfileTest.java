package dev.dsf.fhir.profile;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.Quantity;
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
import dev.dsf.bpe.HelloWorldProcessPluginDefinition;
import dev.dsf.bpe.v2.constants.CodeSystems;
import dev.dsf.bpe.v2.constants.NamingSystems;
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
			Arrays.asList("dsf-task-2.0.0.xml", "dsf-task-hello-world.xml", "dsf-task-hello-user.xml"),
			Arrays.asList("dsf-read-access-tag-2.0.0.xml", "dsf-bpmn-message-2.0.0.xml", "dsf-hello-world.xml"),
			Arrays.asList("dsf-read-access-tag-2.0.0.xml", "dsf-bpmn-message-2.0.0.xml", "dsf-hello-world.xml"));

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

		task.addInput().setValue(new DecimalType(1.1)).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("decimal-example").setVersion(def.getResourceVersion()));
		task.addInput().setValue(new BooleanType(true)).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("boolean-example").setVersion(def.getResourceVersion()));
		task.addInput().setValue(new DateType(new Date())).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("date-example")
						.setVersion(def.getResourceVersion()));
		task.addInput().setValue(new TimeType("10:00:00")).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("time-example")
						.setVersion(def.getResourceVersion()));
		task.addInput().setValue(new DateTimeType(new Date())).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("date-time-example").setVersion(def.getResourceVersion()));
		task.addInput().setValue(new InstantType(new Date())).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("instant-example").setVersion(def.getResourceVersion()));
		task.addInput().setValue(new UriType("http://dsf.dev/fhir/CodeSystem/hello-world")).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("uri-example")
						.setVersion(def.getResourceVersion()));
		task.addInput().setValue(new Reference("http://hl7.org/fhir/Patient/example-id")).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("reference-example").setVersion(def.getResourceVersion()));
		task.addInput()
				.setValue(new Reference().setType("Patient")
						.setIdentifier(new Identifier().setSystem("http://dsf.dev/sid/hello-world-identifier")
								.setValue("identifier-value")))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("reference-identifier-example").setVersion(def.getResourceVersion()));
		task.addInput()
				.setValue(new Identifier().setSystem("http://dsf.dev/sid/hello-world-identifier")
						.setValue("identifier-value"))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("identifier-example").setVersion(def.getResourceVersion()));
		task.addInput()
				.setValue(
						new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world").setCode("coding-example"))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("coding-example").setVersion(def.getResourceVersion()));
		task.addInput()
				.setValue(new Quantity().setSystem("http://unitsofmeasure.org").setCode("m").setUnit("m").setValue(1)
						.setComparator(Quantity.QuantityComparator.LESS_OR_EQUAL))
				.getType().addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("quantity-example").setVersion(def.getResourceVersion()));

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskHelloWorld()
	{
		Task task = new Task();
		task.getMeta().addProfile("http://dsf.dev/fhir/StructureDefinition/task-hello-world");
		task.setInstantiatesCanonical("http://dsf.dev/bpe/Process/helloWorld|" + def.getResourceVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));

		task.addInput().setValue(new StringType("helloWorld")).getType()
				.addCoding(CodeSystems.BpmnMessage.messageName());

		task.addInput().setValue(new StringType("string-value")).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("string-example").setVersion(def.getResourceVersion()));
		task.addInput().setValue(new IntegerType(1)).getType()
				.addCoding(new Coding().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
						.setCode("integer-example").setVersion(def.getResourceVersion()));

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

	@Test
	public void testTaskHelloUserWithQuestionnaireResponseReferenceOutputValid()
	{
		Task task = createValidTaskHelloUser();
		task.setStatus(TaskStatus.INPROGRESS);
		task.addInput().setValue(new StringType(UUID.randomUUID().toString())).getType().getCodingFirstRep()
				.setSystem("http://dsf.dev/fhir/CodeSystem/bpmn-message").setCode("business-key");
		task.addOutput().setValue(new Reference("QuestionnaireResponse/" + UUID.randomUUID().toString())).getType()
				.getCodingFirstRep().setSystem("http://dsf.dev/fhir/CodeSystem/hello-world")
				.setCode("questionnaire-response-reference").setVersion(def.getResourceVersion());

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskHelloUser()
	{
		Task task = new Task();
		task.getMeta().addProfile("http://dsf.dev/fhir/StructureDefinition/task-hello-user");
		task.setInstantiatesCanonical("http://dsf.dev/bpe/Process/helloUser|" + def.getVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("Test_DIC_1"));

		task.addInput().setValue(new StringType("helloUser")).getType()
				.addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}

	@Test
	public void testHelloUserDraftTask() throws Exception
	{
		testDraftTask(validationRule.readTask(Paths.get("src/main/resources/fhir/Task/dsf-task-hello-user.xml")));
	}

	@Test
	public void testHelloWorldDraftTask() throws Exception
	{
		testDraftTask(validationRule.readTask(Paths.get("src/main/resources/fhir/Task/dsf-task-hello-world.xml")));
	}

	private void testDraftTask(Task task)
	{
		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}
}
