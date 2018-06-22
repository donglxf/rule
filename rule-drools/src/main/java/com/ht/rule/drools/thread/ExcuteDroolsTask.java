package com.ht.rule.drools.thread;

import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.vo.model.drools.DroolsActionForm;
import com.ht.rule.drools.facade.DroolsRuleEngineFacade;
import lombok.extern.log4j.Log4j2;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * <P>
 * description:
 * </p>
 * @author 张鹏
 * @since 2018/6/4 18:52
 */
@Log4j2
public class ExcuteDroolsTask extends RecursiveTask<List< List<DroolsActionForm> >> {

    /**
     * 每个小任务 最多只累加2张图片
     */
    private static final int THRESHOLD = 4;
    private SceneInfoVersion sceneInfoVersion;
    private List<Map<String, Object>> datas;
    private DroolsRuleEngineFacade droolsRuleEngineFacade;
    private int type;
    private int start;
    private int end;

    public ExcuteDroolsTask(DroolsRuleEngineFacade droolsRuleEngineFacade,List<Map<String, Object>> datas,  int start, int end, SceneInfoVersion sceneInfoVersion,int type) {
        this.droolsRuleEngineFacade = droolsRuleEngineFacade;
        this.datas = datas;
        this.start = start;
        this.end = end;
        this.sceneInfoVersion = sceneInfoVersion;
        this.type = type;
    }
    @Override
    protected List<List<DroolsActionForm>> compute() {
        List<List<DroolsActionForm>> resList = new ArrayList<>();
        //判断图片数,如果图片数小于3,则只需要一个线程压缩,否则对任务进行分解
        if ((end - start) < THRESHOLD) {
            for (int i = start; i < end; i++) {
                Map<String, Object> data = this.datas.get(i);
                try {
                    List<DroolsActionForm> actionForms =  droolsRuleEngineFacade.excuteAll(sceneInfoVersion,data,type);
                    resList.add(actionForms);
                    log.info("第" + i + "个规则引擎执行成功");
                } catch (Exception e) {
                    resList.add(null);
                    log.error("规则执行异常",e);
                }
            }
            return resList;
        } else {
            //当end与start之间的差大于threshold，即要累加的数超过20个时候，将大任务分解成小任务
            int middle = (start + end) / 2;
            ExcuteDroolsTask left =  new ExcuteDroolsTask(droolsRuleEngineFacade,datas, start, middle, sceneInfoVersion,type);
            ExcuteDroolsTask right = new ExcuteDroolsTask(droolsRuleEngineFacade,datas, middle, end, sceneInfoVersion,type);
            invokeAll(left, right);
            //把两个小任务累加的结果合并起来
            resList.addAll(left.join());
            resList.addAll(right.join());
        }
        return resList;
    }
}
