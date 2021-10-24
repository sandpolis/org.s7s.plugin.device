//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
module com.sandpolis.plugin.device.agent.kilo {
	exports com.sandpolis.plugin.device.agent.kilo.snmp.library;
	exports com.sandpolis.plugin.device.agent.kilo.arp;

	requires com.google.protobuf;
	requires com.sandpolis.core.instance;
	requires com.sandpolis.core.net;
	requires com.sandpolis.plugin.device;
	requires com.sandpolis.core.foreign;
	requires org.snmp4j;
	requires org.slf4j;

	requires jdk.incubator.foreign;
	requires com.sandpolis.core.foundation;
}
