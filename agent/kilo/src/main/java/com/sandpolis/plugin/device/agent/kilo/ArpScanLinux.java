//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device.agent.kilo;

import static com.sandpolis.core.foreign.linux.kernel.if_arp.sockaddr_ll.sll_family$set;
import static com.sandpolis.core.foreign.linux.kernel.if_arp.sockaddr_ll.sll_halen$set;
import static com.sandpolis.core.foreign.linux.kernel.if_arp.sockaddr_ll.sll_ifindex$set;
import static com.sandpolis.core.foreign.linux.kernel.if_arp.sockaddr_ll.sll_pkttype$set;
import static com.sandpolis.core.foreign.linux.kernel.socket.socket_h.bind;
import static com.sandpolis.core.foreign.linux.kernel.socket.socket_h.recvfrom;
import static com.sandpolis.core.foreign.linux.kernel.socket.socket_h.sendto;
import static com.sandpolis.core.foreign.linux.kernel.socket.socket_h.socket;

import java.nio.ByteOrder;
import java.util.List;

import com.sandpolis.core.foreign.linux.kernel.if_ether.if_ether_h;
import com.sandpolis.core.foreign.linux.kernel.if_packet.if_packet_h;
import com.sandpolis.core.foreign.linux.kernel.inet.inet_h;
import com.sandpolis.core.foreign.linux.kernel.if_arp.sockaddr_ll;
import com.sandpolis.core.foreign.linux.kernel.socket.socket_h;

import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public class ArpScanLinux {

	private static final byte[] ARP_DEST_ADDRESS = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff };

	private static final byte[] ARP_TARGET_ADDRESS = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00 };

	private final ResourceScope scope = ResourceScope.newImplicitScope();

	public static record InterfaceInfo(byte[] ip, byte[] mac, int index) {
	}

	public void send_arp(int fd, InterfaceInfo if_info, byte[] dest_ip) {

		var sockaddr = sockaddr_ll.allocate(scope);
		sll_family$set(sockaddr, (short) socket_h.AF_PACKET());
		sll_ifindex$set(sockaddr, if_info.index());
		sll_pkttype$set(sockaddr, (byte) if_packet_h.PACKET_BROADCAST());
		sll_halen$set(sockaddr, (byte) if_info.mac().length);

		var packet = MemorySegment.allocateNative(42, scope);

		// Write ethernet header
		var eth_header = packet.asSlice(0, 14).asByteBuffer().order(ByteOrder.BIG_ENDIAN);
		eth_header.put(ARP_DEST_ADDRESS);
		eth_header.put(if_info.mac());
		eth_header.putShort((short) if_ether_h.ETH_P_ARP());

		// Write ARP header
		var arp_header = packet.asSlice(14, 26).asByteBuffer().order(ByteOrder.BIG_ENDIAN);
		arp_header.putShort((short) 1);
		arp_header.putShort((short) if_ether_h.ETH_P_IP());
		arp_header.putShort((short) 6);
		arp_header.putShort((short) 4);
		arp_header.putShort((short) 1);
		eth_header.put(if_info.mac());
		eth_header.put(if_info.ip());
		eth_header.put(ARP_TARGET_ADDRESS);
		eth_header.put(dest_ip);

		if (sendto(fd, packet, packet.byteSize(), 0, sockaddr, (int) sockaddr_ll.sizeof()) == -1) {
			throw new RuntimeException();
		}
	}

	public int bind_arp(InterfaceInfo if_info) {

		int fd = socket(socket_h.AF_PACKET(), socket_h.SOCK_RAW(), inet_h.htons((short) if_ether_h.ETH_P_ARP()));
		if (fd < 1) {
			throw new RuntimeException();
		}

		var sockaddr = sockaddr_ll.allocate(scope);
		sll_family$set(sockaddr, (short) socket_h.AF_PACKET());
		sll_ifindex$set(sockaddr, fd);

		if (bind(fd, sockaddr, (int) sockaddr.byteSize()) < 1) {
			// TODO close fd
			throw new RuntimeException();
		}

		return fd;
	}

	public void read_arp(int fd) {

		var packet = MemorySegment.allocateNative(42, scope);

		long read = recvfrom(fd, packet, packet.byteSize(), 0, null, null);
		if (read == -1) {
			throw new RuntimeException();
		}
		// TODO
	}

	public InterfaceInfo query_interface(String if_name) {

		var if_request = ifreq.allocate(scope);
		ifreq.ifr_ifrn.ifrn_name$slice(null);
		// TODO

		return null;
	}

	public List<String> scan(String first_address, int size) {

		var if_info = query_interface(null);
		int fd = bind_arp(if_info);
		// TODO
		return null;
	}
}
