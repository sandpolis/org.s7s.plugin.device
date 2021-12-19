//============================================================================//
//                                                                            //
//            Copyright © 2015 - 2022 Sandpolis Software Foundation           //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPLv2. //
//                                                                            //
//============================================================================//

plugins {
	id("java-library")
	id("org.s7s.build.module")
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.+")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.+")

	// http://www.snmp4j.org
	implementation("org.snmp4j:snmp4j:3.5.1")

	compileOnly(project.getParent()?.getParent()!!)

	if (project.getParent() == null) {
		compileOnly("org.s7s.core.integration.linux:+")
	} else {
		compileOnly(project(":core:integration:org.s7s.core.integration.linux"))
	}
}

eclipse {
	project {
		name = "org.s7s.plugin.device:agent:java"
		comment = "org.s7s.plugin.device:agent:java"
	}
}
