package org.mondo.eu.utils.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.junit.Test;
import org.mondo.eu.utils.UnixUtils;

public class UnixUtilsTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		UnixUtils.exec("ls /", Collections.<String, String> emptyMap(), System.out);
	}

	@Test(expected = ExecuteException.class)
	public void testFail() throws FileNotFoundException, IOException {
		UnixUtils.exec("ls :", Collections.<String, String> emptyMap(), System.out);
	}

	@Test
	public void testEnvironmentVariables() throws IOException {
		final Map<?, ?> executionEnvironment = EnvironmentUtils.getProcEnvironment();
		EnvironmentUtils.addVariableToEnvironment(executionEnvironment, "HELLO=world");

		CommandLine commandLine = new CommandLine("/bin/bash");
		commandLine.addArguments(new String[] { "-c", "echo $HELLO" }, false);
		new DefaultExecutor().execute(commandLine);
	}

}
