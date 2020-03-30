package com.openup.app3.arithmetic;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.arithmetic
 * @ClassName: PopSort
 * @Description: 用于失效冒泡排序
 * @Author: Roy
 * @CreateDate: 2020/3/26 14:08
 */

public class PopSort {
    private static  int[] originalArr={6,3,8,2,9,1};

    private void getDestArr(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if (arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }

            }

        }
        for (int i = 0; i <arr.length; i++) {
            System.out.println("arr is "+arr[i]);
        }
    };

    public static void main(String[] args) {
        PopSort popSort=new PopSort();
        popSort.getDestArr(originalArr);
    }
}
