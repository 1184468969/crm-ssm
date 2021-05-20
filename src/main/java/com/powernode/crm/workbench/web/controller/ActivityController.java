package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.settings.service.impl.UserServiceImpl;
import com.powernode.crm.utils.DateTimeUtil;
import com.powernode.crm.utils.PrintJson;
import com.powernode.crm.utils.ServiceFactory;
import com.powernode.crm.utils.UUIDUtil;
import com.powernode.crm.vo.PaginationVO;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.ActivityRemark;
import com.powernode.crm.workbench.service.ActivityService;
import com.powernode.crm.workbench.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController extends HttpServlet {

    public void start(){
        System.out.println("进入到市场活动控制器");
    }
    @Resource
    private ActivityService as;

    @Resource
    private UserService us;
    /*
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");

        String path = request.getServletPath();

        if("/workbench/activity/getUserList.do".equals(path)){

            getUserList(request,response);

        }else if("/workbench/activity/save.do".equals(path)){

            save(request,response);

        }else if("/workbench/activity/pageList.do".equals(path)){

            pageList(request,response);

        }else if("/workbench/activity/delete.do".equals(path)){

            delete(request,response);

        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)){

            getUserListAndActivity(request,response);

        }else if("/workbench/activity/update.do".equals(path)){

            update(request,response);

        }else if("/workbench/activity/detail.do".equals(path)){

            detail(request,response);

        }else if("/workbench/activity/getRemarkListByAid.do".equals(path)){

            getRemarkListByAid(request,response);

        }else if("/workbench/activity/deleteRemark.do".equals(path)){

            deleteRemark(request,response);

        }else if("/workbench/activity/saveRemark.do".equals(path)){

            saveRemark(request,response);

        }else if("/workbench/activity/updateRemark.do".equals(path)){

            updateRemark(request,response);

        }

    }*/

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    private Map<String,Object> updateRemark(HttpSession session, ActivityRemark ar) {
        System.out.println("执行修改备注的操作");

        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)session.getAttribute("user")).getName();
        //表示已经修改过
        String editFlag = "1";

        ar.setEditFlag(editFlag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);

        boolean flag = as.updateRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);

        return map;
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    private Map<String,Object> saveRemark(HttpSession session, ActivityRemark ar) {
        System.out.println("执行添加备注操作");

        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)session.getAttribute("user")).getName();
        String editFlag = "0";

        ar.setId(id);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);

        boolean flag = as.saveRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);

        return map;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    private boolean deleteRemark(String id) {
        System.out.println("执行删除备注操作");

        boolean flag = as.deleteRemark(id);

        return flag;
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    private List<ActivityRemark> getRemarkListByAid(String activityId) {
        System.out.println("根据市场活动id，取得备注信息列表");

        System.out.println("+++++++++"+activityId);

        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        return arList;
    }

    @RequestMapping("/detail.do")
    private ModelAndView detail(String id){
        System.out.println("进入到跳转到详细信息页的操作");

        Activity a = as.detail(id);

        ModelAndView mv = new ModelAndView();

        //这里的key可以让前端用el表达式获取到。
        mv.addObject("a",a);

        mv.setViewName("forward:/workbench/activity/detail.jsp");

        return mv;
    }

    @RequestMapping("/update.do")
    @ResponseBody
    private boolean update(HttpSession session, Activity a) {
        System.out.println("执行市场活动修改操作");

        //修改时间，当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人，当前登录用户
        String editBy = ((User)session.getAttribute("user")).getName();

        a.setEditTime(editTime);
        a.setEditBy(editBy);

        boolean flag = as.update(a);

        return flag;
    }

    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    private Map<String,Object> getUserListAndActivity(String id) {
        System.out.println("进入到查询用户信息列表和根据市场活动id查询单条记录的操作");

        /*
            总结：
                controller调用service的方法，返回值应该是什么
                想一想前端需要什么，就从service层取什么

            前端需要的，管业务层去要
            uList
            a(市场活动对象)

            以上两项信息，复用率不高，选择使用map打包这两项信息即可
            map
         */
        Map<String,Object> map = as.getUserListAndActivity(id);

        return map;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    private boolean delete(String[] id) {
        System.out.println("执行市场活动删除操作");

        boolean flag = as.delete(id);

        return flag;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    private PaginationVO<Activity> pageList(Activity a, Integer pageNo, Integer pageSize) {
        System.out.println("进入到查询市场活动信息列表的操作(结合条件查询+分页查询)");

        String name = a.getName();
        String owner = a.getOwner();
        String startDate = a.getStartDate();
        String endDate = a.getEndDate();
        //pageSize每页展现的记录数
        //计算除略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        // 因为以下两条信息不在domain类中,所以选择使用map进行传值
        // (<parameterType>传值不能使用vo类,<resultType>传值可以使用vo类)
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        /*
            前端要：市场活动信息列表
                   查询的总条数

                   业务层拿到了以上两项信息之后，如果要做返回
                   map
                   map.put("dataList":dataList)
                   mao.put("total":total)
                   PrintJSON map --> json
                   {"total":100,"dataList":[{市场活动1},{2},{3}]}

                   vo
                   PaginationVo<T>
                       private int total;
                       private List<T> dataList;

                   PaginationVO<Activity> vo = new PaginationVO<>;
                   vo.setTotal(total);
                   vo.setTotal(dataList);
                   PrintJSON vo --> json
                   {"total":100,"dataList":[{市场活动1},{2},{3}]}

                   将来分页查询，每个模块都有，所以我们 选择使用一个通用vo，操作起来比较方便
         */
        PaginationVO<Activity> vo = as.pageList(map);

        //vo--> {"total":100,"dataList":[{市场活动1},{2},{3}]}
        return vo;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    private boolean save(HttpSession session, HttpServletRequest request, HttpServletResponse response, Activity a) {
        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人，当前登录用户
        String createBy = ((User)session.getAttribute("user")).getName();

        a.setId(id);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        boolean flag = as.save(a);

        return flag;
    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    private List<User> getUserList(HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        List<User> uList = us.getUserList();

        return uList;
    }
}

