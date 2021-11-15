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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.MessageLiteOrBuilder;
import com.sandpolis.core.net.exelet.Exelet;
import com.sandpolis.plugin.device.agent.kilo.arp.ArpScan;
import com.sandpolis.plugin.device.agent.kilo.ipmi.IpmiScan;
import com.sandpolis.plugin.device.agent.kilo.snmp.SnmpScan;
import com.sandpolis.plugin.device.agent.kilo.ssh.SshScan;
import com.sandpolis.plugin.device.Messages.RQ_FindSubagents;
import com.sandpolis.plugin.device.Messages.RQ_FindSubagents.CommunicatorType;
import com.sandpolis.plugin.device.Messages.RS_FindSubagents;

public final class DeviceExe extends Exelet {

//	@Handler(auth = true)
//	public static MessageLiteOrBuilder rq_register_device(RQ_RegisterDevice rq) throws Exception {
//		// TODO
//		return null;
//	}

	@Handler(auth = true)
	public static MessageLiteOrBuilder rq_find_subagents(RQ_FindSubagents rq) throws Exception {

		var rs = RS_FindSubagents.newBuilder();

		// Determine networks to scan
		List<NetworkInterface> networks = new ArrayList<>();
		if (rq.getNetworkCount() == 0) {
			networks = NetworkInterface.networkInterfaces().collect(Collectors.toList());
		} else {
			for (String name : rq.getNetworkList()) {
				var netIf = NetworkInterface.getByName(name);
				if (netIf != null) {
					networks.add(netIf);
				}
			}
		}

		// Find hosts with ARP scan
		for (var networkInterface : networks) {
			for (var host : ArpScan.scanNetwork(networkInterface)) {
				if (rq.getCommunicatorList().contains(CommunicatorType.SSH)) {
					SshScan.scanHost(host.ip()).ifPresent(info -> {
						rs.addSshDevice(RS_FindSubagents.SshDevice.newBuilder().setIpAddress(host.ip())
								.setFingerprint(info.ssh_banner()).setFingerprint(info.fingerprint()));
					});
				}

				if (rq.getCommunicatorList().contains(CommunicatorType.SNMP)) {
					SnmpScan.scanHost(host.ip()).ifPresent(info -> {
						rs.addSnmpDevice(RS_FindSubagents.SnmpDevice.newBuilder().setIpAddress(host.ip()));
					});
				}

				if (rq.getCommunicatorList().contains(CommunicatorType.IPMI)) {
					IpmiScan.scanHost(host.ip()).ifPresent(info -> {
						rs.addIpmiDevice(RS_FindSubagents.IpmiDevice.newBuilder().setIpAddress(host.ip()));
					});
				}
			}
		}

		return rs;
	}

	private DeviceExe() {
	}
}
