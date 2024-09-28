package com.QuickGo.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrivilegeDTO {
    private Integer privilegeId;
    private String privilegeName;
    private String role;
    private int status;

    private String userCode;
    private List<Integer> privilegeIds;
}
