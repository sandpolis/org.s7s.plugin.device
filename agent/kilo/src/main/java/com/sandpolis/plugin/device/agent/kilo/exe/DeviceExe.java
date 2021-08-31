//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo.exe;

import com.google.protobuf.MessageLiteOrBuilder;
import com.sandpolis.core.net.exelet.Exelet;
import com.sandpolis.plugin.device.msg.MsgDevice.RQ_RegisterDevice;

public final class DeviceExe extends Exelet {

	@Handler(auth = true)
	public static MessageLiteOrBuilder rq_register_device(RQ_RegisterDevice rq) throws Exception {
		// TODO
		return null;
	}

	@Handler(auth = true)
	public static MessageLiteOrBuilder rq_find_subagents(RQ_FindSubagents rq) throws Exception {

		var rs = RS_FindSubagents.newBuilder();

		// Find hosts with ARP scan
		for (var host : ArpScan.scan(rq.getInterface())) {
			if (rq.getCommunicatorsList().contains(CommunicatorType.SSH)) {
				try {
					var socket = new Socket(host, 22);
					try (var out = socket.getOutputStream()) {
						rs.addSshDevice(RS_FindSubagents.SshDevice.newBuilder()
							.setIpAddress(host)
							.setFingerprint(new String(out.toByteArray())));
					}

				} catch (Exception e) {
					// Ignore
				}
			}

			if (rq.getCommunicatorsList().contains(CommunicatorType.SNMP)) {
				var socket = new DatagramSocket(45680);
			}

			if (rq.getCommunicatorsList().contains(CommunicatorType.IPMI)) {
				var socket = new DatagramSocket(45680);

				var ipmiPacket = ByteBuffer.allocate();
				ipmiPacket.putByte(6);
			}
		}

		return rs;
	}

	private DeviceExe() {
	}
}
