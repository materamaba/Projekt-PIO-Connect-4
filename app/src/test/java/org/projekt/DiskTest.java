package org.projekt;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class DiskTest {

    @Test
    void TestCreateDiscWhenIdIsValid() {
        try {
            new Disk(1);
            new Disk(2);
        } catch (Exception error) {
            fail("Constructor should create a correct object");
        }
    }

    @Test
    void TestThrowExceptionWhenIdIsInvalid() {
        try {
            new Disk(3);
            fail("Constructor should create a correct object");
        } catch (Exception error) {
        }
    }
}