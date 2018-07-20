package com.trs.service;

import com.trs.core.util.Status;
import com.trs.model.Knowledge;
import com.trs.model.KnowledgeReviewWithBLOBs;
import com.trs.model.KnowledgeSegmentBook;
import com.trs.model.KnowledgeSegmentBookWithBLOBs;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pz on 2017/3/10.
 */

public interface FeedbackService {

    /**
     * 保存用户反馈
     */
    public Status saveFeedback(HttpServletRequest request);

    public Map findAll(Map map);
}