//package io.renren.modules.zj;
//
//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.common.profile.HttpProfile;
//import com.tencentcloudapi.ocr.v20181119.OcrClient;
//import com.tencentcloudapi.ocr.v20181119.models.*;
//import io.renren.modules.Email.dto.SysEmailDTO;
//import io.renren.modules.Email.service.SysEmailService;
//import io.renren.modules.WxUser.dto.WxUserDTO;
//import io.renren.modules.WxUser.service.WxUserService;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//public class Screenshot {
//    @Autowired
//    private SysEmailService sysEmailService;
//    @Autowired
//    private WxUserService wxUserService;
//
//    public void sendWx() throws Exception {
//        Date now = new Date();
//        SimpleDateFormat sdfPath = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sdfName = new SimpleDateFormat("yyyyMMddHHmmss");
//        String path = sdfPath.format(now);
//        String name = sdfName.format(now);
//        captureScreen("C:" + File.separator + path + File.separator, name + ".png");
//
//    }
//
//
//    /**
//     * 截图
//     *
//     * @param filePath 截图保存文件夹路径
//     * @param fileName 截图文件名称
//     * @throws Exception
//     */
//    public void captureScreen(String filePath, String fileName) throws Exception {
//        mouseClick(32, 48);
//        mouseClick(130, 48);
//        mouseClick(110, 127);
////        mouseClick(52, 566);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Rectangle screenRectangle = new Rectangle(screenSize);
//        Robot robot = new Robot();
//        BufferedImage image = robot.createScreenCapture(screenRectangle);
//        File screenFile = new File(filePath + fileName);
//        if (!screenFile.getParentFile().exists())
//            screenFile.getParentFile().mkdirs();
//        BufferedImage subimage = image.getSubimage(1, 133, 470, 119);
//        ImageIO.write(subimage, "png", screenFile);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String strSentDate = format.format(new Date());
//        List<SysEmailDTO> byDate = this.sysEmailService.getByDate(strSentDate);
//        boolean f = true;
//        String URL = "";
//        if (byDate.size() > 0) {
//            URL = byDate.get(0).getTitle();
//            File oldFile = new File(URL);
//            if (oldFile.exists()) {
//                if (oldFile.length() == screenFile.length()) {
//                    screenFile.delete();
//                    System.out.println("图片存在 不进行图像识别，删除当前图片----------");
//                    f = false;
//                }
//            }
//        }
//        if (f) {
//            TextDetection[] texts = null;
//            int a = (int) (Math.random() * 4);
//            if (a == 0) {
//                texts = getText(filePath + fileName);
//            } else if (a == 1) {
//                texts = getText1(filePath + fileName);
//            } else if (a == 2) {
//                texts = getText2(filePath + fileName);
//            } else if (a == 3) {
//                texts = getText3(filePath + fileName);
//            }
//
//            System.out.println("图像识别完成------------------------------------------------------");
//
//            StringBuffer sb = new StringBuffer();
//
//            for (int i = 0; i < texts.length; i++) {
//                sb.append(texts[i].getDetectedText() + System.getProperty("line.separator"));
//            }
//
//            List<SysEmailDTO> list2 = null;
//            if (texts != null) {
//                String textFirst = texts[0].getDetectedText();
//                System.out.println("----------" + textFirst);
//                list2 = this.sysEmailService.getremark(textFirst);
//
//            }
//
//            SysEmailDTO sysEmailDTO = new SysEmailDTO();
//            sysEmailDTO.setContent(sb.toString());
//            sysEmailDTO.setTitle(filePath + fileName);
//            sysEmailDTO.setCreateDate(new Date());
//            sysEmailDTO.setRemark(texts[0].getDetectedText());
//            this.sysEmailService.save(sysEmailDTO);
//            System.out.println("保存新的sysEmail對象------------------------------------------------------");
//
//
//
//            System.out.println("进行数据对比------------------------------------------------------");
//            if (byDate.size()>0) {
//                System.out.println("原数据byDate.get(0)：");
//                System.out.println(byDate.get(0).getContent());
//                System.out.println(byDate.get(0).getTitle());
//            }
//
//
//            if (texts != null) {
//                String textFirst = texts[0].getDetectedText();
//                System.out.println("----------" + textFirst);
//
//                if (list2 != null && list2.size() > 0) {
//                    return;
//                }
//
//
//                if (!textFirst.contains("vs")) {
//                    System.out.println("------");
//                    return;
//                } else if (textFirst.contains("角") || textFirst.contains("绝") || textFirst.contains("铯") || textFirst.contains("絶")) {
//                    System.out.println("数据包含 角 绝 铯 絶 不推荐");
//                    return;
//                } else if (byDate.size() > 0 && byDate.get(0).getContent().equals(sb.toString())) {
//                    System.out.println("文字完全内容相同 不推");
//                    return;
//                } else if (byDate.size() > 0 && byDate.get(0).getContent().contains(textFirst)) {
//                    System.out.println("文字包含上次推送内容 不推");
//                    return;
//                }
//                System.out.println("------正常推送----");
//                List<WxUserDTO> list = this.wxUserService.getList(strSentDate);
//                if (list != null && list.size() > 0) {
//                    List<String> ids = new ArrayList<>();
//                    for (WxUserDTO dto : list) {
//                        System.out.println(dto.getUserName() + ":" + dto.getOpenId());
//                        ids.add(dto.getOpenId());
//                    }
//                    System.out.println("" + sb);
//                    doPostData(texts[0].getDetectedText(), sb.toString(), ids);
//                }
//            } else {
//                System.out.println("------没有识别出文字--不推--");
//            }
//
//
//
//
//        } else {
//            System.out.println("-----不满足条件 不推----- ");
//        }
//    }
//
//    // 鼠標點擊
//    private void mouseClick(int x, int y) throws Exception {
//        final Robot r = new Robot();
//        r.mouseMove(x, y);
//        //按下鼠标
//        r.mousePress(MouseEvent.BUTTON1_MASK);
//        //释放鼠标
//        r.mouseRelease(MouseEvent.BUTTON1_MASK);
//        Thread.sleep(4000);
//    }
//
//
//    public TextDetection[] getText(String url) {
//
//        String SecretId = "AKIDpfKn5CWfgV6bw64qRq1yhGhV7iDamnbF";
//        String SecretKey = "jQzLWWKMhq13wfCarxpUu64mE3f5HEFF";
//        try {
//
//            Credential cred = new Credential(SecretId, SecretKey);//密钥
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
//            String params = "{\"ImageBase64\":\"" + ImageToBase64(url) + "\"}";
//
//            GeneralBasicOCRRequest req = GeneralBasicOCRRequest.fromJsonString(params, GeneralBasicOCRRequest.class);
//            GeneralBasicOCRResponse resp = client.GeneralBasicOCR(req);
//            return resp.getTextDetections();
//
//        } catch (TencentCloudSDKException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//    }
//
//
//    public TextDetection[] getText1(String url) {
//
//        String SecretId = "AKIDpfKn5CWfgV6bw64qRq1yhGhV7iDamnbF";
//        String SecretKey = "jQzLWWKMhq13wfCarxpUu64mE3f5HEFF";
//        try {
//
//            Credential cred = new Credential(SecretId, SecretKey);//密钥
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
//            String params = "{\"ImageBase64\":\"" + ImageToBase64(url) + "\"}";
//
//            GeneralAccurateOCRRequest req = GeneralAccurateOCRRequest.fromJsonString(params, GeneralAccurateOCRRequest.class);
//
//            // 返回的resp是一个GeneralAccurateOCRResponse的实例，与请求对象对应
//            GeneralAccurateOCRResponse resp = client.GeneralAccurateOCR(req);
//            // 输出json格式的字符串回包
//            System.out.println(GeneralAccurateOCRResponse.toJsonString(resp));
//
//            return resp.getTextDetections();
//
//        } catch (TencentCloudSDKException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//    }
//
//
//    public TextDetection[] getText2(String url) {
//
//        String SecretId = "AKIDpfKn5CWfgV6bw64qRq1yhGhV7iDamnbF";
//        String SecretKey = "jQzLWWKMhq13wfCarxpUu64mE3f5HEFF";
//        try {
//
//            Credential cred = new Credential(SecretId, SecretKey);//密钥
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
//            String params = "{\"ImageBase64\":\"" + ImageToBase64(url) + "\"}";
//
//            GeneralFastOCRRequest req = GeneralFastOCRRequest.fromJsonString(params, GeneralFastOCRRequest.class);
//
//            // 返回的resp是一个GeneralFastOCRResponse的实例，与请求对象对应
//            GeneralFastOCRResponse resp = client.GeneralFastOCR(req);
//            // 输出json格式的字符串回包
//            System.out.println(GeneralFastOCRResponse.toJsonString(resp));
//            return resp.getTextDetections();
//
//        } catch (TencentCloudSDKException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//    }
//
//
//    public TextDetection[] getText3(String url) {
//
//        String SecretId = "AKIDpfKn5CWfgV6bw64qRq1yhGhV7iDamnbF";
//        String SecretKey = "jQzLWWKMhq13wfCarxpUu64mE3f5HEFF";
//        try {
//
//            Credential cred = new Credential(SecretId, SecretKey);//密钥
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
//            String params = "{\"ImageBase64\":\"" + ImageToBase64(url) + "\"}";
//
//            GeneralEfficientOCRRequest req = GeneralEfficientOCRRequest.fromJsonString(params, GeneralEfficientOCRRequest.class);
//
//            // 返回的resp是一个GeneralEfficientOCRResponse的实例，与请求对象对应
//            GeneralEfficientOCRResponse resp = client.GeneralEfficientOCR(req);
//            // 输出json格式的字符串回包
//            System.out.println(GeneralEfficientOCRResponse.toJsonString(resp));
//            return resp.getTextDetections();
//
//        } catch (TencentCloudSDKException e) {
//            System.out.println(e.toString());
//        }
//        return null;
//    }
//
//
//    //图片转化成base64字符串
//    public String ImageToBase64(String img) {
//        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        String imgFile = img;//待处理的图片
//        InputStream in = null;
//        byte[] data = null;
//        //读取图片字节数组
//        try {
//            in = new FileInputStream(imgFile);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //对字节数组Base64编码
////        BASE64Encoder encoder = new BASE64Encoder();
////
////        byte[] encrypted1 =Base64.decodeBase64(data); //解码
//
//        //返回Base64编码过的字节数组字符串
//        return Base64.encodeBase64String(data);
//    }
//
//
//    /**
//     * 执行Post请求
//     *
//     * @param
//     * @param
//     * @return
//     */
//
//    public String doPostData(String summary, String content, List<String> UIDS) throws Exception {
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost("http://wxpusher.zjiecode.com/api/send/message");
//        String result = "";
//        HttpResponse res = null;
//        try {
//            JSONObject object = new JSONObject();
//            object.put("appToken", "AT_sjuJNPwbnJNardEtKqwLuCp5JihyCcLY");
//            object.put("summary", summary);
//            object.put("content", content);
//            object.put("contentType", 1);
//            object.put("uids", UIDS);
//            StringEntity s = new StringEntity(object.toString(), "UTF-8");
//            s.setContentType("application/json");
//            post.setHeader("Accept", "application/json");
//            post.setHeader("Content-type", "application/json; charset=utf-8");
//            post.setEntity(s);
//            res = client.execute(post);
//            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                result = EntityUtils.toString(res.getEntity());
//                return HttpStatus.SC_OK + "";
//            }
//        } catch (Exception e) {
//            if (res == null) {
//                return "HttpResponse 为 null!";
//            }
//            throw new RuntimeException(e);
//        }
//        if (res == null || res.getStatusLine() == null) {
//            return "无响应";
//        }
//        return res.getStatusLine().getStatusCode() + "";
//    }
//
//}