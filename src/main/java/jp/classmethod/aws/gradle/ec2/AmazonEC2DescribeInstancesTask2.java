/*
 * Copyright 2013-2016 Classmethod, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.classmethod.aws.gradle.ec2;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.gradle.api.GradleException;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.TaskAction;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;


public class AmazonEC2DescribeInstancesTask2 extends ConventionTask {
	
	@Getter @Setter
	private String ami;
	
	@Getter @Setter
	private String keyName;
	
	@Getter @Setter
	private String instanceId;
	
	@Getter
	private DescribeInstancesResult describeInstancesResult;

	public AmazonEC2DescribeInstancesTask2() {
		setDescription("Start EC2 instance.");
		setGroup("AWS");
	}
	
	@TaskAction
	public void runInstance() {
		// to enable conventionMappings feature
		String ami = getAmi();
		String keyName = getKeyName();

		if (ami == null) throw new GradleException("AMI ID is required");
		
		AmazonEC2PluginExtension ext = getProject().getExtensions().getByType(AmazonEC2PluginExtension.class);
		AmazonEC2 ec2 = ext.getClient();
		
		DescribeInstancesRequest request = new DescribeInstancesRequest()
				.withInstanceIds(instanceId);
		describeInstancesResult = ec2.describeInstances(request);
		getLogger().info("Describe EC2 instances: {}", describeInstancesResult);
	}
}
