package com.hanghae99.agitclone.agit.repository;

import com.hanghae99.agitclone.agit.entity.Agit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgitRepository extends JpaRepository<Agit, Long> {

}
