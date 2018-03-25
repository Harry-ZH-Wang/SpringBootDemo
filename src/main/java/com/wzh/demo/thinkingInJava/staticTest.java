package com.wzh.demo.thinkingInJava;


class cup{
    public cup(int num)
    {
        System.out.println("cup:" + num);
    }

    void f(int num)
    {
        System.out.println("f:" + num);
    }
}

class cups
{
    static cup cup1;
    static cup cup2;
    static
    {
        cup1 = new cup(1);
        cup2 = new cup(2);
    }

    public cups()
    {
        super();
        System.out.println("cups");
    }
}

public class staticTest {
    public static void main(String[] args) {
        System.out.println("star");
        cups.cup1.f(1);
    }

    static cups cups = new cups();
}