package com.disqo.notes.business.note.control;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.disqo.notes.business.note.entity.Note;

public interface NoteService {

    Note createNote(Note note, Long userId);

    Note updateNote(Note note);

    Optional<Note> getNoteById(Long id);

    Page<Note> getAllNotes(Long userId, int first, int total);

}
