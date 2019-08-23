package com.disqo.notes.business.note.control;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.disqo.notes.business.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByUserId(Long userId, Pageable page);

    Optional<Note> findByIdAndUserId(Long id, Long userId);

}
