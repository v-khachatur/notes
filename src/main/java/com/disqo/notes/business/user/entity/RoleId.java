package com.disqo.notes.business.user.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleId implements Serializable {

    private static final long serialVersionUID = 1L;

    private RoleName name;
    private Long user;

}
