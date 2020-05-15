package com.lambda;

/**
 * @ProjectName: TestApplication
 * @Package: com.lambda
 * @ClassName: PersonCompareInterface
 * @Description: PersonCompareInterface
 * @Author: Roy
 * @CreateDate: 2020/4/7 16:41
 */

public interface PersonCompareInterface<T> {
    int compare(T t1, T t2);
}
