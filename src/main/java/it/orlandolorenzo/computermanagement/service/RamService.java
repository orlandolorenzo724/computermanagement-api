package it.orlandolorenzo.computermanagement.service;

import com.sun.management.OperatingSystemMXBean;
import it.orlandolorenzo.computermanagement.dto.RamDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
@RequiredArgsConstructor
@Slf4j
public class RamService {

    public RamDto getRam() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long total = osBean.getTotalMemorySize() / 1024 / 1024;
        long free = osBean.getFreeMemorySize() / 1024 / 1024;
        long used = total - free;
        return RamDto.builder()
                .total(total)
                .free(free)
                .used(used)
                .build();
    }
}