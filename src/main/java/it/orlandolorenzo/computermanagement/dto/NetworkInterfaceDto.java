package it.orlandolorenzo.computermanagement.dto;

import com.sun.management.OperatingSystemMXBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.*;
import java.util.Enumeration;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NetworkInterfaceDto {

    private String name;
    private String displayName;
    private String hardwareAddress;
    private Integer mtu;
    private String ipv4;
    private String subnetMask;
    private Boolean isVirtual;
    private Boolean isLoopback;

    public static NetworkInterfaceDto fromNetworkInterface(NetworkInterface networkInterface) throws SocketException {
        return NetworkInterfaceDto
                .builder()
                .name(networkInterface.getName())
                .displayName(networkInterface.getDisplayName())
                .hardwareAddress(convertByteArrayToMACAddress(networkInterface.getHardwareAddress()).orElse("N/A"))
                .mtu(networkInterface.getMTU())
                .ipv4(retrieveIpv4(networkInterface.getInetAddresses()).orElse("N/A"))
                .subnetMask("N/A")
                .isVirtual(networkInterface.isVirtual())
                .isLoopback(networkInterface.isLoopback())
                .subnetMask(!networkInterface.getInterfaceAddresses().isEmpty() ? retrieveSubnetMask(networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength()).orElse("N/A") : "N/A")
                .build();
    }

    public static Optional<String> convertByteArrayToMACAddress(byte[] mac) {
        if (mac == null) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            // Convert byte to hexadecimal string representation
            String hex = String.format("%02X", mac[i]);
            // Append the hexadecimal string to the StringBuilder
            sb.append(hex);
            // Add a colon between each byte except for the last one
            if (i < mac.length - 1) {
                sb.append(":");
            }
        }
        return Optional.of(sb.toString());
    }

    private static Optional<String> retrieveIpv4(Enumeration<InetAddress> inetAddressEnumeration) {
        while (inetAddressEnumeration.hasMoreElements()) {
            InetAddress address = inetAddressEnumeration.nextElement();
            if (address instanceof Inet4Address) {
                return Optional.of(address.getHostAddress());
            }
        }

        return Optional.empty();
    }

    public static Optional<String> retrieveSubnetMask(short networkPrefixLength) {
        try {
            // Calculate the subnet mask
            int maskLength = 0xffffffff << (32 - networkPrefixLength);
            byte[] maskBytes = new byte[]{
                    (byte) (maskLength >>> 24 & 0xff),
                    (byte) (maskLength >>> 16 & 0xff),
                    (byte) (maskLength >>> 8 & 0xff),
                    (byte) (maskLength & 0xff)
            };
            InetAddress mask = InetAddress.getByAddress(maskBytes);
            return Optional.of(mask.getHostAddress());
        } catch (UnknownHostException e) {
            return Optional.empty();
        }
    }
}
