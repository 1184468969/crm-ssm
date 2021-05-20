package com.powernode.crm.workbench.dao;

import com.powernode.crm.workbench.domain.ActivityRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ActivityRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
