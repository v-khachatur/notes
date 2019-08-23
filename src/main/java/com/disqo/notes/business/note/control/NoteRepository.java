package com.disqo.notes.business.note.control;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disqo.notes.business.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
