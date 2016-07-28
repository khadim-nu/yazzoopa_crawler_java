package scraping;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author
 */
public class Marks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       // System.out.println("Marks started");
        String url = "jdbc:mysql://localhost:3306/yazzoopa_scraper?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "raath@aws";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
          //  System.out.println("Database connected");
            //////////////////////////
            String urls[][] = {
                {"224", "https://www.marks.com/en/home-page.html"},};

            int ss = 0;
            for (int i = 0; i < urls.length; i++) {
                try {
                    String category_title = "";
                    //////////////////////
                    String urlstring = urls[i][1];
                    Document doc = Jsoup.connect(urlstring)
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .get();

                    Elements products = null;

                    products = doc.getElementsByClass("mega-menu__list-container");
                    // System.out.println(products.size());

                    for (int item = 0; item < products.size(); item++) {
                        Elements links = products.get(item).getElementsByTag("li");
                        //  Document subdoc = Jsoup.connect(link).timeout(100 * 1000).get();
                        int ii = 0;
                        for (Element link : links) {
                            if (ii != 0 && ii < links.size() - 1) {
                                Element ancor = link.getElementsByTag("a").get(0);
                                if (!ancor.hasClass("mega-menu__link_indent")) {
                                    String ll = "https://www.marks.com" + ancor.attr("href");
                                   // System.out.println(ll);
                                    Document subdoc = Jsoup.connect(ll)
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                            .maxBodySize(0)
                                            .timeout(600000)
                                            .get();
                                    String info=subdoc.getElementsByClass("product-grid").get(0).attr("data-module-options");
                                    //{"presetFilters":{"x1":"c.category-level-1","q1":"c_00689","x2":"c.category-level-2","q2":"c_00773","x3":"c.category-level-3","q3":"c_00730"}}
                                      info=info.replace("{\"presetFilters\":{","");
                                      info=info.replace("}}", "");
                                      info=info.replace("\"","");
                                      info=info.replace(":","=");
                                      info=info.replace(",","&");
                                      info +="&count=$perPage&page=1";
                                      info="\"https://www.marks.com/services-rest/marks/search-and-promote/products?"+info+"\"";
                                      System.out.println(info+",");
                                   
                                }
                            }
                            ii++;
                        }
//                    System.exit(1)
                    }

                    System.exit(1);

                    if (products.size() == 0) {
                        ///////
                        Element div = doc.getElementsByClass("grid-content").first();
                        String attr = div.toString();
                        String[] tokens = attr.split("data-productcollectionid=\"");
                        String temp = tokens[1];
                        String temp_url = "http://www.thesource.ca/en-ca/search/product-collection?uid=" + temp.split("\" data-categorycode=\"")[0];
                        temp_url += "&categoryCode=" + temp.split("\" data-categorycode=\"")[1].split("\">")[0];
                        Document doc_t = Jsoup.connect(temp_url).timeout(600000).get();
                        products = doc_t.getElementsByClass("related-product-item");
                    }
                    /////////////
                    System.out.println("size=" + products.size() + " url=" + urlstring);
                    for (int item = 0; item < products.size(); item++) {
                        String link = "http://www.thesource.ca" + products.get(item).select("a").first().attr("href");
                        Document subdoc = Jsoup.connect(link).timeout(100 * 1000).get();
//                   System.out.println(link);
//                    System.exit(1);

                        String name = subdoc.getElementsByClass("pdp-name").first().text();
                        String id = subdoc.getElementsByClass("pdp-code").first().getElementsByTag("span").text();
                        String price = subdoc.select("input[name=price]").val();
                        String description = "";
                        try {
                            description = subdoc.getElementsByClass("product-details-summary").first().getElementsByTag("p").first().text();
                        } catch (Exception e) {
                        }
                        String specification = "";
                        if (subdoc.getElementsByClass("product-detail-specs").hasText()) {
                            specification = subdoc.getElementsByClass("product-detail-specs").first().html();
                        }
                        String image_url = "http://www.thesource.ca" + subdoc.getElementsByClass("primary-image").first().attr("src");
                        int in_stock = 0;
                        if (subdoc.hasClass("in-stock")) {
                            in_stock = 1;
                        }
                        String other = "";
                        try {
                            Statement stmt = connection.createStatement();
                            stmt.execute("set names 'utf8'");
                            String sql = "INSERT INTO items (p_id,category_title,title,status,link,price,image_url,description,specification,other)"
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                            PreparedStatement pstmt = connection.prepareStatement(sql);
                            // Set the values
                            pstmt.setString(1, "thesource_" + id);
                            pstmt.setString(2, category_title);
                            pstmt.setString(3, name);
                            pstmt.setInt(4, in_stock);
                            pstmt.setString(5, link);
                            pstmt.setString(6, "$" + price);
                            pstmt.setString(7, image_url);
                            pstmt.setString(8, description);
                            pstmt.setString(9, specification);
                            pstmt.setString(10, other);
                            // Insert 
                            pstmt.executeUpdate();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
//                    System.exit(1);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done.");
    }
}
