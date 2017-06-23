package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Post;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/6/23 11:16.
 */
public interface IPostService extends BaseRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
}
