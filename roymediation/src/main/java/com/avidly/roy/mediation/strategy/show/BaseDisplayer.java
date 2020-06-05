package com.avidly.roy.mediation.strategy.show;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.show
 * @ClassName: BaseDisplayer
 * @Description: BaseDisplayer
 * @Author: Roy
 * @CreateDate: 2020/6/5 11:41
 */

public abstract class BaseDisplayer {

    public abstract DisplayStrategy getDisplayeStrategy();
    public abstract void display();
}
