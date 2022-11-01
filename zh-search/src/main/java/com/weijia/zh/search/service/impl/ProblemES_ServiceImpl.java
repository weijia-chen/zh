package com.weijia.zh.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.search.config.ElasticSearchConfig;
import com.weijia.zh.search.key.Key;
import com.weijia.zh.search.service.ProblemES_Service;
import com.weijia.zh.search.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProblemES_ServiceImpl implements ProblemES_Service {

    @Autowired
    RestHighLevelClient client;

    @Resource
    private Key key;

    @Override
    public R addES(ProblemEntity problem) {

        log.info("成功进入addES，{}",key.getIndex());
        log.info("传入的数据为:{}",problem);
        // 1、构建创建或更新请求，problems
        IndexRequest indexRequest = new IndexRequest(key.getIndex());
        // 2、设置id
        indexRequest.id(String.valueOf(problem.getId()));// 数据的id

        // 方式二：设置json串格式数据
        indexRequest.source(JSON.toJSONString(problem), XContentType.JSON);
        IndexResponse index = null;
        try {
            index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new R(RespBeanEnum.SUCCESS,null);
    }

    @Override
    public boolean delES(Long problemId)  {
        DeleteRequest request = new DeleteRequest().index(this.key.getIndex()).id(String.valueOf(problemId));
        DeleteResponse response = null;
        try {
            response = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        log.info(response.toString());
        return true;
    }

    @Override
    public List searchProblemOrUserName(String query) throws IOException {

        List<ProblemEntity> problems = new ArrayList<>(100);//最多100条，免于扩容

        // 1、创建检索请求，自定索引（调用该方法可以切换索引searchRequest.indices("bank")）
        SearchRequest searchRequest = new SearchRequest("problems");
        // 2、构建检索条件，DSL
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

//        用于构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

//        组合查询,模糊查询
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", query));
        boolQueryBuilder.should(QueryBuilders.matchQuery("userName", query));
        boolQueryBuilder.should(QueryBuilders.fuzzyQuery("title",query));
        boolQueryBuilder.should(QueryBuilders.fuzzyQuery("userName",query));

        // 取前100条
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);

        System.out.println("检索条件:" + sourceBuilder);
        // 3、检索请求绑定条件
        searchRequest.source(sourceBuilder);
        // 4、执行请求，接收结果
        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        // 5、获取命中的数据【Map map = JSON.parseObject(searchResponse.toString(), Map.class)】
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            // 将_source封装Object对象
            ProblemEntity account = JSON.parseObject(hit.getSourceAsString(), ProblemEntity.class);
            problems.add(account);
            System.out.println("搜索结果： " + account);
        }

        return problems;
    }
}
