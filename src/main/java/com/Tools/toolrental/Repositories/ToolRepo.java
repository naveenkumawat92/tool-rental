package com.Tools.toolrental.Repositories;

import com.Tools.toolrental.Model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepo extends JpaRepository<Tool,String> {

    @Query(nativeQuery = true, value = "SELECT * from tools WHERE tool_code = :code ")
    public Optional<Tool> findByToolCode(String code);
}
