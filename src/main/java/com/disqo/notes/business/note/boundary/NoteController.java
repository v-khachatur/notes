package com.disqo.notes.business.note.boundary;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.disqo.notes.business.common.entity.EntityNotFoundException;
import com.disqo.notes.business.note.control.NoteMapper;
import com.disqo.notes.business.note.control.NoteService;
import com.disqo.notes.business.note.entity.Note;
import com.disqo.notes.business.note.entity.NoteModel;
import com.disqo.notes.business.note.entity.NoteRequestModel;
import com.disqo.notes.business.user.entity.UserPrincipal;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteMapper noteMapper;

    @PostMapping
    public ResponseEntity<NoteModel> createNote(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid NoteModel noteModel) {
        Note note = noteMapper.fromNoteModel(noteModel);
        note = noteService.createNote(note, principal.getUserId());

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(note.getId()).toUri();
        return ResponseEntity.created(uri).body(noteMapper.toNoteModel(note));
    }

    @PutMapping("/{id}")
    public NoteModel updateNote(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid NoteModel noteModel, @PathVariable("id") Long id) {
        Note note = noteMapper.fromNoteModel(noteModel);
        note.setId(id);
        note = noteService.updateNote(note, principal.getUserId());
        return noteMapper.toNoteModel(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("id") Long id) {
        noteService.deleteNote(id, principal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public NoteModel getNoteById(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("id") Long id) {
        Note note = noteService.getNoteById(id, principal.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Note entity not found"));
        return noteMapper.toNoteModel(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteModel>> getUserNotes(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
            @Valid NoteRequestModel noteRequestModel) {
        Page<Note> notes = noteService.getUsersNotes(principal.getUserId(), noteRequestModel.getPage(),
                noteRequestModel.getTotal());

        HttpHeaders headers = new HttpHeaders();
        headers.add("total", String.valueOf(notes.getTotalPages()));

        return new ResponseEntity<>(noteMapper.toNoteModels(notes), headers, HttpStatus.OK);
    }

}
