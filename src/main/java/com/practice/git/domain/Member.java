package com.practice.git.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    protected Member() {

    }

    private Member(String name, String phone, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.phone = phone;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Member createMember(String name, String phone, LocalDateTime createdAt) {
        return new Member(name, phone, createdAt, createdAt);
    }
}
