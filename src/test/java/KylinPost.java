import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class KylinPost {
    private String encoding = "UTF-8";
    static String ACCOUNT = "ADMIN";
    static String PWD = "KYLIN";

    /**
     * 使用httpcline 进行post访问
     *
     * @throws IOException
     */
    public void requestByPostMethod() throws IOException {
        CloseableHttpClient httpClient = this.getHttpClient();
        try {
//创建post方式请求对象
            String url = "http://192.168.0.222:7070/kylin/api/query";
            HttpPost httpPost = new HttpPost(url);
            String sql = "select a.part_dt ,sum(a.price) as sum_price ,max(a.price) as max_price,count(*) as cnt from kylin_sales a "
                    + " inner join kylin_cal_dt b on a.part_dt = b.cal_dt "
                    + " inner join kylin_category_groupings c on a.lstg_site_id = c.site_id and a.leaf_categ_id = c.leaf_categ_id "
                    + " group by a.part_dt ;";
// 接收参数json列表 (kylin 只接受json格式数据)
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sql", sql);
            jsonParam.put("limit", "20");
            jsonParam.put("project", "learn_kylin");
            StringEntity sentity = new StringEntity(jsonParam.toString(), encoding);//解决中文乱码问题
            sentity.setContentEncoding(encoding);
            sentity.setContentType("application/json");
            httpPost.setEntity(sentity);
//设置header信息
//指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Authorization", this.authCode());
            System.out.println("POST 请求...." + httpPost.getURI());
//执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            try {
//                HttpEntity entity = httpResponse.getEntity();
//                if (null != entity) {
//按指定编码转换结果实体为String类型
//                    String body = EntityUtils.toString(entity, encoding);
//                    JSONObject obj = JSONObject.fromObject(body);
//                    System.out.println(body);
//                    System.out.println(obj.get("results"));
//                }
            } finally {
                httpResponse.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.closeHttpClient(httpClient);
        }
    }

    /**
     * kylin 是base64加密的，访问时候需要加上加密码
     *
     * @return
     */
    private String authCode() {
        String auth = ACCOUNT + ":" + PWD;
//        String code = "Basic " + new String(new Base64().encode(auth.getBytes()));
        return auth;
    }

    /**
     * 创建httpclient对象
     *
     * @return
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    /**
     * 关闭链接
     *
     * @param client
     * @throws IOException
     */
    private void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) throws IOException {
        KylinPost ky = new KylinPost();
        ky.requestByPostMethod();
    }
}