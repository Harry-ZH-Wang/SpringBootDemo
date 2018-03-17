import com.wzh.demo.domain.UserBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class mainTest {

    public static void main(String[] args) {
        //测试数据
        List<UserBean> users = new ArrayList<UserBean>();
        UserBean bean1 = new UserBean();
        bean1.setName("张1");
        bean1.setAge(11L);
        UserBean bean2 = new UserBean();
        bean2.setName("张2");
        bean2.setAge(22L);
        UserBean bean3 = new UserBean();
        bean3.setName("张3");
        bean3.setAge(33L);
        UserBean bean4 = new UserBean();
        bean4.setName("张1");
        bean4.setAge(44L);
        UserBean bean5 = new UserBean();
        bean5.setName("张5");
        bean5.setAge(55L);
        users.add(bean1);
        users.add(bean2);
        users.add(bean3);
        users.add(bean4);
        users.add(bean5);

        List<String> strList = new ArrayList<String>();
        strList.add("张1");
        strList.add("张3");
        //strList.add("张1");

//        for (int i = 0; i < strList.size(); i++)
//        {
//            for (int j = 0; j < users.size(); j++) {
//                if (strList.get(i).equals(users.get(j).getName()))
//                {
//                    System.out.println(users.get(j));
//                    //users.remove(j);
//                    //continue;
//                }
//            }
//        }
        Iterator<UserBean> it = users.iterator();
        while (it.hasNext())
        {
            UserBean temp = it.next();
            for (int i = 0; i < strList.size(); i++) {
                if(temp.getName().equals(strList.get(i)))
                {
                    System.out.println(temp);
                    it.remove();
                }
            }
        }
//        System.out.println(users);
//        for (int i = 0; i < users.size(); i++) {
//            for (int j = 0; j < strList.size(); j++) {
//                if(strList.get(j).equals(users.get(i).getName()))
//                {
//                    System.out.println(users.get(i));
//                }
//            }
//        }

    }
}
