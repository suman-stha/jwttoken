package com.signin.jwt.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String action;        // DELETE_USER
    private Integer targetUserId; // deleted user id
    private String performedBy;   // admin email

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}