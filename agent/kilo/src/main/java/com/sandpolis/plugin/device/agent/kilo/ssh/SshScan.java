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

import java.net.Socket;
import java.util.Optional;

public final class SshScan {

	public static record SshScanResult(String ssh_banner, String fingerprint) {
	}

	public static Optional<SshScanResult> scanHost(String ip_address) {

		try (var socket = new Socket(ip_address, 22); var in = socket.getInputStream()) {
			String banner = new String(in.readAllBytes());
			return Optional.of(new SshScanResult(banner, null));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
