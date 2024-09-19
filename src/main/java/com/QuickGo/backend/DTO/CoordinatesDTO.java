package com.QuickGo.backend.DTO;

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
