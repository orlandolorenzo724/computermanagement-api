package it.orlandolorenzo.computermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiskDto {
    private String name;
    private String total;
    private String usable;
    private String unallocated;
}
