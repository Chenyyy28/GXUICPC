package manage.gxuicpc.service.impl;

import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.common.PlatformEnum;
import manage.gxuicpc.entity.Account;
import manage.gxuicpc.entity.vo.AccountVO;
import manage.gxuicpc.mapper.AccountMapper;
import manage.gxuicpc.service.AccountService;
import manage.gxuicpc.utils.RedisUtils;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;
    private static final Long id = ThreadLocalUtil.getCurrentUser();
    private static final String redisKey = RedisUtils.KEY + "account:";

    @QueryRedis(key = redisKey, id = true, resType = AccountVO.class)
    @Override
    public AccountVO getAccount() {
        Long id = ThreadLocalUtil.getCurrentUser();
        List<Account> accounts = accountMapper.getAccountById(id);
        AccountVO accountVO = new AccountVO();
        accounts.forEach(account -> {
            switch (account.getPlatform()) {
                case "codeforces":
                    accountVO.setCodeforces(account.getUid());
                    break;
                case "luogu":
                    accountVO.setLuogu(account.getUid());
                    break;
                case "nowcoder":
                    accountVO.setNowcoder(account.getUid());
                    break;
                case "vjudge":
                    accountVO.setVjudge(account.getUid());
                    break;
            }
        });
        log.info("get the account is :{}", accounts);
        return accountVO;
    }

    @Override
    @DeleteRedis(key = redisKey, id = true)
    public int updateAccount(AccountVO accountVO) {
        Long id = ThreadLocalUtil.getCurrentUser();
        int ok = 1;
        ok = Math.max(updateByPlat(id, accountVO.getCodeforces(), PlatformEnum.CODEFORCES.getPlatform()), ok);
        ok = Math.max(updateByPlat(id, accountVO.getLuogu(), PlatformEnum.LUOGU.getPlatform()), ok);
        ok = Math.max(updateByPlat(id, accountVO.getNowcoder(), PlatformEnum.NOWCODER.getPlatform()), ok);
        ok = Math.max(updateByPlat(id, accountVO.getVjudge(), PlatformEnum.VJUDGE.getPlatform()), ok);
        log.info("update account successfully");
        return ok;
    }

    @DeleteRedis(key = redisKey, id = true)
    @Override
    public int addAccount(Long userId) {
        accountMapper.addUserId(userId, PlatformEnum.CODEFORCES.getPlatform());
        accountMapper.addUserId(userId, PlatformEnum.LUOGU.getPlatform());
        accountMapper.addUserId(userId, PlatformEnum.NOWCODER.getPlatform());
        accountMapper.addUserId(userId, PlatformEnum.VJUDGE.getPlatform());
        log.info("add account successfully");
        return 1;
    }

    @Override
    public int deleteById(Long id) {
        int ok = accountMapper.deleteById(id);
        log.info("delete account successfully");
        return ok;
    }

    private int updateByPlat(Long id, String uid, String platform) {
        Account account = new Account();
        copyPro(account, id, uid, platform);
        Account account1 = accountMapper.getAccountByIdAndPlat(id, platform);
        if (account1 != null) {
            log.info("try to update account: {} of {}", uid, platform);
            return accountMapper.updateAccount(id, uid, platform);
        } else {
            log.info("the account is not exits, try to insert account: {} of {}", uid, platform);
            return accountMapper.insertAccount(id, uid, platform);
        }
    }

    private void copyPro(Account account, Long userId, String uid, String platform) {
        account.setUserId(userId);
        account.setUid(uid);
        account.setPlatform(platform);
    }
}
