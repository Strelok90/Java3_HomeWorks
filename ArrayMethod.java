
public class ArrayMethod {

    public int[] returnNewArray (int[] arr) {
        int temp = 0;
        boolean isThereFour = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                temp = i;
                isThereFour = true;
            }
        }
        if (!isThereFour) throw new RuntimeException("Четверок в массиве нет");
        int[] newArr = new int[arr.length-(temp+1)];
        if (newArr.length>0) {
            for (int i = temp + 1, j = 0; i < arr.length; i++, j++) {
                newArr[j] = arr[i];
            }
        }
        return newArr;
    }

    public boolean isOneOrFour (int[] arr) {
        boolean isThereOne = false;
        boolean isThereFour = false;
        for (int i: arr) {
            if (i==1) isThereOne = true;
            else if (i==4) isThereFour = true;
            else return false;
        }
        return isThereOne &&  isThereFour;
    }
}