package com.shsxt.crm.enums;

public enum Color {
    RED(0),BLUE(1),GREEN(2);

    private Integer number;

    /**
     * 带参构造 参数为number
     * @param number
     */
    private Color(Integer number){
        this.number=number;
    }

    /**
     * 对外界提供get方法 方法返回的是Integer类型的number
     * @return
     */
    public Integer getNumber() {
        return number;
    }
}
class Test{
    public static void main(String[] args) {
        System.out.println(Color.BLUE.getNumber());
    }
}
