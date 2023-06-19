package dev.dsf.bpe.start;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.bpe.ConstantsHelloWorld;
import dev.dsf.bpe.HelloWorldProcessPluginDefinition;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.constants.NamingSystems;
import dev.dsf.fhir.client.FhirWebserviceClient;

public class HelloWorldExampleStarter
{
	// Environment variable "DSF_CLIENT_CERTIFICATE_PATH" or args[0]: the path to the client-certificate
	// dsf/dsf-tools/dsf-tools-test-data-generator/cert/Webbrowser_Test_User/Webbrowser_Test_User_certificate.p12
	// Environment variable "DSF_CLIENT_CERTIFICATE_PASSWORD" or args[1]: the password of the client-certificate
	// password
	public static void main(String[] args) throws Exception
	{
		ExampleStarter.forServer(args, ConstantsExampleStarters.DIC_1_FHIR_BASE_URL).startWith(task());
	}

	private static Task task()
	{
		var def = new HelloWorldProcessPluginDefinition();

		Task task = new Task();
		task.getMeta().addProfile(ConstantsHelloWorld.PROFILE_DSF_TASK_TASK_HELLO_WORLD);
		task.setInstantiatesCanonical(
				ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_WORLD_PROCESS_URI + "|" + def.getResourceVersion());
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).setIdentifier(NamingSystems.OrganizationIdentifier
				.withValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_DIC_1));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier
						.withValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_DIC_1));

		task.addInput().setValue(new StringType(ConstantsHelloWorld.PROFILE_DSF_TASK_HELLO_WORLD_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}
}
