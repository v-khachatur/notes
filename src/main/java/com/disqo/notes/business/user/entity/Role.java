package com.disqo.notes.business.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roles")
@IdClass(RoleId.class)
public class Role {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Id
    @Column(name = "name", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private RoleName name;

}
