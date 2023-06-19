package dev.dsf.bpe;

public interface ConstantsHelloWorld
{
	String PROCESS_NAME_HELLO_WORLD = "helloWorld";
	String PROCESS_NAME_FULL_HELLO_WORLD = "dsfdev_" + PROCESS_NAME_HELLO_WORLD;

	String PROCESS_NAME_HELLO_USER = "helloUser";
	String PROCESS_NAME_FULL_HELLO_USER = "dsfdev_" + PROCESS_NAME_HELLO_USER;

	String PROCESS_DSF_URI_BASE = "http://dsf.dev/bpe/Process/";

	String PROFILE_DSF_TASK_TASK_HELLO_WORLD = "http://dsf.dev/fhir/StructureDefinition/task-hello-world";
	String PROFILE_DSF_TASK_HELLO_WORLD_PROCESS_URI = PROCESS_DSF_URI_BASE + PROCESS_NAME_HELLO_WORLD;
	String PROFILE_DSF_TASK_HELLO_WORLD_MESSAGE_NAME = "helloWorld";

	String PROFILE_DSF_TASK_TASK_HELLO_USER = "http://dsf.dev/fhir/StructureDefinition/task-hello-user";
	String PROFILE_DSF_TASK_HELLO_USER_PROCESS_URI = PROCESS_DSF_URI_BASE + PROCESS_NAME_HELLO_USER;
	String PROFILE_DSF_TASK_HELLO_USER_MESSAGE_NAME = "helloUser";
}
