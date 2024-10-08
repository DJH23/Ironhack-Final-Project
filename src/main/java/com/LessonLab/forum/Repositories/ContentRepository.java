package com.LessonLab.forum.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.LessonLab.forum.Models.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT c FROM #{#entityName} c WHERE c.createdAt BETWEEN :start AND :end")
    List<Content> findByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<Content> findByContentContaining(String text);

    @Query("SELECT c FROM #{#entityName} c ORDER BY c.createdAt DESC")
    Page<Content> findRecentContents(Pageable pageable);

    @Query("SELECT c FROM Content c WHERE TYPE(c) = :contentType")
    List<Content> findByContentType(@Param("contentType") Class<?> contentType);

    Page<Content> findByUserId(Long userId, Pageable pageable);
}
