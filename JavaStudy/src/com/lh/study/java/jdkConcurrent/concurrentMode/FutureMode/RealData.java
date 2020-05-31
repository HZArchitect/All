package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode;

/**
 * 真实数据，这个真实数据构造可能很慢，需要用户等待很久
 */
public class RealData implements Data {

    protected final String result;

    /**
     * 构造器，构造返回值
     * @param para
     */
    public RealData(String para) {
        //构造需要一定的时间，使用sleep模拟
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < 10 ; i++) {
            sb.append(para + ":" + i + '\t');
            try {
                // 每次停止1毫秒，模拟很慢的过程
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
