package android.view.tools;

import android.view.entities.message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class jxHtml {

    public List<message> getMessage() throws IOException {
        List list = new ArrayList();
        Document doc = Jsoup.connect("http://tuan.baidu.com/wuhan")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post(); //.get();

        Elements content = doc.select("a[class~=fcb*]");
        Elements contentImg = doc.select("img[alt~=【*]").select("img[height~=145*]");
        HashMap<Integer, String> map = new HashMap();
        int j = 0;
        for (Element linkImg : contentImg) {
            String imgUrl = linkImg.attr("src");
            map.put(j, imgUrl);
            j++;
        }
        int k = 0;
        for (Element link : content) {
            String url = link.attr("href");
            String title = link.attr("title");

            if (!title.contains("该网站")) {
                // map.get(k);
                message message = new message();
                message.setTitle(title);
                message.setSaleurl(url);
                message.setImgurl(map.get(k));
                list.add(message);
                k++;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            message message2 = (message) list.get(i);
            System.out.println(message2.getSaleurl());
            System.out.println(message2.getTitle());
            System.out.println(message2.getImgurl());

        }
        return list;
    }
}
