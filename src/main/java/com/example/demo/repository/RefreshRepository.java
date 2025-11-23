package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RefreshEntity;


public interface  RefreshRepository extends JpaRepository<RefreshEntity,Long> {

    Optional<RefreshEntity>  findByTokenHash(String tokenHash);
    
}
