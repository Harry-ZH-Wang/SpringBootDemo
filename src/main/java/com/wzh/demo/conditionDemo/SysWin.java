package com.wzh.demo.conditionDemo;

public class SysWin implements Computer{

    public SysWin() {
        super();
        //System.out.println(this.price());
    }

    @Override
    public String price()
    {
        Contants.computerPrice = "7000";
        return "当前操作系统：" + Contants.system + " 电脑价格" + Contants.computerPrice;
    }

}
