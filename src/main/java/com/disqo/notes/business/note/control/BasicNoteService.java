package com.disqo.notes.business.note.control;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.disqo.notes.business.note.entity.Note;
import com.disqo.notes.business.note.entity.NoteNotFoundException;
import com.disqo.notes.business.user.control.UserRepository;
import com.disqo.notes.business.user.entity.User;

@Service
public class BasicNoteService implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Note createNote(Note note, Long userId) {
        Objects.requireNonNull(note, "note must not be null");
        Objects.requireNonNull(userId, "user id must not be null");

        User user = userRepository.getOne(userId);
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));

        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public Note updateNote(Note note) {
        Objects.requireNonNull(note, "note must not be null");
        Note old = noteRepository.findById(note.getId()).orElseThrow(NoteNotFoundException::new);

        note.setCreatedAt(old.getCreatedAt());
        note.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));

        return noteRepository.save(note);
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return noteRepository.findById(id);
    }

    @Override
    public Page<Note> getAllNotes(Long userId, int first, int total) {
        Objects.requireNonNull(userId, "userId must not be null");
        return noteRepository.findAll(PageRequest.of(first, total));
    }

}
