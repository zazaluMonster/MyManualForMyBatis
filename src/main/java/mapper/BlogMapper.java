package mapper;


import org.apache.ibatis.annotations.MapKey;
import pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * mapper类的例子 ，mapper类很简单，保证和mapper.xml中一一对应即可
 */
public interface BlogMapper {

    Blog selectBlog(int id);

    List<Blog> selectBlogs();

    @MapKey("id")
    Map<Integer, Blog> selectMapBlogs();

    int insertBlog(Blog Blog);

    int updateBlog(Blog Blog);

    int deleteBlog(int id);
}


