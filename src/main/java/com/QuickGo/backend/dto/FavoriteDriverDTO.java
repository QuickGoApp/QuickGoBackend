package com.QuickGo.backend.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDriverDTO {

    private Integer id;
    private String passengerCode;
    private String driverCode;
    private String createDateTime;
    private String updateDateTime;
    private int isActive ;
}
