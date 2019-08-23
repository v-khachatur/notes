package com.disqo.notes.business.note.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NoteRequestModel {

    private Long userId;

    @Min(1)
    @NotNull
    private Integer page;

    @Min(1)
    @NotNull
    private Integer total;

}
