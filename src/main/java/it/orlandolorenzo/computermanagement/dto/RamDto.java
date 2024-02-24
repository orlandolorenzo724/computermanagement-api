package it.orlandolorenzo.computermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RamDto {
    private Long total;
    private Long free;
    private Long used;
}
