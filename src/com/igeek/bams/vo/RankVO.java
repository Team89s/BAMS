package com.igeek.bams.vo;

/**
 * @version 1.0
 * @Description 排行的辅助类
 * @Author chenmin
 * @Date 2020/11/16 11:34
 *
 * 富豪排行榜
 * 身份证号（由低到高）    姓名     总资产（由高到低）
 *
 */
public class RankVO implements Comparable<RankVO>{

    private String personId;
    private String name;
    private double totalBalance;

    public RankVO() {
    }

    public RankVO(String personId, String name, double totalBalance) {
        this.personId = personId;
        this.name = name;
        this.totalBalance = totalBalance;
    }

    /**
     * 获取
     * @return personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * 设置
     * @param personId
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return totalBalance
     */
    public double getTotalBalance() {
        return totalBalance;
    }

    /**
     * 设置
     * @param totalBalance
     */
    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String toString() {
        return "RankVO{personId = " + personId + ", name = " + name + ", totalBalance = " + totalBalance + "}";
    }

    @Override
    public int compareTo(RankVO o) {
        if(this.totalBalance==o.totalBalance){
            return this.personId.compareTo(o.personId);
        }
        return Double.compare(o.totalBalance,this.totalBalance);
    }
}
