package mi.song.skill.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mi.song.skill.util.SkillSSL;

@Controller
public class CinemaController {

    @RequestMapping("/cinemainfo")
    @ResponseBody
    public String index() {
        // getCinemaInfo();
        return getCinemaInfo().toString();//"Hello, Spring";
    }

    //영화 정보 받아오는 함수
    public JSONObject getCinemaInfo() {
        JSONObject result = new JSONObject();
        String baseUrl = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EC%98%81%ED%99%94+%EC%83%81%EC%98%81%EC%9E%91";
        try {

            if (baseUrl.contains("https://"))
                SkillSSL.setSSL();

            Document cinemaWholePage = Jsoup.connect(baseUrl).get();
            Elements section = cinemaWholePage.select("ul.thumb_list>li>dl>dt>a");
                        
            JSONArray jsonArray = new JSONArray();

            for (Element element : section) {
                Attributes attr = element.attributes();
                String title = attr.get("title");
                jsonArray.put(title);                
            }          
            JSONObject data = new JSONObject();            
            result.append("code", 1000);

            //data append
            data.append("contentType", "textRandom");
            data.append("inputType", "text");
            data.append("responseButtons", new JSONArray());
            data.append("responseText", jsonArray);            
            data.append("imagePath", false);
            data.append("imageUrl", null);
            data.append("entities", new JSONArray());
            data.append("requiredEntities", new JSONArray());
            data.append("lifespan", 5);

            result.append("data", data);

        } catch(Exception e){
            e.printStackTrace();
            result.append("code", 9000);
        }finally{
            return result;
        }        
    }

}