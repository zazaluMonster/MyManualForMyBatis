package api_demo;

import mapper.BlogMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import pojo.Blog;

import java.io.IOException;
import java.sql.Connection;

/**
 * use SqlSessionFactory to get SqlSession Instance,which is connect to your database.
 * We can use this obj to execute our sql statement which you are defined in mapper.xml
 * then SqlSession will return the DataObject which is holding the data we want.
 */
public class UseMybatis {


    public void getSqlSessionAndExecuteStatementUsingOldAPI() throws IOException {
        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
        } finally {
            session.close();
        }
    }


    public void getSqlSessionAndExecuteStatementUsingOfficalRecommend() throws IOException {
        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(101);
        } finally {
            session.close();
        }
    }

    /**
     *  默认的 openSession()方法没有参数，它会创建有如下特性的 SqlSession：
     *      1. 会开启一个事务（也就是不自动提交）。
     *      2. 将从由当前环境配置的 DataSource 实例中获取 Connection 对象。事务隔离级别将会使用驱动或数据源的默认设置。
     *      3. 预处理语句不会被复用，也不会批量处理更新
     * @throws IOException
     */
    public void getSqlSessionByDefault() throws IOException {

        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        SqlSession session = sqlSessionFactory.openSession();

    }

    public void getSqlSessionByOtherParameters(Connection connection) throws IOException {
        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        boolean autoCommit = true;
        SqlSession session1 = sqlSessionFactory.openSession(autoCommit);
        SqlSession session2 = sqlSessionFactory.openSession(connection);//传入自己的Connection类
        SqlSession session3 = sqlSessionFactory.openSession(TransactionIsolationLevel.REPEATABLE_READ);//设置事务级别
        /*
            ExecutorType.SIMPLE：这个执行器类型不做特殊的事情。它为每个语句的执行创建一个新的预处理语句。
            ExecutorType.REUSE：这个执行器类型会复用预处理语句。
            ExecutorType.BATCH：这个执行器会批量执行所有更新语句，如果 SELECT 在它们中间执行，必要时请把它们区分开来以保证行为的易读性。
         */
        SqlSession session4 = sqlSessionFactory.openSession(ExecutorType.REUSE);//设置执行器级别

        /*  其他可用API
            SqlSession openSession(ExecutorType execType,TransactionIsolationLevel level)
            SqlSession openSession(ExecutorType execType, boolean autoCommit)
            SqlSession openSession(ExecutorType execType, Connection connection)
         */

    }

    /**
     * 单独使用mybatis的时候，需要自行进行事务处理
     * 当设置autocommit为true的时候，也可以全部交给mybatis自己处理，默认的autoCommit是false的
     */
    public void useSqlSessionWithTransaction() throws IOException {
        /*  相关api
            void commit()
            void commit(boolean force)
            void rollback()
            void rollback(boolean force)
         */
        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // following 3 lines pseudocod for "doing some work"
            session.insert("");
            session.update("");
            session.delete("");
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     *  Mybatis 使用到了两种缓存：本地缓存（local cache）和二级缓存（second level cache）
     *  每当一个新 session 被创建，MyBatis 就会创建一个与之相关联的本地缓存。
     *  本地缓存会被增删改、提交事务、关闭事务以及关闭 session 所清空。
     *
     *  二级缓存在mapper文件里可以配置，这里不说
     */
    public void clearCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = StartMybatis.originalStartMybatisByXML();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // following 3 lines pseudocod for "doing some work"
            session.insert("");
            session.update("");
            session.delete("");
            session.commit();
            session.clearCache();
        } finally {
            session.close();
        }
    }

}
