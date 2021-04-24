package com.igeek.bams.dao;

import com.igeek.bams.entity.Account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @Description 给集合容器操作的层
 * @Author chenmin
 * @Date 2020/10/29 9:42
 */
public class ListDao implements IDao<Account, List<Account>>{

    //集合容器，用来存储Account
    private List<Account> accs = new ArrayList<>();
    //id的初始值
    private Long id = 100000000000000000L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //给数组中添加元素
    public void insert(Account acc){
        accs.add(acc);
    }

    //从数组中查询元素 （id和密码）
    public Account selectOne(Long id,String password){
        for (int i = 0; i <accs.size(); i++) {
            if(id.equals(accs.get(i).getId()) && password.equals(accs.get(i).getPassword())) {
                return accs.get(i);
            }
        }
        return null;
    }

    //从数组中查询元素 (id)
    public Account selectOne(Long id){
        for (Account acc : accs) {
            if(acc!=null){
                if(id.equals(acc.getId())){
                    return acc;
                }
            }
        }
        return null;
    }

    //查询所有账户信息
    public List<Account> selectAll(){
        return accs;
    }

    //删除
    @Override
    public void delete(Long id) {

    }

    //更新
    @Override
    public void update(Account acc) {

    }

    /**
     * 自动生成id  86215021(0~7) 2013(8~11) 02(12~13) 0001(14~17)
     * @return
     */
    @Override
    public Long createId() {
        //获取账户id使用
        Long id = this.getId();
        String idStr = id.toString();

        //获取当前年
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        //切割id字符串
        String yearStr = idStr.substring(8,12);
        String monthStr = idStr.substring(12,14);
        String count = idStr.substring(14);

        //若切割出来的数据年不是当前年 或者 月不是当前月
        if(!yearStr.equals(String.valueOf(year)) || !monthStr.equals(String.valueOf(month))){
            yearStr = String.valueOf(year);
            monthStr = String.valueOf(month);
            count = "0001";
        }

        if(count.equals("9999")){
            System.out.println("当月开户名额已满，请次月再来！");
            return -1L;
        }

        //重新组合idStr
        if (month<10){
            idStr = "86215021"+yearStr+"0"+monthStr+count;
        }else{
            idStr = "86215021"+yearStr+monthStr+count;
        }

        id = Long.valueOf(idStr); //86215021 2020 11 0001
        //加1存储
        this.setId(id+1);
        return id;
    }
}
