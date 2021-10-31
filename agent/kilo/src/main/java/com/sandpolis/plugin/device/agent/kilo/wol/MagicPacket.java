package com.sandpolis.plugin.device.agent.kilo.wol;

public record MagicPacket(DatagramPacket packet) {

	private static final int PACKET_SIZE = 6 + 6 * 6;

	public static MagicPacket of(String mac) {
		var packet = ByteBuffer.allocate(PACKET_SIZE);

		// Set the broadcast address
		packet.putBytes(new byte[] { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff });

		// Add the target MAC 6 times
		for (int i = 0; i < 6; i++) {
			packet.putBytes(mac);
		}

		InetAddress address = InetAddress.getByName(ipStr);

		return new MagicPacket(new DatagramPacket(packet.getBytes(), PACKET_SIZE, address, 9));
	}

	public void send() {
		try(DatagramSocket socket = new DatagramSocket()) {

			// Send the magic packet a few times
			for (int i = 0; i < 3; i++) {
				socket.send(packet);
			}
		}
	}
}
