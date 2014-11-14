package org.mondo.eu.utils;
/*******************************************************************************
 * Copyright (c) 2010-2014, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Gabor Szarnyas - initial API and implementation
 *******************************************************************************/


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.io.IOUtils;

public class UnixUtils {

	public static String createTempFileFromScript(final String script) throws IOException, FileNotFoundException {
		return createTempFileFromResource(script, ".sh", true);
	}

	public static String createTempFileFromResource(final String script, final String extension,
			final boolean executable) throws IOException, FileNotFoundException {
		final InputStream scriptInputStream = UnixUtils.class.getResourceAsStream("/" + script);

		// create a temporary file
		final File scriptTempFile = File.createTempFile("unix-utils-", extension);
		scriptTempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(scriptTempFile)) {
			IOUtils.copy(scriptInputStream, out);
		}
		scriptTempFile.setExecutable(executable);

		final String command = scriptTempFile.getAbsolutePath();
		return command;
	}

	public static void execResourceScript(final String command, final Map<String, String> environmentVariables,
			final boolean showOutput) throws FileNotFoundException, IOException {
		execResourceScript(command, "", environmentVariables, showOutput);
	}

	public static void execResourceScript(final String command, final String arguments,
			final Map<String, String> environmentVariables, final boolean showOutput) throws FileNotFoundException,
			IOException {
		String tempScript = UnixUtils.createTempFileFromScript(command);

		String scriptCommand = tempScript + " " + arguments;
		BufferedReader reader = exec(scriptCommand, environmentVariables);
		
		if (showOutput) {
			System.out.println("Command: " + scriptCommand);
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static BufferedReader exec(final String command, final Map<String, String> environmentVariables) throws FileNotFoundException, IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		return exec(command, environmentVariables, stream);
	}

	private static BufferedReader exec(final String command, final Map<String, String> environmentVariables,
			ByteArrayOutputStream byteArrayOutputStream) throws IOException, ExecuteException {
		final Map<?, ?> executionEnvironment = EnvironmentUtils.getProcEnvironment();
		for (Entry<String, String> environmentVariable : environmentVariables.entrySet()) {
			String keyAndValue = environmentVariable.getKey() + "=" + environmentVariable.getValue();
			EnvironmentUtils.addVariableToEnvironment(executionEnvironment, keyAndValue);
		}

		PumpStreamHandler streamHandler = new PumpStreamHandler(byteArrayOutputStream);

		CommandLine commandLine = new CommandLine("/bin/bash");
		commandLine.addArguments(new String[] { "-c", command }, false);
		
		DefaultExecutor executor = new DefaultExecutor();
		executor.setStreamHandler(streamHandler);
		executor.execute(commandLine, executionEnvironment);
		
		return toBufferedReader(byteArrayOutputStream);
	}
	
	public static BufferedReader toBufferedReader(ByteArrayOutputStream byteArrayOutputStream) {
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
	}
}
