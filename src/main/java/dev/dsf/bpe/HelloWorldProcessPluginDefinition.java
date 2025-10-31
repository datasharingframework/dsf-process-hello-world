package dev.dsf.bpe;

import java.util.List;
import java.util.Map;

import dev.dsf.bpe.spring.config.HelloWorldConfig;
import dev.dsf.bpe.v2.AbstractProcessPluginDefinition;

public class HelloWorldProcessPluginDefinition extends AbstractProcessPluginDefinition
{
	@Override
	public List<String> getProcessModels()
	{
		return List.of("bpe/hello-world.bpmn", "bpe/hello-user.bpmn");
	}

	@Override
	public List<Class<?>> getSpringConfigurations()
	{
		return List.of(HelloWorldConfig.class);
	}

	@Override
	public Map<String, List<String>> getFhirResourcesByProcessId()
	{
		var aHelloUser = "fhir/ActivityDefinition/dsf-hello-user.xml";
		var aHelloWorld = "fhir/ActivityDefinition/dsf-hello-world.xml";

		var cHelloWorld = "fhir/CodeSystem/dsf-hello-world.xml";

		var qHelloWorld = "fhir/Questionnaire/dsf-hello-user.xml";

		var sHelloUser = "fhir/StructureDefinition/dsf-task-hello-user.xml";
		var sHelloWorld = "fhir/StructureDefinition/dsf-task-hello-world.xml";

		var tHelloUser = "fhir/Task/dsf-task-hello-user.xml";
		var tHelloWorld = "fhir/Task/dsf-task-hello-world.xml";

		var vHelloWorld = "fhir/ValueSet/dsf-hello-world.xml";

		return Map.of("dsfdev_helloUser", List.of(aHelloUser, qHelloWorld, sHelloUser, tHelloUser), "dsfdev_helloWorld",
				List.of(aHelloWorld, cHelloWorld, sHelloWorld, tHelloWorld, vHelloWorld));
	}
}
