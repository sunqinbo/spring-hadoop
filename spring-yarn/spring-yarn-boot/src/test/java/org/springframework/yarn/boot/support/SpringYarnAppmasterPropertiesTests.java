/*
 * Copyright 2014 the original author or authors.
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
package org.springframework.yarn.boot.support;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Tests for {@link SpringYarnAppmasterProperties} bindings.
 *
 * @author Janne Valkealahti
 *
 */
public class SpringYarnAppmasterPropertiesTests {

	@Test
	public void testAllPropertiesSet() {
		SpringApplication app = new SpringApplication(TestConfiguration.class);
		ConfigurableApplicationContext context = app
				.run(new String[] { "--spring.config.name=SpringYarnAppmasterPropertiesTests" });
		SpringYarnAppmasterProperties properties = context.getBean(SpringYarnAppmasterProperties.class);
		assertThat(properties, notNullValue());

		assertThat(properties.getAppmasterClass(), is("appmasterClassFoo"));

		Map<String, String> arguments = properties.getArguments();
		assertThat(arguments, notNullValue());
		assertThat(arguments.size(), is(2));
		assertThat(arguments.get("argumentsKeyFoo1"), is("argumentsValFoo1"));
		assertThat(arguments.get("argumentsKeyFoo2"), is("argumentsValFoo2"));

		List<String> classpath = properties.getClasspath();
		assertThat(classpath, notNullValue());
		assertThat(classpath.size(), is(2));
		assertThat(classpath.get(0), is("classpath1Foo"));
		assertThat(classpath.get(1), is("classpath2Foo"));

		assertThat(properties.getContainerCount(), is(123));
		assertThat(properties.getContainerFile(), is("containerFileFoo"));
		assertThat(properties.getContainerRunner(), is("containerRunnerFoo"));

		List<String> options = properties.getOptions();
		assertThat(options, notNullValue());
		assertThat(options.size(), is(2));
		assertThat(options.get(0), is("options1Foo"));
		assertThat(options.get(1), is("options2Foo"));

		assertThat(properties.getMemory(), is("memoryFoo"));
		assertThat(properties.getPriority(), is(234));
		assertThat(properties.getVirtualCores(), is(123));

		assertThat(properties.isWaitLatch(), is(false));
		context.close();
	}

	@Configuration
	@EnableConfigurationProperties({SpringYarnProperties.class, SpringYarnAppmasterProperties.class, SpringYarnEnvProperties.class})
	protected static class TestConfiguration {
	}

}