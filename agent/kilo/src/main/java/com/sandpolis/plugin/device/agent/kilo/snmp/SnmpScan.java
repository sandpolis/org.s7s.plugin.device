//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo.snmp;

public final class SnmpScan {

	public static record SnmpScanResult(String snmp_version) {
	}

	public static Optional<SnmpScanResult> scanHost(String ip_address) {

		// Use SNMP4j?
	}

}
