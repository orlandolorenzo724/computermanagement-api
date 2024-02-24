package it.orlandolorenzo.computermanagement.controller;

import it.orlandolorenzo.computermanagement.dto.DiskDto;
import it.orlandolorenzo.computermanagement.service.DiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/disks")
@RequiredArgsConstructor
public class DiskController {

    private final DiskService diskService;

    @GetMapping
    public List<DiskDto> getDiskList() {
        return diskService.getDiskList();
    }
}
