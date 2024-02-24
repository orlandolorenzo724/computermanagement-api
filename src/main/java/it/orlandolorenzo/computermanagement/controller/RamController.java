package it.orlandolorenzo.computermanagement.controller;

import it.orlandolorenzo.computermanagement.dto.RamDto;
import it.orlandolorenzo.computermanagement.service.RamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ram")
@RequiredArgsConstructor
public class RamController {

    private final RamService ramService;

    @GetMapping
    public RamDto getRam() {
        return ramService.getRam();
    }
}
