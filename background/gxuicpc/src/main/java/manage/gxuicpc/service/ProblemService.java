package manage.gxuicpc.service;

import manage.gxuicpc.entity.Problem;

public interface ProblemService {
    Problem getProblemAmount();

    Integer addUser(Long id);

    int deleteById(Long id);
}
