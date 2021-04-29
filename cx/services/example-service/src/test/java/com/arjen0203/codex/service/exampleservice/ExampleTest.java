package com.arjen0203.codex.service.exampleservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    public void RunningTest() {
        Assertions.assertEquals(true, true);
    }

    @Test
    public void FailingTest() {
        Assertions.assertEquals(true, false);
    }
}
