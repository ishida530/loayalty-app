package org.example.cli;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainE2ETest {

    @Test
    void interactiveFlow_printsWarningBelow10() throws Exception {
        String input = "earn zzz 12\nredeem zzz 5\nexit\n";
        var in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        var outContent = new ByteArrayOutputStream();

        var originalIn = System.in;
        var originalOut = System.out;
        try {
            System.setIn(in);
            System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
            Main.main(new String[]{}); // uruchamiamy w trybie interaktywnym
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Warning: Customer zzz has a low balance:"),
                "Expected warning in output, got:\n" + output);
    }
}
