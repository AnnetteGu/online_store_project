package com.annette.spring.project.online_store.repository;

import java.util.List;
import java.util.Map;

public interface CategoryRepoCustom {

    public List<Map<String, Object>> findAllCustom();

    public Map<String, Object> findByIdCustom(int id);
 
}
