package tools;

public class Tools {
    public static char[] toPrimitiveCharArray(Character[] charArray) {
        char[] resultArray = new char[charArray.length];

        // Iterate through each element and extract the char value
        for (int i = 0; i < charArray.length; i++) {
            resultArray[i] = charArray[i];
        }

        return resultArray;
    }
}
