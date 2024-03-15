package com.Tools.toolrental.Repositories;

import com.Tools.toolrental.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolBrandRepo extends JpaRepository<Brand,String> {
}
