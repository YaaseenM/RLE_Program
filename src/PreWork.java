/*public class PreWork {

    // 1. sum by parity
    // input: {5,12,8,5,3,11,7,2,3,16,4}
    // output: {30,46}
    public static int[] sumByParity(int[] arr) {
        int[] res = new int[2];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) { // we are at even indices
                res[0] += arr[i];
            }
            else { // we are at odd indices
                res[1] += arr[i];
            }
        }
        return res;
    }

    // 2. input: {3,2,5,4,4,4,5,5,5,5}, {3,2,5,4,4,4,5,5,5,5}
    //    output: false, true
    // {3,3,3,3,2,1,5,5,6,6,6} => true
    // {3,3,3,2,1,5,5,6,6,6) => false
    public static boolean consecutivePours(int[] arr) {
        int count = 1;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                count++;    // 2
            }
            else {
                count = 1;
            }
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }

    //             0,1,2
    // 3.  input: {2,1,3}
    //     output: {0,0,1,2,2,2}
    public static int[] expandByIndex(int[] arr) {
        // size of the output
        int size = 0;
        int index = 0;
        for(int item : arr) {
            size += item;
        }
        int[] res = new int[size];
        for(int i = 0; i < arr.length; i++) {
            int value = i;  // item in the odd indices e.g. 15
            int repeats = arr[i]; // item in the even indices before 15, e.g. 3
            for(int j = 0; j < repeats; j++) {
                res[index] = value;
                index++;
            }
        }
        return res;
    }

    // 4. input: "abcd3fgh1"
    //    output: 2
    public static int numericalCount(String string){
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                count++;
            }

        }
        return count;
    }

    public static void main(String[] args) {
        String str = "f";
        System.out.println(Byte.parseByte(str, 16));
    }


     public static void main(String[] args) {
        int[] arr = {5,12,8,5,3,11,7,2,3,16,4};
        int [] res = sumByParity(arr);
        for (int item : res) {
            System.out.println(item);
        }
    }
}










byte[] strToData = new byte[rleString.length()];
        int charDigit = 1;
        String value;
        for (int i = 0; i < rleString.length(); i++) {
            if (charDigit == 1) {
                value = String.valueOf(rleString.charAt(i));
                strToData[i] = Byte.parseByte(value, 16);
            } else if (charDigit == 2){
                value = String.valueOf(rleString.charAt(i));
                strToData[i] = Byte.parseByte(value, 16);
            } else {
                continue;
            }
            charDigit++;
        }
        return strToData;

*/