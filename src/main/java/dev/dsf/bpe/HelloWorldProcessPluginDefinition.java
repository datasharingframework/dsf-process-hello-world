package dev.dsf.bpe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.dsf.bpe.spring.config.HelloWorldConfig;
import dev.dsf.bpe.v1.ProcessPluginDefinition;

public class HelloWorldProcessPluginDefinition implements ProcessPluginDefinition
{
	public static final String VERSION = "1.0.0.0";
	public static final LocalDate RELEASE_DATE = LocalDate.of(2023, 8, 22);

	@Override
	public String getName()
	{
		return "dsf-process-hello-world";
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	@Override
	public LocalDate getReleaseDate()
	{
		return RELEASE_DATE;
	}

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

		return Map.of(ConstantsHelloWorld.PROCESS_NAME_FULL_HELLO_USER,
				Arrays.asList(aHelloUser, qHelloWorld, sHelloUser, tHelloUser),
				ConstantsHelloWorld.PROCESS_NAME_FULL_HELLO_WORLD,
				Arrays.asList(aHelloWorld, cHelloWorld, sHelloWorld, tHelloWorld, vHelloWorld));
	}
}
