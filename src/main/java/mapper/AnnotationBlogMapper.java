package mapper;

import org.apache.ibatis.annotations.Select;
import pojo.Blog;

/**
 * mybatis3后提供了比较多的注解类来实现一个基于注解的映射器类，但是由于目前并不能完全覆盖所有的mybatis映射的功能，所以这个坑就不介绍了，统一使用XML配置映射关系就行
 *
 * 这是注解的API文档：http://www.mybatis.org/mybatis-3/zh/java-api.html
 */
public interface AnnotationBlogMapper {
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);
}
