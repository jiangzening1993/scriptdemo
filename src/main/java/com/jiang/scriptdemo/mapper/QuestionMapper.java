package com.jiang.scriptdemo.mapper;

import com.jiang.scriptdemo.entity.Question;
import com.jiang.scriptdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Select("insert into question(title,description,createid,tag,createtime,fileid) values (#{title},#{description},#{createid},#{tag},#{createtime},#{fileid)")
    void createquestion(Question question);

    @Select("select * from question order by createtime desc limit #{offset},#{size} ")
    List<Question> list(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(1) from question")
    int count();

    @Select("select count(1) from question where createid=#{userid}")
    int countbyid(int userid);

    @Select("select * from question where id=#{id}")
    Question getbyId(int id);

    @Select("select * from question where createid=#{userid} limit #{offset},#{size}")
    List<Question> listbyid(@Param("userid") int userid, @Param("offset") int offset, @Param("size") int size);

    @Update("update question set title=#{title},description=#{description},tag=#{tag},createtime=#{createtime} where id=#{id}")
    void updatequestion(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateView(int id);

    @Update("update question set comment_count=comment_count+1 where id=#{parent_id}")
    void updatecomment(int parent_id);

    @Select("select * from question where tag REGEXP #{result} and id!=#{id} limit 0,10")
    List<Question> getbytag(@Param("id") int id, @Param("result") String result);

    @Select("select title from question where id=#{outerid}")
    String gettitlebyid(int outerid);

    @Select("select * from question order by view_count desc limit 0,10")
    List<Question> gettopten();


}