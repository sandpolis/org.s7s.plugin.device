//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo.arp;

import java.net.NetworkInterface;
import java.util.List;

import com.sandpolis.core.foundation.util.SystemUtil;

public final class ArpScan {

	public static List<String> scanNetwork(NetworkInterface networkInterface) {

		// Check the network size first
		for (var address : networkInterface.getInterfaceAddresses()) {
			if (address.getNetworkPrefixLength() < 20) {
				throw new RuntimeException();
			}
		}

		switch (SystemUtil.OS_TYPE) {
		case LINUX:
			return new ArpScannerLinux(networkInterface, networkInterface.getInterfaceAddresses().get(0)).run();
		case WINDOWS:
		default:
			throw new RuntimeException();
		}
	}
}
