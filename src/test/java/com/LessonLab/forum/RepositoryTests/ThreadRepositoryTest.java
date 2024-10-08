package com.LessonLab.forum.RepositoryTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.LessonLab.forum.Repositories.ThreadRepository;
import com.LessonLab.forum.Models.Thread;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ThreadRepositoryTest {

    @Autowired
    private ThreadRepository threadRepository;

    private Thread testThread;

    @BeforeEach
    public void setUp() {
        // Create a test thread
        testThread = new Thread();
        testThread.setTitle("Test Thread");
        testThread.setDescription("This is a test thread");
        threadRepository.save(testThread);
    }

    @Test
    public void testFindByTitleContaining() {
        // Act
        List<Thread> threads = threadRepository.findByTitleContaining("Test");

        // Assert
        assertFalse(threads.isEmpty());
        threads.forEach(thread -> assertTrue(thread.getTitle().contains("Test")));
    }

    @Test
    public void testFindByDescriptionContaining() {
        // Act
        List<Thread> threads = threadRepository.findByDescriptionContaining("test");

        // Assert
        assertFalse(threads.isEmpty());
        threads.forEach(thread -> assertTrue(thread.getDescription().contains("test")));
    }

    @AfterEach
    public void tearDown() {
        // Delete the test thread
        threadRepository.delete(testThread);
    }
}
