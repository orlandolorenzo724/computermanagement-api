package it.orlandolorenzo.computermanagement.service;

import it.orlandolorenzo.computermanagement.dto.NetworkInterfaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NetworkInterfaceService {

    public List<NetworkInterfaceDto> getNetworkInterfaces() {
        List<NetworkInterfaceDto> networkInterfaceDtoList = new ArrayList<>();

        try {
            List<NetworkInterface> networkInterfaceList = new ArrayList<>();

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                networkInterfaceList.add(interfaces.nextElement());
            }

            for (NetworkInterface networkInterface : networkInterfaceList) {
                NetworkInterfaceDto networkInterfaceDto = NetworkInterfaceDto.fromNetworkInterface(networkInterface);
                networkInterfaceDtoList.add(networkInterfaceDto);
            }

            // Remove if ipv4 equals to N/A
            networkInterfaceDtoList.removeIf(networkInterfaceDto -> networkInterfaceDto.getIpv4().equals("N/A"));
        } catch (SocketException e) {
            log.error("Error while getting network interfaces", e);
        }

        return networkInterfaceDtoList;
    }


}