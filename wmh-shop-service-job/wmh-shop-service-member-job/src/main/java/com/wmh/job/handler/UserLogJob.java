package com.wmh.job.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wmh.job.domain.UserLoginLogDo;
import com.wmh.job.service.UserLoginLogService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserLogJob {
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Value("${job.pageSize}")
    private Integer pageSize;

    /**
     * 微信活动
     *
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("userLoginLogHandler")
    public ReturnT<String> weChatActivitieJobHandler(String param) throws Exception {

        try {
            // 获取分片的数量
            ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
            int index = shardingVO.getIndex();
            log.info(">>>定时任务开始出发<<<param:{},index:{},pageSize:{}", param, index, pageSize);
            index = ((index + 1) - 1) * pageSize;
//            Page<UserLoginLogDo> page = new Page<>(index, pageSize);
//            IPage<UserLoginLogDo> ipage = userLoginLogService.page(page);
            List<UserLoginLogDo> iPage = userLoginLogService.selectByPage(index, pageSize);
            log.info("userDos:" + JSONObject.toJSONString(iPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnT.SUCCESS;
    }
}

