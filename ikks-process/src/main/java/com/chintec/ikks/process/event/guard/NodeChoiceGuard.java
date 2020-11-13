package com.chintec.ikks.process.event.guard;

import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.enums.ModelResultEnum;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.util.StringUtils;

/**
 * 一个实现了org.springframework.statemachine.guard.Guard的类
 * 重写了evaluate方法
 * 用于choice'的选择判断
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/12 11:22
 */
@Slf4j
public class NodeChoiceGuard implements org.springframework.statemachine.guard.Guard<NodeStateEnum, NodeStateChangeEnum> {
    @Override
    public boolean evaluate(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        log.info("进入选择:{}", context);
        FlowTaskStatusPo flowTaskStatusPo = context.getMessage().getHeaders().get("flowTaskStatusPo", FlowTaskStatusPo.class);
        assert flowTaskStatusPo != null;
        return (ModelResultEnum.RESULT_PASS.getCode() + "").equals(flowTaskStatusPo.getData().getHandleStatus());
    }
}
