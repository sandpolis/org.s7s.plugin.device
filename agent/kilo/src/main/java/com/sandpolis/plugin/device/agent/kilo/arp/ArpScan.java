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

public final class ArpScan {

	public static List<String> scan(String interfaceName) {

		switch (SystemUtil.OS_TYPE) {
		case OsType.LINUX:
			return new ArpScanLinux(interfaceName).run();
		case OsType.WINDOWS:
			return new ArpScanWindows(interfaceName).run();
		default:
			throw new RuntimeException();
		}
	}
}
