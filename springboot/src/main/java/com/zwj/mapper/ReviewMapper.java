package com.zwj.mapper;

import com.zwj.pojo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewMapper {
    int insert(Review review);

    int deleteById(@Param("id") int id);

    int update(Review review);

    List<Review> selectAll();
}
