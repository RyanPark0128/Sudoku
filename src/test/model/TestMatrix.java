package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMatrix {
    private Matrix testMatrix;
    
    @BeforeEach
    void runBefore() {
        testMatrix = new Matrix();
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}
