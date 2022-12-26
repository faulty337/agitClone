package com.hanghae99.agitclone.agit.repository;

import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.entity.AgitMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgitMemberRepository extends JpaRepository<AgitMember, Long> {
    List<AgitMember> findAllByUserId(Long userId);
}
