package com.openup.app3.arithmetic;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.arithmetic
 * @ClassName: SimpleSelectionSort
 * @Description: SimpleSelectionSort
 * @Author: Roy
 * @CreateDate: 2020/3/26 14:25
 */

public class SimpleSelectionSort {


    private void getResult(int[] arr){
        int n=arr.length;
        for (int i = 0; i < n; i++) {
            int min=i;
            for (int j=i+1;j<n;j++){
                if (arr[j]<arr[min]){
                    min=j;
                }
            }
            if (min!=i){
                int temp=arr[i];
                arr[i]=arr[min];
                arr[min]=temp;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println("result is "+arr[i]);
        }

    }

    public static void main(String[] args) {
        int[] ins = {1,2,3,4,656,67,7,5,43};
        SimpleSelectionSort simpleSelectionSort=new SimpleSelectionSort();
        simpleSelectionSort.getResult(ins);
    }
}
