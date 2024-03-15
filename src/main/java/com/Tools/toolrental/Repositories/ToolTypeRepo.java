package com.Tools.toolrental.Repositories;

import com.Tools.toolrental.Model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolTypeRepo extends JpaRepository<ToolType,String> {
}
