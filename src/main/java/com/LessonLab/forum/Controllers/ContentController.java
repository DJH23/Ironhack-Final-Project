package com.LessonLab.forum.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.LessonLab.forum.Models.Content;
import com.LessonLab.forum.Services.CommentService;
import com.LessonLab.forum.Services.PostService;
import com.LessonLab.forum.Services.ThreadService;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private ThreadService threadService;
    
    @PostMapping("/{contentType}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> addContent(@PathVariable String contentType, @RequestBody Content content) {
        Content savedContent;
        switch (contentType.toLowerCase()) {
            case "comment":
                savedContent = commentService.addContent(content, null);
                break;
            case "post":
                savedContent = postService.addContent(content, null);
                break;
            case "thread":
                savedContent = threadService.addContent(content, null);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED);
    }

    @PutMapping("/{contentType}/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateContent(@PathVariable String contentType, @PathVariable Long id, @RequestBody String newContent) {
        Content updatedContent;
        switch (contentType.toLowerCase()) {
            case "comment":
                updatedContent = commentService.updateContent(id, newContent, null);
                break;
            case "post":
                updatedContent = postService.updateContent(id, newContent, null);
                break;
            case "thread":
                updatedContent = threadService.updateContent(id, newContent, null);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }

    @GetMapping("/{contentType}/{id}")
    public ResponseEntity<?> getContent(@PathVariable String contentType, @PathVariable Long id) {
        Content content;
        switch (contentType.toLowerCase()) {
            case "comment":
                content = commentService.getContent(id);
                break;
            case "post":
                content = postService.getContent(id);
                break;
            case "thread":
                content = threadService.getContent(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/search/{contentType}")
    public ResponseEntity<?> searchContent(@PathVariable String contentType, @RequestParam String searchText) {
        List<? extends Content> contents;
        switch (contentType.toLowerCase()) {
            case "comment":
                contents = commentService.searchContent(searchText);
                break;
            case "post":
                contents = postService.searchContent(searchText);
                break;
            case "thread":
                contents = threadService.searchContent(searchText);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/user/{contentType}/{userId}")
    public ResponseEntity<?> getPagedContentByUser(@PathVariable String contentType, @PathVariable Long userId, Pageable pageable) {
        Page<? extends Content> contents;
        switch (contentType.toLowerCase()) {
            case "comment":
                contents = commentService.getPagedContentByUser(userId, pageable);
                break;
            case "post":
                contents = postService.getPagedContentByUser(userId, pageable);
                break;
            case "thread":
                contents = threadService.getPagedContentByUser(userId, pageable);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/created-at-between/{contentType}")
    public ResponseEntity<?> getContentsByCreatedAtBetween(@PathVariable String contentType, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        List<Content> contents;
        switch (contentType.toLowerCase()) {
            case "comment":
                contents = commentService.getContentsByCreatedAtBetween(start, end);
                break;
            case "post":
                contents = postService.getContentsByCreatedAtBetween(start, end);
                break;
            case "thread":
                contents = threadService.getContentsByCreatedAtBetween(start, end);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/content-containing/{contentType}")
    public ResponseEntity<?> getContentsByContentContaining(@PathVariable String contentType, @RequestParam String text) {
        List<? extends Content> contents;
        switch (contentType.toLowerCase()) {
            case "comment":
                contents = commentService.getContentsByContentContaining(text);
                break;
            case "post":
                contents = postService.getContentsByContentContaining(text);
                break;
            case "thread":
                contents = threadService.getContentsByContentContaining(text);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @DeleteMapping("/{contentType}/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteContent(@PathVariable String contentType, @PathVariable Long id) {
        switch (contentType.toLowerCase()) {
            case "comment":
                commentService.deleteContent(id, null);
                break;
            case "post":
                postService.deleteContent(id, null);
                break;
            case "thread":
                threadService.deleteContent(id, null);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{contentType}")
    public ResponseEntity<?> listContent(@PathVariable String contentType) {
        List<? extends Content> contents;
        switch (contentType.toLowerCase()) {
            case "comment":
                contents = commentService.listContent();
                break;
            case "post":
                contents = postService.listContent();
                break;
            case "thread":
                contents = threadService.listContent();
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @PostMapping("/{contentType}/{contentId}/vote")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> handleVote(@PathVariable String contentType, @PathVariable Long contentId, @RequestParam Long userId, @RequestParam boolean isUpVote) {
        switch (contentType.toLowerCase()) {
            case "comment":
                commentService.handleVote(contentId, userId, isUpVote);
                break;
            case "post":
                postService.handleVote(contentId, userId, isUpVote);
                break;
            case "thread":
                threadService.handleVote(contentId, userId, isUpVote);
                break;
            default:
                throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
