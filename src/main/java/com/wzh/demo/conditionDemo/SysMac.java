package com.wzh.demo.conditionDemo;

public class SysMac implements Computer{

    public SysMac() {
        super();
        //System.out.println(this.price());
    }

    public String price()
    {
        Contants.computerPrice = "10000";

        return "当前操作系统：" + Contants.system + " 电脑价格" + Contants.computerPrice;
    }
}


