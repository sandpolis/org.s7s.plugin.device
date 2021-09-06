//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo.ipmi;

public final class IpmiScan {

	public static record IpmiScanResult(String ipmi_version) {
	}

	private static record RmcpHeader (
		byte version,
		byte reserved,
		byte sequence_number,
		byte message_class) {

	}

	public static Optional<IpmiScanResult> scanHost(String ip_address) {

		var socket = new DatagramSocket(45680);

		var packetData = ByteBuffer.allocate();
		ipmiPacket.putByte(6);

		socket.send(new DatagramPacket(packetData, packetData.byteSize(), new InetSocketAddress​(ip_address, 623)));

		// Attempt to receive response
		var response = new DatagramPacket(new byte[0], 0);
		socket.receive(response);
	}
}
