package com.booklog.member.dao;


import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.model.dto.UpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface MemberDao {
    void saveRefreshToken(Map<String, Object> map) throws Exception;
    void deleteRefreshToken(Map<String, Object> map);
    void updateMember(UpdateDto updateDto);

    void deleteMember(int memberNo);
}
