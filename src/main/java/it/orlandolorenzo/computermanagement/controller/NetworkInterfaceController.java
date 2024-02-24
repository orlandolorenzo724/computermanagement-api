package it.orlandolorenzo.computermanagement.controller;

import it.orlandolorenzo.computermanagement.dto.NetworkInterfaceDto;
import it.orlandolorenzo.computermanagement.service.NetworkInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/network-interfaces")
@RequiredArgsConstructor
public class NetworkInterfaceController {

    private final NetworkInterfaceService networkInterfaceService;

    @GetMapping
    public List<NetworkInterfaceDto> getNetworkInterfaces() {
        return networkInterfaceService.getNetworkInterfaces();
    }
}
