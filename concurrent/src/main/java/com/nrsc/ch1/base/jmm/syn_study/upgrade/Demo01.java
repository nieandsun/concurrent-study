package com.nrsc.ch1.base.jmm.syn_study.upgrade;

public class Demo01 {

    public static void main(String[] args) {
        contactString("aa", "bb", "cc");
    }

    public static String contactString(String s1, String s2, String s3) {
        return new StringBuffer().append(s1).append(s2).append(s3).toString();
    }
}

class Demo02 {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        //StringBuffer是同步方法，
        // 其实没必要每次append都去判断锁相关的内容，可以将整个for循环搞成同步的 ---> JVM的锁粗化可能会直接帮你这样弄
        for (int i = 0; i < 100; i++) {
            sb.append("aa");
        }
        System.out.println(sb.toString());
    }
}