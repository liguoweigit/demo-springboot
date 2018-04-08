package com.yongche.job;

import com.yongche.utils.SendMailUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author liguowei
 * @time 2018/3/29 11:39
 * @package com.li.job
 */
@Component("DownloadExcelJob")
public class DownloadExcelJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 */1 * * * ?")  // 每分钟执行一次
    //@Scheduled(cron = "0 0 */3 * * ? ")  // 每两个小时执行一次
    //@Scheduled(cron = "0 0 7,9,15 * * ? ") // 每天7点、9点、15点运行
    public void downloadExcel() throws Exception {
        CloseableHttpClient httpclient = null;
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;

        try {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            // 将最大连接数增加到300
            cm.setMaxTotal(300);
            // 将每个路由基础的连接增加到100
            cm.setDefaultMaxPerRoute(100);
            // 链接超时setConnectTimeout ，读取超时setSocketTimeout
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();

            httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig).build();

            logger.info("download-excel start...");
            HttpGet httpget = new HttpGet("https://web.umeng.com/main.php?c=site&a=show&ajax=module=report&tab=0&search=&downloadType=xls");
            httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpget.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            httpget.setHeader("Connection", "keep-alive");
            httpget.setHeader("Cookie", "UM_distinctid=1626beb9321286-060db8b8d0353-49566f-fa000-1626beb93231c1; CNZZDATA30086426=cnzz_eid%3D158573608-1522224680-https%253A%252F%252Fweb.umeng.com%252F%26ntime%3D1523149916; CNZZDATA30069868=cnzz_eid%3D1530871736-1522227849-https%253A%252F%252Fweb.umeng.com%252F%26ntime%3D1523150585; CNZZDATA33222=cnzz_eid%3D986633784-1522225697-https%253A%252F%252Fweb.umeng.com%252F%26ntime%3D1523150217; CNZZDATA30001831=cnzz_eid%3D1379293902-1522228819-https%253A%252F%252Fweb.umeng.com%252F%26ntime%3D1523148336; CNZZDATA1255696692=1966078599-1522227339-https%253A%252F%252Fweb.umeng.com%252F%7C1523149659; cna=tf9IEpfskgwCAT2UJLKZQG0H; isg=BJKSS1l1HOSBcmDq03DBqifd4F60Cx18-HjeW1zrsMUwbzNpRDKtTUaN20vTBA7V; _cnzz_CV30069868=%E6%98%AF%E5%90%A6%E7%99%BB%E5%BD%95%7C%E6%AD%A3%E5%B8%B8%E7%99%BB%E5%BD%95%7C1523182057686; PHPSESSID=3mkkll7a0hakso70u0rvpptto7; uc_session_id=270c596c-cc42-49a5-bcae-cce3d0f6b09b; umplus_uc_token=1YAoZ_ZRmDgo1OufBmRAisQ_5fc0efec465342b19751dab1ed2dbf16; umplus_uc_loginid=15650672414%40163.com; cn_1258498910_dplus=1%5B%7B%22userid%22%3A%2215650672414%40163.com%22%7D%2C0%2C1523153250%2C0%2C1523153250%2C%22%24direct%22%2C%221626beb9321286-060db8b8d0353-49566f-fa000-1626beb93231c1%22%2C%221522228971%22%2C%22%24direct%22%2C%22%24direct%22%5D; from=umeng; edtoken=cnzz_5ac97968a118a; cn_ea1523f470091651998a_dplus=%7B%22distinct_id%22%3A%20%221626beb9321286-060db8b8d0353-49566f-fa000-1626beb93231c1%22%2C%22sp%22%3A%20%7B%22%24_sessionid%22%3A%200%2C%22%24_sessionTime%22%3A%201523153302%2C%22%24dp%22%3A%200%2C%22%24_sessionPVTime%22%3A%201523153302%7D%2C%22initial_view_time%22%3A%20%221522227339%22%2C%22initial_referrer%22%3A%20%22https%3A%2F%2Fweb.umeng.com%2Fmain.php%3Fc%3Duser%26a%3Dindex%22%2C%22initial_referrer_domain%22%3A%20%22web.umeng.com%22%7D");
            httpget.setHeader("Host", "web.umeng.com");
            httpget.setHeader("Referer", "https://web.umeng.com/main.php?c=site&a=show");
            httpget.setHeader("Upgrade-Insecure-Requests", "1");
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");

            ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>() {
                @Override
                public byte[] handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toByteArray(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };

            byte[] out = httpclient.execute(httpget, responseHandler);
            File f = new File("/yongche/lgw/excelfile");
            if(!f.exists()){
                f.mkdir();
            }
            String filePath = "/yongche/lgw/excelfile/15650672414@163.com-站点流量概况(" + LocalDate.now().minusDays(1) + UUID.randomUUID().toString() + ").xls";
            File file = new File(filePath);
            output = new FileOutputStream(file);
            bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(out);
            logger.info("download-excel end...");

            JavaMailSenderImpl sender = SendMailUtils.initJavaMailSender();
            String[] ss = { "liguowei_hnny@163.com", "1071563296@qq.com" };
            SendMailUtils.sendWithAttament(sender,ss, LocalDate.now().minusDays(1)+"站点流量概况","<html><head></head><body><h3>亲爱的：请查收附件</h3></body></html>",LocalDate.now().minusDays(1)+".xls",filePath);





        } catch (Exception e) {
            e.printStackTrace();
            logger.error("downloadExcel occur exception,msg:{}",e.getMessage());
        } finally {

            if (bufferedOutput != null) {
                bufferedOutput.close();
            }
            if (output != null) {
                output.close();
            }

            if (httpclient != null) {
                httpclient.close();
            }

        }
    }

    public static void main(String[] args) {
        try {
            new DownloadExcelJob().downloadExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
