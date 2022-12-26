package com.hanghae99.agitclone.agit.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class AgitMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long agitId;

    @Builder
    public AgitMember(Long userId, Long agitId){
        this.userId = userId;
        this.agitId = agitId;
    }
}
