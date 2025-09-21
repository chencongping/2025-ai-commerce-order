package com.ai.agent.repository;

import com.ai.agent.entity.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    
    // 根据商品名称查询评价
    List<Testimonial> findByProduct(String product);
    
    // 查询评分大于等于指定值的评价
    List<Testimonial> findByRatingGreaterThanEqual(Integer rating);
    
    // 查询最新的N条评价
    @Query("SELECT t FROM Testimonial t ORDER BY t.id DESC")
    List<Testimonial> findLatestTestimonials(@Param("limit") Integer limit);
}