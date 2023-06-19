package dev.dsf.fhir.profile;

import static dev.dsf.bpe.HelloWorldProcessPluginDefinition.RELEASE_DATE;
import static dev.dsf.bpe.HelloWorldProcessPluginDefinition.VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;

import org.hl7.fhir.r4.model.ActivityDefinition;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;
import dev.dsf.bpe.HelloWorldProcessPluginDefinition;
import dev.dsf.fhir.authorization.process.ProcessAuthorizationHelper;
import dev.dsf.fhir.authorization.process.ProcessAuthorizationHelperImpl;
import dev.dsf.fhir.validation.ResourceValidator;
import dev.dsf.fhir.validation.ResourceValidatorImpl;
import dev.dsf.fhir.validation.ValidationSupportRule;

public class ActivityDefinitionProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(ActivityDefinitionProfileTest.class);

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(
			HelloWorldProcessPluginDefinition.VERSION, HelloWorldProcessPluginDefinition.RELEASE_DATE,
			Arrays.asList("dsf-activity-definition-1.0.0.xml", "dsf-extension-process-authorization-1.0.0.xml",
					"dsf-extension-process-authorization-consortium-role-1.0.0.xml",
					"dsf-extension-process-authorization-organization-1.0.0.xml",
					"dsf-coding-process-authorization-local-all-1.0.0.xml",
					"dsf-coding-process-authorization-local-consortium-role-1.0.0.xml",
					"dsf-coding-process-authorization-local-organization-1.0.0.xml",
					"dsf-coding-process-authorization-remote-all-1.0.0.xml",
					"dsf-coding-process-authorization-remote-consortium-role-1.0.0.xml",
					"dsf-coding-process-authorization-remote-organization-1.0.0.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-process-authorization-1.0.0.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-process-authorization-recipient-1.0.0.xml",
					"dsf-process-authorization-requester-1.0.0.xml"));

	private final ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	private final ProcessAuthorizationHelper processAuthorizationHelper = new ProcessAuthorizationHelperImpl();

	@Test
	public void testHelloWorldValid() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/dsf-hello-world.xml"));

		ValidationResult result = resourceValidator.validate(ad);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());

		assertTrue(processAuthorizationHelper.isValid(ad, taskProfile -> true, orgIdentifier -> true, role -> true));
	}

	@Test
	public void testHelloUserValid() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/dsf-hello-user.xml"));

		ValidationResult result = resourceValidator.validate(ad);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());

		assertTrue(processAuthorizationHelper.isValid(ad, taskProfile -> true, orgIdentifier -> true, role -> true));
	}
}
