/*
 * Copyright (c) 2010 Eugene Prokopiev <enp@itx.ru>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.itx.service;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceApp implements Daemon {

	private AbstractApplicationContext context;
	private String[] contextFiles;

	public ServiceApp() {
		this.contextFiles = new String []{"context.xml"};
	}

	public ServiceApp(String[] contextFiles) {
		if (contextFiles.length > 0)
			this.contextFiles = contextFiles;
		else
		this.contextFiles = new String []{"context.xml"};
	}

	public void init(DaemonContext context) {}

	public void destroy() {}

	public void start() {
		context = new ClassPathXmlApplicationContext(contextFiles);
	}

	public void stop() {
		context.close();
	}

	public static void main(String[] args) throws Exception {
		final ServiceApp serviceApp = new ServiceApp();
		serviceApp.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				serviceApp.stop();
			}
		});
		Thread.sleep(Long.MAX_VALUE);
	}
}
