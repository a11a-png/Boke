package com.wjg.boke.boke;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjg.boke.boke.po.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BokeApplicationTests {

    @Value("${im.server.port}")
    public int imPort;

    @Autowired
    public RestHighLevelClient restHighLevelClient;

    @Test
    public void pp(){
        System.out.println(imPort);
    }

    //创建索引并赋值
    @Test
    public void contextLoads() throws IOException {
        System.out.println("开始运行");
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("10001");
        // 2.创建实体类对象，填充数据
        User user=new User();
        user.setName("张三丰");
        user.setAge(30);
        user.setSex("男");
        // 3.利用jackson将实体类对象转换成JSON格式字符串
        ObjectMapper mapper=new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        //4.添加文档数据，数据格式为JSON格式
        indexRequest.source(userJson, XContentType.JSON);
        //执行创建索引请求
        IndexResponse response=restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        // 5.发送请求，获取响应结果
        System.out.println("_index: "+response.getIndex());
        System.out.println("_id: "+response.getId());
        System.out.println("_result: "+response.getResult());
        // 一番操作后，关闭客户端连接
        restHighLevelClient.close();
    }

    //索引的获取，并判断其是否存在
    @Test
    public void getIndexExists() throws IOException {
        GetIndexRequest request=new GetIndexRequest("user");
        boolean exists=restHighLevelClient.indices().exists(request,RequestOptions.DEFAULT);
        System.out.println(exists);// 索引是否存在
        // 一番操作后，关闭客户端连接
        restHighLevelClient.close();
    }

    //文档添加
    @Test
    public void testAddDocument() throws IOException {
        //创建user
        User user=new User();
        user.setName("笑笑");
        user.setAge(20);
        user.setSex("女");
        //创建请求
        IndexRequest request=new IndexRequest("user");
        request.id("2");
        //将数据放入索引请求中
        request.source(JSON.toJSONString(user),XContentType.JSON);
        //客户端发送请求，获取响应结果
        IndexResponse response=restHighLevelClient.index(request,RequestOptions.DEFAULT);
        System.out.println(response.status());// 获取建立索引的状态信息 CREATED
        System.out.println(response);// 查看返回内容
        //IndexResponse[index=user,type=_doc,id=2,version=1,result=created,seqNo=1,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
    }

    //文档信息的获取
    @Test
    public void testGetDocument() throws IOException {
        GetRequest request=new GetRequest("user","2");
        GetResponse response=restHighLevelClient.get(request,RequestOptions.DEFAULT);
        //打印文档内容
        String str=JSON.toJSONString(response.getSourceAsString()).replace("\\","");
        User uu=JSON.parseObject(JSON.parse(response.getSourceAsString()).toString(),User.class);
        System.out.println(uu); // 返回的全部内容和命令是一样的
        // 一番操作后，关闭客户端连接
        restHighLevelClient.close();
    }

    //文档的获取，并判断其是否存在
    @Test
    public void testGetDocumentInIndex() throws IOException {
        GetRequest request=new GetRequest("user","2");
        // 不获取返回的 _source的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists=restHighLevelClient.exists(request,RequestOptions.DEFAULT);
        System.out.println(exists);
        restHighLevelClient.close();
    }

    //文档的更新
    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest request=new UpdateRequest("user","2");
        User user=new User();
        user.setName("舟舟");
        request.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse response=restHighLevelClient.update(request,RequestOptions.DEFAULT);
        //修改状态
        System.out.println(response.status());  //ok
        restHighLevelClient.close();
    }

    //文档的删除
    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest request=new DeleteRequest("user","2");
        DeleteResponse response=restHighLevelClient.delete(request,RequestOptions.DEFAULT);
        //删除状态
        System.out.println(response.status());
        restHighLevelClient.close();
    }

    // 文档的查询
    // SearchRequest 搜索请求
    // SearchSourceBuilder 条件构造
    // HighlightBuilder 高亮
    // TermQueryBuilder 精确查询
    // MatchAllQueryBuilder
    // xxxQueryBuilder ...
    @Test
    public void testSearch() throws IOException {
       //1、创建查询请求对象
       SearchRequest searchRequest=new SearchRequest("jd_goods");
       //2、构建搜索条件
       SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
       // (1)查询条件  使用QueryBuilders工具类创建
       // 精确查询
       TermQueryBuilder termQueryBuilder= QueryBuilders.termQuery("title","vue");
       //  (3)条件投入
       searchSourceBuilder.query(termQueryBuilder);

       //3、添加条件到请求
       searchRequest.source(searchSourceBuilder);

       //4、客户端查询请求
       SearchResponse search=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

       //5、查看返回结果
       SearchHits hits=search.getHits();
       System.out.println("=======================");
       for (SearchHit documentFields : hits.getHits()) {
           System.out.println(documentFields.getSourceAsMap());
       }
    }

    //前面的操作都无法批量添加数据
    @Test
    public void testAdd() throws IOException {
        IndexRequest request=new IndexRequest("bulk");  // 没有id会自动生成一个随机ID
        request.source(JSON.toJSONString(new User(20,"小小","男")),XContentType.JSON);
        request.source(JSON.toJSONString(new User(21,"小鹏","男")),XContentType.JSON);
        request.source(JSON.toJSONString(new User(22,"小巫","男")),XContentType.JSON);
        request.source(JSON.toJSONString(new User(23,"小梁","男")),XContentType.JSON);
        IndexResponse response=restHighLevelClient.index(request,RequestOptions.DEFAULT);

        System.out.println(response.status());// created
        //u6jhaoABxPixLtXgCF8E随机生成ID   数据都被 “小梁” 覆盖
        restHighLevelClient.close();
    }

    //批量添加数据
    @Test
    public void testBulk() throws IOException {

        BulkRequest bulkRequest=new BulkRequest();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(20,"liuyou-1","男"));
        users.add(new User(20,"liuyou-2","男"));
        users.add(new User(20,"liuyou-3","男"));
        users.add(new User(20,"liuyou-4","男"));
        users.add(new User(20,"liuyou-5","男"));
        users.add(new User(20,"liuyou-6","男"));

        for (int i=0;i<users.size();i++){
            User user= users.get(i);
            bulkRequest.add(
                    new IndexRequest("bb").id(""+i+"")
                    .source(JSON.toJSONString(user),XContentType.JSON)
            );
        }
        BulkResponse response=restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(response.status());
        restHighLevelClient.close();
    }

}
