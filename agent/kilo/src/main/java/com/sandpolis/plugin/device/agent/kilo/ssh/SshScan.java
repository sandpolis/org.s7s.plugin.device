//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo.ssh;

public final class SshScan {

	public static record SshScanResult(String ssh_version, String fingerprint) {
	}

	public static Optional<SshScanResult> scanHost(String ip_address) {

		try {
			var socket = new Socket(host, 22);
			try (var out = socket.getOutputStream()) {
			}

		} catch (Exception e) {
			// Ignore
		}
	}

}
