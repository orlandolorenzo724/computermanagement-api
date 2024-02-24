package it.orlandolorenzo.computermanagement.service;

import it.orlandolorenzo.computermanagement.dto.DiskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiskService {

    public List<DiskDto> getDiskList() {
        List<DiskDto> diskDtoList = new ArrayList<>();

        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        fileStores.forEach(fileStore -> {
            try {
                DiskDto diskDto = DiskDto.builder()
                        .name(fileStore.name())
                        .total(formatSize(fileStore.getTotalSpace()))
                        .usable(formatSize(fileStore.getUsableSpace()))
                        .unallocated(formatSize(fileStore.getUnallocatedSpace()))
                        .build();
                diskDtoList.add(diskDto);
            } catch (Exception e) {
                log.error("Error while getting disk info", e);
            }
        });

        return diskDtoList;
    }

    private static String formatSize(long bytes) {
        final long k = 1024;
        long size = bytes;
        if (size < k) {
            return size + " B";
        }
        int exp = (int) (Math.log(size) / Math.log(k));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", size / Math.pow(k, exp), pre);
    }
}
