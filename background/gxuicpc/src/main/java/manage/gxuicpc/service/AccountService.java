package manage.gxuicpc.service;

import manage.gxuicpc.entity.vo.AccountVO;

public interface AccountService {
    AccountVO getAccount();

    int updateAccount(AccountVO accountVO);

    int addAccount(Long userId);

    int deleteById(Long id);
}
