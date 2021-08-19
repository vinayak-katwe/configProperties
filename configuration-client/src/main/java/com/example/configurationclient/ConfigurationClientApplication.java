/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.configurationclient;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigurationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationClientApplication.class, args);
	}
}

@RefreshScope
@RestController
class MessageRestController {

	@Value("${message:Hello default}")
	private String message;

	@Autowired
	private Environment env;

	@Autowired
	private ConfigurableEnvironment environment;

	@Autowired
	private PropertyConfiguration propConfigObj;

	@RequestMapping("/message")
	public String getMessage() {
		return this.message;
	}

	@RequestMapping("/prop")
	public String getProp() {
		System.out.println();
		if (this.propConfigObj != null) {
			return this.propConfigObj.getProperty();
		} else {
			return "propConfigObj is empty";
		}

	}

	@PostConstruct
	private void getProfiles() {

		System.out.println("-----------getActiveProfiles PROFILES-----");
		for (String profile : env.getActiveProfiles()) {
			System.out.println(profile);
		}
		System.out.println("-----------getDefaultProfiles PROFILES-----");
		for (String profile : env.getDefaultProfiles()) {
			System.out.println(profile);
		}

		System.out.println("-----------getActiveProfiles PROFILES-----");
		for (String profile : environment.getActiveProfiles()) {
			System.out.println(profile);
		}

		// return this.propConfigObj.getProperty()+" current profile
		// is-"+env.getActiveProfiles()+" default profiles -"+env.getDefaultProfiles();
	}

}
