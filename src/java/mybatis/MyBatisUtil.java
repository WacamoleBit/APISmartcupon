/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybatis;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Dell
 */
public class MyBatisUtil {
    
    //Recursos para la conexion con la base de datos
    public static final String RESOURCE = "mybatis/mybatis-config.xml";
    public static final String ENVIRONMENT = "development";
    
    //Método para la conexion con la base de datos
    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, ENVIRONMENT);
            session = sqlMapper.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
   
        return session;
    }

}
