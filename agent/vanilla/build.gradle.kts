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
	id("sandpolis-soi")
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

	// http://www.snmp4j.org
	implementation("org.snmp4j:snmp4j:3.5.1")

	compileOnly(project.getParent()?.getParent()!!)
}

eclipse {
	project {
		name = "com.sandpolis.plugin.device:agent:vanilla"
		comment = "com.sandpolis.plugin.device:agent:vanilla"
	}
}
