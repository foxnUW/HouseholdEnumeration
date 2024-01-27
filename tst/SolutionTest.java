import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolutionTest {
    private static final String SAMPLE_INPUT = "tst/data/sample.txt";
    private static final String SAMPLE_EXPECTED = "tst/data/sample_expected.txt";
    private static final String SAMPLE_MALFORMED_INPUT = "tst/data/sampleMalformed.txt";
    private static final String RANDOM_TXT_INPUT = "tst/data/randomFile.txt";

    @Test
    void testStart() throws FileNotFoundException {
        // Test with sample file, using data from the prompt
        Scanner expectedScanner = new Scanner(new File(SAMPLE_EXPECTED));
        List<String> output_expected = new ArrayList<>();
        while (expectedScanner.hasNextLine()) {
            output_expected.add(expectedScanner.nextLine());
        }

        Scanner inputScanner = new Scanner(new File(SAMPLE_INPUT));
        String output = Solution.start(inputScanner);
        List<String> output_actual = List.of(output.split("\n"));

        assertEquals(output_expected.size(), output_actual.size());
        for (int i = 0; i < output_expected.size(); i++) {
            assertEquals(output_expected.get(i), output_actual.get(i));
        }

        // Scanner is empty. Test the "empty" condition for start().
        output = Solution.start(inputScanner);
        assertEquals("", output);

        inputScanner.close();
        expectedScanner.close();
    }

    @Test
    void testStart_malformedInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new File(SAMPLE_MALFORMED_INPUT));
        assertThrows(InputMismatchException.class, () -> Solution.start(inputScanner));
        inputScanner.close();

        Scanner inputScanner2 = new Scanner(new File(RANDOM_TXT_INPUT));
        assertThrows(InputMismatchException.class, () -> Solution.start(inputScanner2));
        inputScanner2.close();
    }
}