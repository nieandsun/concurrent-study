package com.nrsc.ch1.base.jmm.syn_study.deep;

import org.openjdk.jol.info.ClassLayout;

public class JavaObjTest {

    public static void main(String[] args) {
        JavaObj javaObj = new JavaObj();
        //调用javaObj的hashCode方法
        int hashCode = javaObj.hashCode();
        //将hashCode转为16进制，因为打印的对象头里可以看出16进制的存储数据
        System.out.println(Integer.toHexString(hashCode));
        //将hashCode转为2进制，因为打印的对象头里也可以看出2进制的存储数据
        System.out.println(Integer.toBinaryString(hashCode));
        //打印javaObj的对象布局
        System.err.println(ClassLayout.parseInstance(javaObj).toPrintable());
    }
}
