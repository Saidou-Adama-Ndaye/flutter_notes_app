package com.tirage.springbackend.controller;

import com.tirage.springbackend.exception.ResourceNotFoundException;
import com.tirage.springbackend.model.Note;
import com.tirage.springbackend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class NoteController {
    @Autowired
    private NoteRepository noteRepository;

    //get all students
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    //Create student rest api
    @PostMapping("/notes")
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    // get student by id rest api
    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not exist with id:"+id));
        return ResponseEntity.ok(note);
    }

    // Update student rest api
    @PutMapping("/notes/{id} ")
    public  ResponseEntity<Note> updateNote(@PathVariable  Long id, @RequestBody Note noteDetails){

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not exist with id:"+id));

        note.setMatiere(noteDetails.getMatiere());
        note.setNote(noteDetails.getNote());

        Note updatedNote =  noteRepository.save(note);
        return  ResponseEntity.ok(updatedNote);
    }

    // delete student rest api
    
    public ResponseEntity <Map<String, Boolean>> deleteNote(@PathVariable Long id){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not exist with id:"+id));
        noteRepository.delete(note);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);

    }
}
