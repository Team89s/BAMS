package com.igeek.bams.dao;

import com.igeek.bams.entity.Account;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Description TODO
 * @Author chenmin
 * @Date 2020/11/27 14:49
 */
public class FileDao implements IDao<Account, List<Account>> {

    //存储账户的ID
    File idFile = new File("id.txt");

    //存储账户信息Account
    File accFile = new File("account");

    public FileDao(){
        //创建文件
        if(!idFile.exists()){
            try {
                idFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //创建文件夹
        if(!accFile.exists()){
            accFile.mkdirs();
        }
    }

    //从文本中获取ID --> 读入 BufferedReader
    public Long getId(){
        //给ID赋初始值，第一次读取为空，则直接使用初始值
        Long id = 100000000000000000L;
        try (BufferedReader br = new BufferedReader(new FileReader(idFile))){
            String idStr = br.readLine();
            if(idStr!=null && !"".equals(idStr)){
                id = Long.valueOf(idStr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    //存储ID至文本中  --> 写出  PrintWriter
    public void setId(Long id){
        try (PrintWriter pw = new PrintWriter(idFile)){
            pw.println(id.toString());
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //序列化的方法  写出对象至文本中  ObjectOutputStream
    public void writeAcc(Account acc){
        File file = new File(accFile,acc.getId()+".txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(acc);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化的方法  读入对象至内存中 ObjectInputStream
    public Account readAcc(File accFile){
        Account acc = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(accFile))){
            acc = (Account)ois.readObject();
        } catch(EOFException e){
            //文件读到末尾
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return acc;
    }


    //查询  读入
    @Override
    public Account selectOne(Long id, String password) {
        Account account = this.selectOne(id);
        if(account!=null && password.equals(account.getPassword())){
            return account;
        }
        return null;
    }

    @Override
    public Account selectOne(Long id) {
        File[] files = accFile.listFiles();
        for (File file : files) {
            if(file.getName().contains(id.toString())){
                return readAcc(file);
            }
        }
        return null;
    }

    @Override
    public List<Account> selectAll() {
        List<Account> list = new ArrayList<>();
        File[] files = accFile.listFiles();
        for (File file : files) {
            list.add(readAcc(file));
        }
        return list;
    }

    //插入  写出
    @Override
    public void insert(Account acc) {
        writeAcc(acc);
    }

    //更新  写出
    @Override
    public void update(Account acc) {
        writeAcc(acc);
    }

    @Override
    public void delete(Long id) {

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
