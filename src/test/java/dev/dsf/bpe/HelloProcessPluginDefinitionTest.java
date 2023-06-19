package dev.dsf.bpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import dev.dsf.bpe.v1.ProcessPluginDefinition;

public class HelloProcessPluginDefinitionTest
{
	@Test
	public void testResourceLoading()
	{
		ProcessPluginDefinition definition = new HelloWorldProcessPluginDefinition();
		Map<String, List<String>> resourcesByProcessId = definition.getFhirResourcesByProcessId();

		var helloWorld = resourcesByProcessId.get(ConstantsHelloWorld.PROCESS_NAME_FULL_HELLO_WORLD);
		assertNotNull(helloWorld);
		assertEquals(3, helloWorld.stream().filter(this::exists).count());

		var helloUser = resourcesByProcessId.get(ConstantsHelloWorld.PROCESS_NAME_FULL_HELLO_USER);
		assertNotNull(helloUser);
		assertEquals(4, helloUser.stream().filter(this::exists).count());
	}

	private boolean exists(String file)
	{
		return getClass().getClassLoader().getResourceAsStream(file) != null;
	}
}
