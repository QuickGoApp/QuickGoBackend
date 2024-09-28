package com.QuickGo.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatesDTO {
    private double lat;
    private double lng;
}
