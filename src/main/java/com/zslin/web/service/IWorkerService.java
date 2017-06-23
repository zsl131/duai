package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Worker;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/6/23 11:11.
 */
public interface IWorkerService extends BaseRepository<Worker, Integer>, JpaSpecificationExecutor<Worker> {
}
