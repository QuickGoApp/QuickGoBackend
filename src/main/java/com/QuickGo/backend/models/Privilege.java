package com.QuickGo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer privilegeId;

    @Column(name = "privilege_name")
    private String privilegeName;
    @Column(name = "status")
    private int status;

    @Override
    public String toString() {
        return "Privilege{" +
                "privilegeId=" + privilegeId +
                ", privilegeName='" + privilegeName + '\'' +
                ", status=" + status +
                '}';
    }
}