package com.arjen0203.codex.service.exampleservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    void succeeding() {
        Assertions.assertEquals(true, true);
    }

    @Test
    void failing() {
        Assertions.assertEquals(true, false);
    }
}
