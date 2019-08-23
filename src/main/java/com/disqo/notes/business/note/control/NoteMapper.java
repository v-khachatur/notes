package com.disqo.notes.business.note.control;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.disqo.notes.business.note.entity.Note;
import com.disqo.notes.business.note.entity.NoteModel;

@Component
public class NoteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Note fromNoteModel(NoteModel noteModel) {
        return modelMapper.map(noteModel, Note.class);
    }

    public NoteModel toNoteModel(Note note) {
        return modelMapper.map(note, NoteModel.class);
    }

    public List<NoteModel> toNoteModels(Page<Note> notes) {
        return notes.map(this::toNoteModel).getContent();
    }

}
