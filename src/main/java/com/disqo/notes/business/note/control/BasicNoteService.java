package com.disqo.notes.business.note.control;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.disqo.notes.business.common.entity.EntityNotFoundException;
import com.disqo.notes.business.note.entity.Note;
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
    public Note updateNote(Note note, Long userId) {
        Objects.requireNonNull(note, "note must not be null");
        Objects.requireNonNull(userId, "user id must not be null");
        Note old = getNoteById(note.getId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Note entity not found"));

        note.setCreatedAt(old.getCreatedAt());
        note.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        note.setUser(old.getUser());

        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public void deleteNote(Long id, Long userId) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(userId, "user id must not be null");
        Note old = getNoteById(id, userId).orElseThrow(() -> new EntityNotFoundException("Note entity not found"));
        noteRepository.delete(old);
    }

    @Override
    public Optional<Note> getNoteById(Long id, Long userId) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(userId, "user id must not be null");
        return noteRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Page<Note> getUsersNotes(Long userId, int first, int total) {
        Objects.requireNonNull(userId, "userId must not be null");
        Sort sort = Sort.by("id").descending();
        return noteRepository.findByUserId(userId, PageRequest.of(first - 1, total, sort));
    }

}
