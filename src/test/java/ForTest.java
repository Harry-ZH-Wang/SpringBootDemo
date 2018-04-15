import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 循环移除测试
 */
public class ForTest {

    public static void main(String[] args) {
        List<String> l1 = new ArrayList<String>();
        List<String> l2 = new ArrayList<String>();
        List<String> l3 = new ArrayList<String>();

        l1.add("1");
        l1.add("1");
        l1.add("2");
        l1.add("3");

        l2.add("2");
        l2.add("1");
        l2.add("2");
        l2.add("3");
        l2.add("4");
        l2.add("2");

        for(int i = 0; i < l1.size(); i++)
        {
            Iterator<String> itr = l2.iterator();
            while(itr.hasNext())
            {
                String temp = itr.next();
                if(temp.equals(l1.get(i)))
                {
                    l3.add(temp);
                    itr.remove();
                }
            }
        }
        System.out.println(l3);
    }
}
