package org.hero.threadpool;


/**
 * 枚举等于数据库。 One Two等于数据库的表的一行记录，age name 为属性字段
 */
public enum StudentEnum {
    One(1,"zhangshan"),Two(2,"wangwu"),three(3,"lisi");

    StudentEnum(int age, String name) {
        this.age=age;
        this.name=name;
    }

    private Integer age;
    private String name;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 取出数据库的一行记录
     * @param index
     * @return
     */
    public static StudentEnum getSearch(Integer index){
        for(StudentEnum se:StudentEnum.values()){
            if(se.getAge()==index){
                return se;
            }
        }
        return null;
    }


}
