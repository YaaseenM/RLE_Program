import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RleProgramTest {

    @Test
    public void toHexStringTest() {
        byte[] data = {3,15,6,4}; // input
        String hexString = "3f64"; // output/expected result
        assertEquals(hexString, RleProgram.toHexString(data));
    }

    @Test
    public void getDecodedLengthTest() {
        byte[] rleData = {3,15,6,2,20,1};             // input
        byte res = 29;                   // output
        assertEquals(res, RleProgram.getDecodedLength(rleData));
    }

    @Test
    public void countRunsTest() {
        byte[] flatData = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5};
        int groups = 5; //expected result
        assertEquals(groups, RleProgram.countRuns(flatData));
    }
    @Test
    public void encodeRleTest() {
        byte[] flatData = {15,15,15,4,4,4,4,4,4}; // input
        byte[] rleData = {3,15,6,4}; // output/expected result
        assertArrayEquals(rleData, RleProgram.encodeRle(flatData));
    }
    @Test
    public void decodeRleTest() {
        byte[] rleData = {3,15,8,4}; // input
        byte[] res = {15,15,15,4,4,4,4,4,4,4,4}; // output/expected result
        assertArrayEquals(res, RleProgram.decodeRle(rleData));
    }
    @Test
    public void stringToDataTest() {
        String dataString = "3f64"; // input
        byte[] res = {3, 15, 6, 4}; // output/expected result
        assertArrayEquals(res, RleProgram.stringToData(dataString));
    }
    @Test
    public void toRleStringTest() {
        byte[] rleData = {15,15,6,4,6,9}; // input
        String hexString = "15f:64:69"; // output/expected result
        assertEquals(hexString, RleProgram.toRleString(rleData));
    }
    @Test
    public void stringToRleTest() {
        String rleString = "15F:64"; // input
        byte[] res = {15, 15, 6, 4}; // output/expected result
        assertArrayEquals(res, RleProgram.stringToRle(rleString));
    }

}
