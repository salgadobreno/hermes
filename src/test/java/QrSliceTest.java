import com.avixy.qrtoken.negocio.QrSlice;
import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created on 25/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrSliceTest {
    byte[] header = "hhhhh".getBytes();
    byte[] dados = "dados".getBytes();

    @Test
    public void testConstructor(){
        QrSlice slice = new QrSlice(header, dados, 10);
        assertNotNull(slice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDadosArguments() {
        // should throw error if header+dados is bigger than the length
        QrSlice slice = new QrSlice(new byte[10], new byte[10], 5);
    }

    @Test
    public void testGetDadosPadding(){
        // should right-pad the result
        QrSlice slice = new QrSlice(header, dados, 11);
        assertEquals(slice.getDados().charAt(10), '0');
    }
}