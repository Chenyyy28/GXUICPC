package manage.gxuicpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.common.mail.SendMail;
import manage.gxuicpc.entity.Contest;
import manage.gxuicpc.entity.ContestPage;
import manage.gxuicpc.entity.dto.ContestNoticeDTO;
import manage.gxuicpc.entity.vo.ContestVO;
import manage.gxuicpc.mapper.ContestMapper;
import manage.gxuicpc.mapper.RankMapper;
import manage.gxuicpc.mapper.UserMapper;
import manage.gxuicpc.service.ContestService;
import manage.gxuicpc.utils.RedisUtils;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestMapper contestMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RankMapper rankMapper;
    @Autowired
    SendMail sendMail;
    private static final String redisKey = RedisUtils.KEY + "contest";


    @QueryRedis(key = redisKey, id = true, page = true, resType = PageInfo.class)
    @Override
    public PageInfo<List<ContestVO>> getContests(ContestPage contestPage) {
        PageHelper.startPage(contestPage.getCurrentPage(), contestPage.getPageSize());
        List<ContestVO> contests = contestMapper.getContest(contestPage);
        PageInfo pageInfo = new PageInfo<>(contests);
        pageInfo.setTotal(contestMapper.getContest(contestPage).size());
        return pageInfo;
    }

    @Override
    @DeleteRedis(key = redisKey, id = true)
    public long addContest(Contest contest) {
        contest.setCreateTime(LocalDateTime.now());
        try {
            return contestMapper.addContest(contest);
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional
    @Override
    @DeleteRedis(key = redisKey, id = true)
    public long updateContest(Contest contest) {
        try {
            contestMapper.deleteContest(contest.getId());
            return addContest(contest);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @DeleteRedis(key = redisKey, id = true)
    public long deleteContest(Long id) {
        try {
            contestMapper.deleteContest(id);
            rankMapper.deleteFactors(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int sendNoticeEmail(ContestNoticeDTO contestNoticeDTO) {
        Integer role = contestMapper.getContestById(contestNoticeDTO.getContestId()).getRoleId();
        log.info(String.valueOf(role));
        List<String> emails = userMapper.getUserEmail(role);
        log.info(Arrays.toString(emails.toArray()));
        sendMail.build(emails, contestNoticeDTO.getSubject(), contestNoticeDTO.getContent());
        sendMail.send();
        return 1;
    }
}