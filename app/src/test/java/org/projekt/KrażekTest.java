package org.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KrażekTest {

    @Test
    void TestCreateDiscWhenIdIsValid() {
        try {
            new Krażek(1);
            new Krażek(2);
        } catch (Exception error) {
            fail("Constructor should create a correct object");
        }
    }

    @Test
    void TestThrowExceptionWhenIdIsInvalid() {
        try {
            new Krażek(3);
            fail("Constructor should create a correct object");
        } catch (Exception error) {
        }
    }
}