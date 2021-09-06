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
				SshScan.scanHost(host).ifPresent(info -> {
					rs.addSshDevice(RS_FindSubagents.SshDevice.newBuilder()
						.setIpAddress(host)
						.setVersion(info.ssh_version())
						.setFingerprint(info.fingerprint()));
				});
			}

			if (rq.getCommunicatorsList().contains(CommunicatorType.SNMP)) {
				SnmpScan.scanHost(host).ifPresent(info -> {
					rs.addSnmpDevice(RS_FindSubagents.SnmpDevice.newBuilder()
						.setIpAddress(host));
				});
			}

			if (rq.getCommunicatorsList().contains(CommunicatorType.IPMI)) {
				IpmiScan.scanHost(host).ifPresent(info -> {
					rs.addIpmiDevice(RS_FindSubagents.IpmiDevice.newBuilder()
						.setIpAddress(host));
				});
			}
		}

		return rs;
	}

	private DeviceExe() {
	}
}
