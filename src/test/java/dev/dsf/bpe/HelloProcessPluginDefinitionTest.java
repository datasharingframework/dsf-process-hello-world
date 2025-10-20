package dev.dsf.bpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import dev.dsf.bpe.v2.ProcessPluginDefinition;

public class HelloProcessPluginDefinitionTest
{
	@Test
	public void testResourceLoading()
	{
		ProcessPluginDefinition definition = new HelloWorldProcessPluginDefinition();
		Map<String, List<String>> resourcesByProcessId = definition.getFhirResourcesByProcessId();

		var helloWorld = resourcesByProcessId.get("dsfdev_helloWorld");
		assertNotNull(helloWorld);
		assertEquals(5, helloWorld.stream().filter(this::exists).count());

		var helloUser = resourcesByProcessId.get("dsfdev_helloUser");
		assertNotNull(helloUser);
		assertEquals(4, helloUser.stream().filter(this::exists).count());
	}

	private boolean exists(String file)
	{
		return getClass().getClassLoader().getResourceAsStream(file) != null;
	}
}
