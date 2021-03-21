//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

plugins {
	id("java-library")
	id("sandpolis-java")
	id("sandpolis-module")
	id("sandpolis-protobuf")
	id("sandpolis-publish")
	id("sandpolis-soi")
	id("sandpolis-plugin")
	id("sandpolis-codegen")
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

	if (project.getParent() == null) {
		api("com.sandpolis:core.clientagent:0.1.0")
		api("com.sandpolis:core.clientserver:0.1.0")
	} else {
		api(project(":module:com.sandpolis.core.clientagent"))
		api(project(":module:com.sandpolis.core.clientserver"))
	}

	// https://github.com/javaee/jpa-spec
	implementation("javax.persistence:javax.persistence-api:2.2")
}

sandpolis_plugin {
	id = project.name
	coordinate = "com.sandpolis:sandpolis-plugin-device"
	name = "Device Plugin"
	description = ""
}
