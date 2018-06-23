package domin;

import java.util.Date;

/**
 * <一句话功能描述>
 * <功能详细描述>
 *
 * @author wzh
 * @version 2018-06-18 16:43
 * @see [相关类/方法] (可选)
 **/
public class User {

    private String name;

    private int age;

    private Date birthday;

    public User() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
