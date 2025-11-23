package com.example.demo.entity;

import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class RefreshEntity {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String userId;

    @Column (nullable = false)
    private String tokenHash;

    @Column (nullable = false)
    Instant issuedAt;

    @Column( nullable = false )
    Instant expiresAt;

    @Column (nullable = false)
    private boolean revokedFlag;

    @Column(nullable =  false)
    private String deviceInfo;
   

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    

  
    public boolean isRevokedFlag() {
        return this.revokedFlag;
    }

    public void setRevokedFlag(boolean revokedFlag) {
        this.revokedFlag = revokedFlag;
    }

}
