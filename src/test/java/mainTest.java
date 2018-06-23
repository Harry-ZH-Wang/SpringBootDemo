import com.wzh.demo.domain.UserBean;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class mainTest {

    public <T> List<T> readFileWithLine(String regex,String simpleDateFormat,T obj) throws Exception
    {
        //解析后返回的数据集合
        List<T> clazzes = new ArrayList<T>();

        String str  = "123|234|2324|4234|4234";

        // 拆分行数据
        String [] line = str.split(regex);

        T t = (T) obj.getClass().getDeclaredConstructor().newInstance();

        // 获取文件属性数组
        Field[] fieldes = t.getClass().getDeclaredFields();

        // 循环排除final属性
        List<Field> fieldList = new ArrayList<Field>();
        for(Field cell : fieldes)
        {
            if(!Modifier.isFinal(cell.getModifiers()))
            {
                // 非final的属性才进行处理
                fieldList.add(cell);
            }
        }

        // 数据和对象映射要求完全对应，所以这里取对象下标
        for(int i = 0; i < fieldList.size(); i++)
        {
            Field field = fieldList.get(i);
            // 设置权限
            field.setAccessible(true);

            // 判断数据类型进行转换,这里只做了几种常见类型的判断，如果有需要可以继续添加
            String type = field.getType().getName();
            try {

                if("java.lang.Integer".equals(type) || "int".equals(type))
                {
                    field.set(obj,NumberUtils.toInt(line[i]));
                }
                else if("java.lang.Double".equals(type) || "double".equals(type))
                {
                    field.set(obj,NumberUtils.toDouble(line[i]));
                }
                else if("java.lang.Float".equals(type) || "float".equals(type))
                {
                    field.set(obj,NumberUtils.toFloat(line[i]));
                }
                else if("java.lang.Long".equals(type) || "long".equals(type))
                {
                    field.set(obj,NumberUtils.toLong(line[i]));
                }
                else if("java.lang.Short".equals(type) || "short".equals(type))
                {
                    field.set(obj,NumberUtils.toShort(line[i]));
                }
                else if("java.lang.Boolean".equals(type) || "boolean".equals(type))
                {
                    field.set(obj, BooleanUtils.toBoolean(line[i]));
                }
                else if("java.util.Date".equals(type) || "Date".equals(type))
                {
                    SimpleDateFormat sdf=new SimpleDateFormat(simpleDateFormat);
                    if(StringUtils.isBlank(line[i]))
                    {
                        field.set(obj, null);
                    }else{
                        field.set(obj, sdf.parse(line[i]));
                    }
                }
                else {
                    field.set(obj, line[i]);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        // 添加数据
        clazzes.add((T) obj);



        return  clazzes;
    }
    public static void main(String[] args) {

        mainTest t = new mainTest();
        UserBean bean = new UserBean();
        try {
           List<UserBean> b = t.readFileWithLine("\\|",null,bean);
            System.out.println(b);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
