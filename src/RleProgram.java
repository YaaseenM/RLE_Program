// The conversion of data program form an image file

import java.util.Arrays;
import java.util.Scanner;

public class RleProgram {
    public static void main(String[] args) {
        // In the beginning, you are welcomed with this text,
        System.out.println("Welcome to the RLE image encoder!");
        System.out.println("\nDisplaying Spectrum Image: ");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);
        System.out.println("\n");

        int option;
        byte[] imageData = null;
        Scanner scanner = new Scanner(System.in);
        // Start a do while loop so that the menu is presented first before
        do {
            System.out.println("RLE Menu");
            System.out.println("--------");
            System.out.println("0. Exit");
            System.out.println("1. Load File");
            System.out.println("2. Load Test Image");
            System.out.println("3. Read RLE String");
            System.out.println("4. Read RLE Hex String");
            System.out.println("5. Read Data Hex String");
            System.out.println("6. Display Image");
            System.out.println("7. Display RLE String");
            System.out.println("8. Display Hex RLE Data");
            System.out.println("9. Display Hex Flat Data\n");
            System.out.print("Select a Menu Option: ");
            option = scanner.nextInt();
            //List of options that will be executed based on user's input.

            // Load File
            if (option == 1) {
                System.out.print("Enter name of file to load: ");
                String userInputFileName = scanner.next();
                imageData = ConsoleGfx.loadFile(userInputFileName);
                // Load Test File
            } else if (option == 2) {
                System.out.println("Test image data loaded.");
                //storedData = ConsoleGfx.testImage;
                imageData = (ConsoleGfx.testImage);
                // Read RLE String
            } else if (option == 3) {
                System.out.print("Enter an RLE string to be decoded: ");
                String userInput = scanner.next();
                imageData = decodeRle((stringToRle(userInput)));
                // Read Hex String
            } else if (option == 4) {
                System.out.print("Enter the hex string holding RLE data: ");
                String userInput = scanner.next();
                imageData = decodeRle(stringToData(userInput));
                // Read Data Hex String
            } else if (option == 5) {
                System.out.print("Enter the hex string holding flat data: ");
                String userInput = scanner.next();
                imageData = decodeRle(stringToData(userInput));
                // Display Image
            } else if (option == 6) {
                System.out.println("Displaying image...");
                //ConsoleGfx.displayImage(storedData);
                ConsoleGfx.displayImage(imageData);
                // Display RLE String
            } else if (option == 7) {
                System.out.println("RLE representation: " + toRleString(encodeRle(imageData)));
                // Display Hex RLE Data
            } else if (option == 8) {
                System.out.println("RLE hex values: " + toHexString(encodeRle(imageData)));
                // Display Hex Flat Data
            } else if (option == 9) {
                System.out.println("Flat hex values: " + toHexString(imageData));
            }
            // Exit
        } while (option != 0);

    }

    // Translates data to a hexadecimal string
    public static String toHexString(byte[] data) {
        String hexString = null;
        // Translation from a double-digit number to a letter
        for (byte number : data) {
            if (number == 10) {
                // There is an if statement for null because null will be added in the string if the string itself was not set differently.
                if (hexString == null) {
                    hexString = "a";
                } else {
                    hexString += "a";
                }
            } else if (number == 11) {
                if (hexString == null) {
                    hexString = "b";
                    // Just add the letter to the string after the first character is defined.
                } else {
                    hexString += "b";
                }
            } else if (number == 12) {
                if (hexString == null) {
                    hexString = "c";
                } else {
                    hexString += "c";
                }
            } else if (number == 13) {
                if (hexString == null) {
                    hexString = "d";
                } else {
                    hexString += "d";
                }
                // This process continues to 15 where the max character is 15.
            } else if (number == 14) {
                if (hexString == null) {
                    hexString = "e";
                } else {
                    hexString += "e";
                }
            } else if (number == 15) {
                if (hexString == null) {
                    hexString = "f";
                } else {
                    hexString += "f";
                }
                // Simply add the numbers to the string
            } else {
                if (hexString == null) {
                    hexString = String.valueOf(number);
                } else {
                    hexString += String.valueOf(number);
                }
            }
        }
        return hexString;
    }

    // Return number of runs of data in an image data set
    public static int countRuns(byte[] flatData) {
        int group = 1;
        int count = 1;
        for (int i = 0; i < flatData.length - 1; i++) {
            if (flatData[i] == flatData[i + 1]) {
                count++;
            } else {
                count = 1;
                group++;
            }
            if (count > 15) {
                group++;
                count = 1;
            }
        }
        return group;
    }

    // Returns encoding of raw data passed in
    public static byte[] encodeRle(byte[] flatData) {
        byte count = 1;
        byte countCount = 0;
        byte value;
        // The array is defined with the number of runs plus 2 to ensure it is limited to it defined range.
        byte[] res = new byte[countRuns(flatData) * 2];
        for (int i = 0; i < flatData.length - 1; i++) {
            value = flatData[i];
            if (flatData[i] == flatData[i + 1] && count < 15) {
                count++;
            } else {
                res[countCount] = count;
                count = 1;
                res[countCount + 1] = value;
                countCount += 2;
            }
        }
        value = flatData[flatData.length - 1];
        // This statement outside the loop is used to count the last two numbers that the loop could not achieve.
        res[res.length - 1] = flatData[flatData.length - 1];
        res[countCount] = count;
        res[countCount + 1] = value;
        return res;
    }

    // Returns decompressed size RLE data
    public static byte getDecodedLength(byte[] rleData) {
        byte res = 0;
        for (int i = 0; i < rleData.length - 1; i++) {
            if ((i % 2) == 0) {
                res += rleData[i];
            }
        }
        return res;
    }

    // Returns the decoded data set from RLE encoded data
    public static byte[] decodeRle(byte[] rleData) {
        // size of the output
        byte index = 0;
        byte[] res = new byte[getDecodedLength(rleData)];
        for (byte i = 0; i < rleData.length; i += 2) {
            byte value = rleData[i + 1]; // item in the odd indices e.g. 15
            int repeats = rleData[i]; // item in the even indices before 15, e.g. 3
            for (int j = 0; j < repeats; j++) {
                res[index] = value;
                index++;
            }
        }
        return res;
    }

    // Translates a string in hexadecimal format into byte data.
    public static byte[] stringToData(String dataString) {
        byte[] strToData = new byte[dataString.length()];
        for (int i = 0; i < dataString.length(); i++) {
            String value;
            value = String.valueOf(dataString.charAt(i));
            strToData[i] = Byte.parseByte(value, 16);
        }
        return strToData;
    }

    // Translates RLE data into a human-readable representation
    public static String toRleString(byte[] rleData) {
        String hexString = null;
        int spacer = 1;
        int digit = 1;
        // Translation from a double-digit number to a letter
        for (byte number : rleData) {
            if (digit == 2) {
                if (number == 10) {
                    // There is an if statement for null because null will be added in the string if the string itself was not set differently.
                    if (hexString == null) {
                        hexString = "a";
                    } else {
                        hexString += "a";
                    }
                } else if (number == 11) {
                    if (hexString == null) {
                        hexString = "b";
                        // Just add the letter to the string after the first character is defined.
                    } else {
                        hexString += "b";
                    }
                } else if (number == 12) {
                    if (hexString == null) {
                        hexString = "c";
                    } else {
                        hexString += "c";
                    }
                } else if (number == 13) {
                    if (hexString == null) {
                        hexString = "d";
                    } else {
                        hexString += "d";
                    }
                    // This process continues to 15 where the max character is 15.
                } else if (number == 14) {
                    if (hexString == null) {
                        hexString = "e";
                    } else {
                        hexString += "e";
                    }
                } else if (number == 15) {
                    if (hexString == null) {
                        hexString = "f";
                    } else {
                        hexString += "f";
                    }
                    // Simply add the numbers to the string that are not greater than 9
                } else {
                    if (hexString == null) {
                        hexString = String.valueOf(number);
                    } else {
                        hexString += String.valueOf(number);
                    }
                }
                digit = 1;
            } else {
                if (hexString == null) {
                    hexString = String.valueOf(number);
                } else {
                    hexString += String.valueOf(number);
                }
                digit++;
            }
            // This if statement is supposed to represent a placement for the colon after the runs
            if ((spacer % 2 == 0) && (spacer > 0) && (spacer != rleData.length)) {
                hexString += ":";
            }
            spacer++;
        }
        return hexString;
    }

    // Translates a string from RLE format with delimiters into RLE byte data
    public static byte[] stringToRle(String rleString) {
        // Used to count the length number
        int arrayLength = 0;
        for (int i = 0; i < rleString.length(); i++) {
            if (rleString.charAt(i) == ':') {
                arrayLength++;
            }
        }
        // Turns the data into a string array but filters out the colons
        String[] stringRle = new String[(arrayLength + 1) * 2];
        String[] rawRleData = rleString.split(":");
        for (int i = 0; i < rawRleData.length; i++) {
            if (rawRleData[i].length() == 2) {
                stringRle[i * 2] = rawRleData[i].substring(0, 1);
                stringRle[(i * 2) + 1] = rawRleData[i].substring(1, 2);
            } else if (rawRleData[i].length() == 3) {
                stringRle[i * 2] = rawRleData[i].substring(0, 2);
                stringRle[(i * 2) + 1] = rawRleData[i].substring(2, 3);
            }
        }
        // Convert the letters in the array into numbers
        for (int j = 0; j < stringRle.length; j++) {
            // Converts all upper case to lowercase to make it easier to read
            stringRle[j] = stringRle[j].toLowerCase();
            switch (stringRle[j]) {
                case "a":
                    stringRle[j] = "10";
                    break;
                case "b":
                    // For an example, "B" will turn into "b". Now "b" will turn into 11. The number 11 will be placed into the string for the j it is at.
                    stringRle[j] = "11";
                    break;
                case "c":
                    stringRle[j] = "12";
                    break;
                case "d":
                    stringRle[j] = "13";
                    break;
                case "e":
                    stringRle[j] = "14";
                    break;
                case "f":
                    stringRle[j] = "15";
                    break;
            }
        }
        // Convert the string array into a byte array
        byte[] res = new byte[(arrayLength + 1) * 2];
        for (int j = 0; j < res.length; j++) {
            res[j] = Byte.parseByte(stringRle[j]);
        }
        return res;
    }
}
