package com.avixy.qrtoken.core.extensions;

import org.junit.Test;
import static org.junit.Assert.*;

public class HorarioFieldTest {
    String randomString = "balbla 029 90 kla ?;szp";
    String validString = "12:01";
    String alsoInvalid = "1234:4321";

    HorarioField horarioField = new HorarioField();

    @Test
    public void testReplaceText() throws Exception {
        // Won't accept randomString
        assertNotEquals(randomString, horarioField.getText());
        // Won't accept alsoInvalid
        horarioField.setText(alsoInvalid);
        assertNotEquals(alsoInvalid, horarioField.getText());
        // Will accept validString
        horarioField.setText(validString);
        assertEquals(validString, horarioField.getText());
    }
}