package api_demo;

import mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * get SqlSessionFactory ,And then we can use mybatis API with this obj
 */
public class StartMybatis {


    public static SqlSessionFactory originalStartMybatisByXML() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    public static SqlSessionFactory originalStartMybatisNotByXML() throws IOException {
        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

//        configuration部分配置java形式配置，这种形式是最高级的，会覆盖别的配置方式
//        configuration.setLazyLoadingEnabled(true);
//        configuration.setEnhancementEnabled(true);
//        configuration.getTypeAliasRegistry().registerAlias(Blog.class);
//        configuration.getTypeAliasRegistry().registerAlias(Post.class);
//        configuration.getTypeAliasRegistry().registerAlias(Author.class);
//        configuration.addMapper(BoundBlogMapper.class);
//        configuration.addMapper(BoundAuthorMapper.class);
//
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    public static void getDifferentSqlSessionFactoryByEnvironment(TransactionFactory mysqlTransaction,DataSource mysqlDataSource,TransactionFactory oracleTransaction, DataSource oracleDataSource) throws IOException {
        Environment mysqlEnv = new Environment("mysql", mysqlTransaction,mysqlDataSource);
        Environment oracleEnv = new Environment("mysql", oracleTransaction,oracleDataSource);

        Configuration mysqlConfig = new Configuration(mysqlEnv);
        Configuration oracleConfig = new Configuration(oracleEnv);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory mysql = new SqlSessionFactoryBuilder().build(mysqlConfig);
        SqlSessionFactory oracle = new SqlSessionFactoryBuilder().build(oracleConfig);
    }
}
