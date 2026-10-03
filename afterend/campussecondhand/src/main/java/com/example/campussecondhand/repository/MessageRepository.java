package com.example.campussecondhand.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campussecondhand.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MessageRepository extends BaseMapper<Message> {

    @Select("SELECT * FROM messages WHERE receiver_id = #{userId} ORDER BY created_time DESC")
    List<Message> findByReceiverId(@Param("userId") Long userId);

    @Select("SELECT * FROM messages WHERE sender_id = #{userId} ORDER BY created_time DESC")
    List<Message> findBySenderId(@Param("userId") Long userId);

    @Select("SELECT * FROM messages WHERE (sender_id = #{user1Id} AND receiver_id = #{user2Id}) OR (sender_id = #{user2Id} AND receiver_id = #{user1Id}) ORDER BY created_time ASC")
    List<Message> findConversation(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Select("SELECT COUNT(*) FROM messages WHERE receiver_id = #{userId} AND is_read = 0")
    int countUnread(@Param("userId") Long userId);
}
